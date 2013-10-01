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
import javax.jdo.JDOException;
import javax.jdo.JDOFatalUserException;
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
import com.owb.playhelp.server.domain.Ngo;
import com.owb.playhelp.server.domain.SNgo;
import com.owb.playhelp.server.domain.user.UserProfile;
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
public class NgoStandard  implements Serializable, Cacheable {

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
	private Long ngoId;

	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private Long standardId;

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
	public NgoStandard(Long ngoId, Long standardId, Long creatorId) {
		this.standardId = standardId;
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
				
				dq = pm.newQuery("select id from " + NgoStandard.class.getName());			
				List<Long> foundIdNgos;
				foundIdNgos = (List<Long>) dq.execute();
				
				NgoStandard foundNgo = null;
				ArrayList<NgoStandard> ngoArray = new ArrayList<NgoStandard>();
				for (Long ngoId: foundIdNgos){
					if (ngoId != null){
						foundNgo = pm.getObjectById(NgoStandard.class, ngoId);
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
	  

	  public static SNgo getStandard(Ngo ngo) {
		  return NgoStandard.getStandard(ngo.getId());
	  }

	  public static SNgo getStandard(Long ngoId) {
	
		// Open the data-store manager 
	    PersistenceManager pm = PMFactory.getTxnPm();
	    
	    // Set the required variables
	    Transaction tx = null;
	    NgoStandard oneResult = null;
	    Long stdId = null;
	    
	    // Define the query
	    Query q = pm.newQuery(NgoStandard.class, "ngoId == :ngoId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        // Execute the query for the current uniqueId of the input object
	        oneResult = (NgoStandard) q.execute(ngoId);
		      
	        if (oneResult != null) {
	          log.info("Standard exists for Ngo id : "+ngoId);
	          stdId = oneResult.getStandardId();
	        } else {
		      log.info("Standard does not exist for Ngo id: "+ngoId);
	        }
	    } catch (JDOUserException e){
	          log.info("JDOUserException: NgoStandard table is empty");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (tx.isActive()) {
		        tx.rollback();
		      }
		      // Close the persistent manager and the query.
		      //pm.close();
		      q.closeAll();
		}
	    
	    if (stdId == null){
	    	pm.close();
	    	return null;
	    }
	    
	    SNgo standard = null;
	    //pm = PMFactory.getTxnPm();
	    tx = pm.currentTransaction();
	    
	    try{
	    	for (int i=0; i < NUM_RETRIES; i++){
	            tx = pm.currentTransaction();
	            tx.begin();
	            standard = (SNgo) pm.getObjectById(SNgo.class, stdId);
	    	}
	    } catch (JDOFatalUserException e){
	    		log.info("JDOUserException: SNgo table does not contain id "+stdId);
	          standard=null;
	    } catch (JDOUserException e){
	          log.info("JDOUserException: SNgo table is empty");
	          standard=null;
	    } catch (JDOException e) {
	        e.printStackTrace();
	        standard=null;
	    } catch (Exception e) {
	    	 e.printStackTrace();
		     standard = null;
	    } finally{
	          if (tx.isActive()) {
	              log.info("NgoStandard:getStandard: transaction rollback.");
	              tx.rollback();
	            }
	            pm.close();
	    }
	    return standard;
	  }
	  
	  public static boolean isAssociated(Long ngoId, Long standardId) {
	
		// Open the data-store manager 
	    PersistenceManager pm = PMFactory.getTxnPm();
	    
	    // Set the required variables
	    Transaction tx = null;
	    NgoStandard oneResult = null;
	    boolean isAssociated = false;
	    
	    // Define the query
	    Query q = pm.newQuery(NgoStandard.class, "standardId == :standardId && ngoId == :ngoId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        // Execute the query for the current uniqueId of the input object
	        oneResult = (NgoStandard) q.execute(standardId, ngoId);
		      
	        if (oneResult != null) {
	          log.info("User association exists: "+standardId+", "+ngoId);
	          isAssociated = true;
	        } else {
		      log.info("User association does not exist: "+standardId+", "+ngoId);
	        }
	    } catch (JDOUserException e){
	          log.info("JDOUserException: NgoStandard table is empty");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (tx.isActive()) {
		        tx.rollback();
		      }
		      // Close the persistent manager and the query.
		      pm.close();
		      q.closeAll();
		}
	    
	    return isAssociated;
	  }

	  public static void associate(Long ngoId, Long standardId, Long currentUser) {
		  
		  if (NgoStandard.isAssociated(ngoId, standardId)){
			  log.info("User association already exists: " + standardId+", "+ngoId);
			  return;
		  }
		  
		  //Create a new object with the input values
		  NgoStandard ngoUser = new NgoStandard(ngoId, standardId, currentUser);
		  
		  
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
	          log.info("JDOUserException: NgoStandard table is empty");
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


	  public Long getId(){
		  return this.id;
	  }
	  public Long getNgoId(){
		  return this.ngoId;
	  }
	  public Long getStandardId(){
		  return this.standardId;
	  }
	  public Long getCreatorId(){
		  return this.creatorId;
	  }
	  public Date getDate(){
		  return this.creationDate;
	  }
}