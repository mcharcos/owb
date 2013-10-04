package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;

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

	  //private static final long serialVersionUID = -2023204547641864687L;
	
	public SNgo(){
		super();
	}
	
	public SNgo(DBRecord record){
		super(record);
	}
	
	public SNgo(Ngo record){
		this();
		this.setName(record.getName());
		this.setDescription(record.getDescription());
		this.setAddress(record.getAddress());
		this.setLatitude(record.getLatitude());
		this.setLongitude(record.getLongitude());

		// Complete standard information from record
		// We will have to check if it already existed 
		// an instance in the DB for this record (probably by checking the uniqueId)
		// Since this is going to be something that we need more than once, 
		// we should create the method to perform the task.
	}

	public SNgo(DBRecordInfo record){
		super(record);
		
		// Complete standard information from record
		// We will have to check if it already existed 
		// an instance in the DB for this record (probably by checking the uniqueId of NgoInfo and then id of Ngo
		// matching this uniqueId)
		// Since this is going to be something that we need more than once, 
		// we should create the method to perform the task.
	}

	public SNgo(StandardInfo record){
		super(record);
	}

	/**
	 * Retrieve the user from the database if it already exist or
	 * create a new account if it is the first loggin
	 * @param Standard
	 * @return
	 */
	  public static SNgo findOrCreate(SNgo record, Long userId) {
	
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
	          log.info("JDOUserException: SNgo table is empty");
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
	  

		/**
		 * Creates a share object to be passed to the front end. The resulting
		 * object contains the information of the data-store object.
		 * @param o
		 * @return
		 */
		public static StandardInfo toInfo(SNgo o, Date date) {
			if (o == null)
				return null;

			// Create a new object with the information from the input
			StandardInfo oInfo = new StandardInfo(StandardInfo.ORGANIZATION, 
												  o.getWater(date), o.getFood(date),o.getShelter(date),o.getClothing(date),
					                              o.getMedicine(date),o.getHygiene(date),o.getSafety(date),o.getActivity(date),
					                              o.getEducation(date),o.getGuidance(date),o.getResponsibility(date),o.getDiscipline(date),
					                              o.getLove(date),o.getCompassion(date),o.getJoy(date),o.getHope(date));
			
			// Set the unique Id to the current Id from the input object
			oInfo.setUniqueId(o.getUniqueId());
			
			oInfo.deactivateMember();
			
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
		public static StandardInfo toInfo(SNgo o, Long userId, Date date) {
			if (o == null)
				return null;

			// Create a new object with the information from the input
			StandardInfo oInfo = SNgo.toInfo(o, date);
			
			// Check if the share object was created. If not, null should be 
			// returned instead of setting privacies. 
			if (oInfo == null)
				return null;

			// Set privacy depending on the user uniqueId
			// If the user with uniqueId id is a member or a follower
			// the appropriate attributes of the shared object
			// will be set.
			if (o.isMember(userId)) oInfo.activateMember();
			
			return oInfo;
		}
		

		/**
		 * Creates a share object to be passed to the front end. The resulting
		 * object contains the information of the data-store object.
		 * @param o
		 * @return
		 */
		public static StandardInfo toInfo(SNgo o) {
			if (o == null)
				return null;

			Date date = new Date();
			// Create a new object with the information from the input
			StandardInfo oInfo = new StandardInfo(StandardInfo.ORGANIZATION, 
												  o.getWater(date), o.getFood(date),o.getShelter(date),o.getClothing(date),
					                              o.getMedicine(date),o.getHygiene(date),o.getSafety(date),o.getActivity(date),
					                              o.getEducation(date),o.getGuidance(date),o.getResponsibility(date),o.getDiscipline(date),
					                              o.getLove(date),o.getCompassion(date),o.getJoy(date),o.getHope(date));
			
			// Set the unique Id to the current Id from the input object
			oInfo.setUniqueId(o.getUniqueId());
			
			oInfo.deactivateMember();
			
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
		public static StandardInfo toInfo(SNgo o, Long userId) {
			if (o == null)
				return null;

			// Create a new object with the information from the input
			StandardInfo oInfo = SNgo.toInfo(o);
			oInfo.setDBType(StandardInfo.ORGANIZATION);
			
			// Check if the share object was created. If not, null should be 
			// returned instead of setting privacies. 
			if (oInfo == null)
				return null;

			// Set privacy depending on the user uniqueId
			// If the user with uniqueId id is a member or a follower
			// the appropriate attributes of the shared object
			// will be set.
			if (o.isMember(userId)) oInfo.activateMember();
			
			return oInfo;
		}
}
