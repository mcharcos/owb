package com.owb.playhelp.server.domain;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import com.owb.playhelp.server.PMFactory;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class represents a Standard entity in the data store related to the Ngo. 
 * We differenciate Standards for Ngo and Orphanage so we can do searches later
 * for the specific entities instead of retrieving all of them and then checking
 * which ones correspond to what.
 *
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SNgo extends Standard {
	
	public SNgo(){
		super();
	}
	
	public SNgo(DBRecord record){
		super(record);
	}

	/**
	 * Retrieve the user from the database if it already exist or
	 * create a new account if it is the first loggin
	 * @param Standard
	 * @return
	 */
	  public static SNgo findOrCreate(SNgo record, String userId) {
	
		// Open the data-store manager 
	    PersistenceManager pm = PMFactory.getTxnPm();
	    
	    // Set the required variables
	    Transaction tx = null;
	    SNgo oneResult = null, detached = null;
	    
	    String uniqueId = record.getUniqueId();
	    
	    // Define the query
	    Query q = pm.newQuery(SNgo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        // Execute the query for the current uniqueId of the input object
	        oneResult = (SNgo) q.execute(uniqueId);
	        
	        // If found, we detach the copy from the datastore so
	        // we can use it when returned
	        // If not found, we make the current ngo persistent 
	        // and detache the object.
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("Standard " + uniqueId + " does not exist, creating...");
	          pm.makePersistent(record);
	          detached = pm.detachCopy(record);
	        }
	        
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
	          log.info("JDOUserException: UserProfile table is empty");
	          // Create friends from Google+
	          pm.makePersistent(record);
	          detached = pm.detachCopy(record);	
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
	      q.closeAll();
	    }
	    
	    // Return a detached copy of the retrieved object or 
	    // the input object after being persisted
	    return detached;
	  }
}
