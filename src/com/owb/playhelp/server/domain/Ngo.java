/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain;

import java.util.UUID;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.associations.NgoStandard;
import com.owb.playhelp.server.domain.associations.NgoUser;
import com.owb.playhelp.server.utils.EmailHelper;
import com.owb.playhelp.shared.DBRecordInfo;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class represents an NGO entity in the data store. It includes the information
 * intrinsic to the NGO such as location and contact information.
 *
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Ngo extends DBRecord  {

	public Ngo(){
		UUID uuid = UUID.randomUUID();
		this.setUniqueId(uuid.toString());  
	}
	
	/**
	 * Constructor class from a shared class. It will initialize an object
	 * with the information from an DBRecordInfo object passed from the 
	 * front end. This constructor facilitates the creation of back end
	 * objects using information from the front end.
	 * @param DBRecordInfo
	 */
	public Ngo(DBRecordInfo ngoInfo) {
		// Construct the object
		this();
		
		// Copy the information from the input
		this.reEdit(ngoInfo);
		this.setUniqueId(ngoInfo.getUniqueId());
		
		// If the input object did not contain a uniqueId, that means 
		// that it should be created because probably is a new entry
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.setUniqueId(uuid.toString());
		} 
	}


	/**
	 * Retrieve the user from the database if it already exist or
	 * create a new account if it is the first login
	 * @param DBRecord
	 * @return
	 */
	  public static Ngo findOrCreateDBRecord(Ngo record, Long userId){
	
		// Open the data-store manager 
	    PersistenceManager pm = PMFactory.getTxnPm();
	    
	    // Set the required variables
	    Transaction tx = null;
	    Ngo oneResult = new Ngo(), detached = new Ngo();
	    boolean newPersisted=false;
	    
	    // Get the unique Id from the input ngo
	    // Because all the ngo objects are created or copied
	    // with a uniqueId there should be one. 
	    // TODO We can implement handling this case later but it is not critical
	    String uniqueId = record.getUniqueId();
	    
	    // Define the query
	    Query q = pm.newQuery(Ngo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        // Initialize and start the transaction
	    	tx = pm.currentTransaction();
	        tx.begin();
	        
	        // Execute the query for the current uniqueId of the input object
	        oneResult = (Ngo) q.execute(uniqueId);
	        
	        // If found, we detach the copy from the datastore so
	        // we can use it when returned
	        // If not found, we make the current ngo persistent 
	        // and detache the object.
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("Ngo " + uniqueId + " does not exist, creating...");
	          pm.makePersistent(record);
	          log.info("Sending email...");
	          EmailHelper.sendMail(record);
	          newPersisted=true;
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
	          log.info("JDOUserException: Ngo table is empty");
	          // Create friends from Google+
	          pm.makePersistent(record);
	          detached = pm.detachCopy(record);	
	          newPersisted=true;
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
	    
	    // Here we create the association if it is a new persisted object 
	    if (newPersisted) {
	          NgoUser.associate(detached.getId(), userId, userId);
	          SNgo sNgo = SNgo.findOrCreate(new SNgo(detached), userId);
	          NgoStandard.associate(record.getId(), sNgo.getId(), userId);
	    }
	    
	    // Return a detached copy of the retrieved object or 
	    // the input object after being persisted
	    return detached;
	  }


		/**
		 * Creates a share object to be passed to the front end. The resulting
		 * object contains the information of the data-store object.
		 * @param o
		 * @return
		 */
		public static DBRecordInfo toInfo(Ngo o) {
			if (o == null)
				return null;

			// Create a new object with the information from the input
			DBRecordInfo oInfo = new DBRecordInfo(DBRecordInfo.ORGANIZATION, o.getName(), o.getDescription().getValue(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
			
			// Set the unique Id to the current Id from the input object
			oInfo.setUniqueId(o.getUniqueId());
			
			oInfo.deactivateMember();
			oInfo.deactivateFollower();
			
			// Include the standard information in the Info object
			// if it exists in the DB
			/*
			SNgo standard = NgoStandard.getStandard(o);
			if (standard != null){
				//oInfo.setStandard(SNgo.toInfo(standard));
			}*/
			
			return oInfo;
		}

		/**
		 * Creates a share object to be passed to the front end. The resulting
		 * object contains the information of the data-store object. The returned
		 * object privacy is set according to the input userUniqueId accessibility 
		 * to the input record. 
		 * @param o
		 * @param userUniqueId
		 * @return
		 */
		public static DBRecordInfo toInfo(Ngo o, Long userId) {
			if (o == null)
				return null;

			// Create a new object with the information from the input
			DBRecordInfo oInfo = Ngo.toInfo(o);
			
			// Check if the share object was created. If not, null should be 
			// returned instead of setting privacies. 
			if (oInfo == null)
				return null;

			// Set privacy depending on the user uniqueId
			// If the user with uniqueId id is a member or a follower
			// the appropriate attributes of the shared object
			// will be set.
			if (o.isMember(userId)) oInfo.activateMember();
			if (o.isFollower(userId)) oInfo.activateFollower();

			// Include the standard information in the Info object
			// if it exists in the DB
			/*SNgo standard = NgoStandard.getStandard(o);
			if (standard != null){
				oInfo.setStandard(SNgo.toInfo(standard));
			}*/
			
			return oInfo;
		}

		/**
		 * Returns true if the input unique Id is a member of the 
		 * record.
		 * @param userId
		 * @return
		 */
		@Override
		public boolean isMember(Long userId){
			return NgoUser.isAssociated(this.getId(), userId);
		}
}