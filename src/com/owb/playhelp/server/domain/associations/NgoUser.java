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
public class NgoUser  implements Serializable, Cacheable {

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
	private String ngoId;

	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private String userId;

	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private String creatorId;

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
	public NgoUser(String ngoId, String userId, String creatorId) {
		this.userId = userId;
		this.ngoId = ngoId;
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
				
				dq = pm.newQuery("select id from " + NgoUser.class.getName());			
				List<Long> foundIdNgos;
				foundIdNgos = (List<Long>) dq.execute();
				
				NgoUser foundNgo = null;
				ArrayList<NgoUser> ngoArray = new ArrayList<NgoUser>();
				for (Long ngoId: foundIdNgos){
					if (ngoId != null){
						foundNgo = pm.getObjectById(NgoUser.class, ngoId);
						ngoArray.add(foundNgo);	
					}
				}
				 return;
			}// end try
		    catch (Exception e) {
		        e.printStackTrace();
		      }
		    return;
		}
	  
	  
	  public static boolean isAssociated(String ngoId, String userId) {
	
		// Open the data-store manager 
	    PersistenceManager pm = PMFactory.getTxnPm();
	    
	    // Set the required variables
	    Transaction tx = null;
	    NgoUser oneResult = null;
	    
	    // Define the query
	    Query q = pm.newQuery(NgoUser.class, "userId == :userId && ngoId == :ngoId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        // Execute the query for the current uniqueId of the input object
	        oneResult = (NgoUser) q.execute(userId, ngoId);
		      
	        if (oneResult != null) {
	          log.info("User association exists: "+userId+", "+ngoId);
	          return true;
	        } else {
		      log.info("User association does not exist: "+userId+", "+ngoId);
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

	  public static void associate(String ngoId, String userId, String currentUser) {
		  
		  if (NgoUser.isAssociated(ngoId, userId)){
			  log.info("User association already exists: " + userId+", "+ngoId);
			  return;
		  }
		  
		  //Create a new object with the input values
		  NgoUser ngoUser = new NgoUser(ngoId, userId, currentUser);
		  
		  
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
	        
	        pm.makePersistent(ngoUser);
	        
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
	          pm.makePersistent(ngoUser);	    	
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