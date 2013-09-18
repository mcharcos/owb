package com.owb.playhelp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.client.service.orphanage.OrphanageService;
import com.owb.playhelp.server.domain.Orphanage;
import com.owb.playhelp.server.domain.user.UserProfile;
import com.owb.playhelp.shared.DBRecordInfo;

public class OrphanageServiceImpl extends RemoteServiceServlet implements OrphanageService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6040792375246657307L;
	private static Logger logger = Logger.getLogger(OrphanageServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;

	@Override
	public DBRecordInfo updateDBRecord(DBRecordInfo orphanageInfo){

	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
		Orphanage orphanage = Orphanage.findOrCreateDBRecord(new Orphanage(orphanageInfo),user.getUniqueId());
		orphanage.reEdit(orphanageInfo);
		
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();

		// If the user is not an admin and it is not a member the ngo information could not
		// be updated
		if (!user.isAdmin()){
	      if (!orphanage.isMember(userUniqueId)) {
	    	  pm.close();
	    	  return null;
	      }
		}
		
	    if (!orphanage.isMember(userUniqueId)) return null;
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(orphanage);
				try {
			          logger.fine("starting commit");
			          pm.currentTransaction().commit();
			          logger.fine("commit was successful");
			          break;
			    } catch (JDOCanRetryException e1) {
			          if (i == (NUM_RETRIES - 1)) {
			            throw e1;
			          }
			        }
			} // end for
		}catch (Exception e) {
		      e.printStackTrace();
		      logger.warning(e.getMessage());
		      orphanageInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				orphanageInfo = null;
			}
			pm.close();
		}
		
		return orphanageInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Orphanage addedOrphanage = addOrphanage(orphanageInfo);
		
		// do something to store the information
		// probably creating a Orphanage from OrphanageInfo and
		// store it if it does not exist already
		//return Orphanage.toInfo(addedOrphanage);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DBRecordInfo> getDBRecordList(){
		//ArrayList<OrphanageInfo> orphanageList = new ArrayList<OrphanageInfo>();
		
		PersistenceManager pm = PMFactory.getNonTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		String userUniqueId = null;
		if (user != null) userUniqueId = user.getUniqueId();
		pm.close();
		

		pm = PMFactory.getNonTxnPm();
		try{
			Query dq = null;
			
			dq = pm.newQuery("select id from " + Orphanage.class.getName());			
			List<Long> foundIdOrphanages;
			foundIdOrphanages = (List<Long>) dq.execute();
			
			Orphanage foundOrphanage = null;
			DBRecordInfo orphanageInfo = null;
			ArrayList<DBRecordInfo> orphanageArray = new ArrayList<DBRecordInfo>();
			for (Long orphanageId: foundIdOrphanages){
				if (orphanageId != null){
					foundOrphanage = pm.getObjectById(Orphanage.class, orphanageId);
					//pm.deletePersistent(pm.getObjectById(Orphanage.class, orphanageId));
					orphanageInfo = Orphanage.toInfo(foundOrphanage,userUniqueId);
					orphanageArray.add(orphanageInfo);	
				}
			}
			 return orphanageArray;
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    return null;
	}

	@Override
	public void removeDBRecord(DBRecordInfo orphanageInfo){
		
		if (orphanageInfo.getUniqueId() == null) {
			logger.info("UniqueId is empty");
			return;
		}
		
		PersistenceManager pm = PMFactory.getTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		if (user == null) return;
		String userUniqueId = user.getUniqueId();
		pm.close();
		
		pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Orphanage oneResult = null, detached = null;

	    String uniqueId = orphanageInfo.getUniqueId();

	    Query q = pm.newQuery(Orphanage.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Orphanage) q.execute(uniqueId);
	        if (oneResult != null) {
	        	logger.info("Found object with uniqueId: " + uniqueId);
	        	detached = pm.detachCopy(oneResult);
	        } else {
	        	logger.info("UserProfile " + uniqueId + " does not exist and can't be removed...");
	        }
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
	          logger.info("JDOUserException: Orphanage table is empty"); 	
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
	      pm.close();
	      q.closeAll();
	    }
	    
	    // Exit if we did not find any Orphanage with these properties
	    if (oneResult == null) {
	    	return;
	    }
	    
		// If the user is not an admin and it is not a member the ngo information could not
		// be updated
		if (!user.isAdmin()){
	      if (!oneResult.isMember(userUniqueId)) {
	    	  logger.info("User " + uniqueId + " is not a member");
	    	  return;
	      }
		}
		
		// Here we know that the user is member or admin
		// so we can go ahead and remove the instance from the DB
	  	  pm = PMFactory.getTxnPm();
	  	  pm.deletePersistent(detached);
	  	  pm.close();
    	
	}
		
}
