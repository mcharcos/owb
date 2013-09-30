/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.associations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class represents associations between NGO and User entities in the data store. It includes the information
 * intrinsic to the instances (uniqueIds) and the id of the creator.
 *
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class OrphanageUser  implements Serializable, Cacheable {

	  protected static final Logger log = Logger.getLogger(Utils.class.getName());
	  protected static final int NUM_RETRIES = 5; 

	/*
	 * Index id of the data store
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;


	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private Long orphanageId;

	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private Long userId;

	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private Long creatorId;

	/*
	 * Date of the association
	 */
	@Persistent
	private Date creationDate;
	
	
	
	/**
	 * Constructor class from a shared class. It will initialize an object
	 * with the information from an DBRecordInfo object passed from the 
	 * front end. This constructor facilitates the creation of back end
	 * objects using information from the front end.
	 * @param DBRecordInfo
	 */
	public OrphanageUser(Long orphanageId, Long userId, Long creatorId) {
		this.userId = userId;
		this.orphanageId = orphanageId;
		this.creatorId = creatorId;
		this.creationDate = new Date();
	}


	  public void addToCache() {
	    CacheSupport.cachePut(this.getClass().getName(), id, this);
	  }

	  public void removeFromCache() {
	    CacheSupport.cacheDelete(this.getClass().getName(), id);
	  }
	  
	  public static void getAssociationList(){
			PersistenceManager pm = PMFactory.getNonTxnPm();

			pm = PMFactory.getNonTxnPm();
			try{
				Query dq = null;
				
				dq = pm.newQuery("select id from " + OrphanageUser.class.getName());			
				List<Long> foundIdNgos;
				foundIdNgos = (List<Long>) dq.execute();
				
				OrphanageUser foundOrphanage = null;
				ArrayList<OrphanageUser> ngoArray = new ArrayList<OrphanageUser>();
				for (Long ngoId: foundIdNgos){
					if (ngoId != null){
						foundOrphanage = pm.getObjectById(OrphanageUser.class, ngoId);
						ngoArray.add(foundOrphanage);	
					}
				}
				 return;
			}// end try
		    catch (Exception e) {
		        e.printStackTrace();
		      }
		    return;
		}
	  
	  
	  public static boolean isAssociated(Long orphanageId, Long userId) {
	
		// Open the data-store manager 
	    PersistenceManager pm = PMFactory.getTxnPm();
	    
	    // Set the required variables
	    Transaction tx = null;
	    OrphanageUser oneResult = null;
	    
	    // Define the query
	    Query q = pm.newQuery(OrphanageUser.class, "userId == :userId && orphanageId == :orphanageId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        // Execute the query for the current uniqueId of the input object
	        oneResult = (OrphanageUser) q.execute(userId, orphanageId);
		      
	        if (oneResult != null) {
	          log.info("User association exists: "+userId+", "+orphanageId);
	          return true;
	        } else {
		      log.info("User association does not exist: "+userId+", "+orphanageId);
	          return false;
	        }
	    } finally {
	      if (tx.isActive()) {
		        tx.rollback();
		      }
		      // Close the persistent manager and the query.
		      pm.close();
		      q.closeAll();
		}
	  }

	  public static void associate(Long orphanageId, Long userId, Long currentUser) {
		  
		  if (OrphanageUser.isAssociated(orphanageId, userId)){
			  log.info("User association already exists: " + userId+", "+orphanageId);
			  return;
		  }
		  
		  //Create a new object with the input values
		  OrphanageUser orphanageUser = new OrphanageUser(orphanageId, userId, currentUser);
		  
		  
		  // Open the data-store manager 
	      PersistenceManager pm = PMFactory.getTxnPm();
	    
	      // Set the required variables
	      Transaction tx = null;
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        pm.makePersistent(orphanageUser);
	        
	        // commit the transaction
	        try {
	          tx.commit();
	          break;
	        }
	        catch (JDOCanRetryException e1) {
	          if (i == (NUM_RETRIES - 1)) { 
	            throw e1;
	          }
	        }
	      } // end for
	    } catch (JDOUserException e){
	          log.info("JDOUserException: NgoUser table is empty");
	          // Create friends from Google+
	          pm.makePersistent(orphanageUser);	    	
		      try {
		    	  tx.commit();
		        }
		        catch (JDOCanRetryException e1) {
		        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    finally {
	      if (tx.isActive()) {
	        tx.rollback();
	      }
	      // Close the persistent manager and the query.
	      pm.close();
	    }
	    return;
	  }


}