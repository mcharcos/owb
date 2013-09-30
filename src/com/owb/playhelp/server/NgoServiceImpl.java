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
import com.owb.playhelp.client.service.NgoService;
import com.owb.playhelp.server.domain.Ngo;
import com.owb.playhelp.server.domain.SNgo;
import com.owb.playhelp.server.domain.user.UserProfile;
import com.owb.playhelp.shared.DBRecordInfo;

public class NgoServiceImpl extends RemoteServiceServlet implements NgoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6040792375246657307L;
	private static Logger logger = Logger.getLogger(NgoServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;

	@Override
	public DBRecordInfo updateDBRecord(DBRecordInfo record){
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) {
	    	pm.close();
	    	return null;
	    }
	    pm.close();

	    Long userId = user.getId();
		Ngo ngo = Ngo.findOrCreateDBRecord(new Ngo(record),userId);
		ngo.reEdit(record);
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
		// If the user is not an admin and it is not a member the ngo information could not
		// be updated
		if (!user.isAdmin()){
	      if (!ngo.isMember(userId)) {
	    	  pm.close();
	    	  return null;
	      }
		}
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(ngo);
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
		      record = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				record = null;
			}
			pm.close();
		}
		
		
		// Update the standard information
		SNgo standard = SNgo.findOrCreate(new SNgo(record),userId);
		standard.add(record.getStandard());

		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(standard);
				try {
			          logger.fine("starting commit for standard");
			          pm.currentTransaction().commit();
			          logger.fine("commit was successful for standard");
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
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback for standard");
			}
			pm.close();
		}
		
		return record;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DBRecordInfo> getDBRecordList(){
		PersistenceManager pm = PMFactory.getNonTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		Long userId = null;
		boolean isAdmin = false;
		if (user != null) {
			userId = user.getId();
			isAdmin = user.isAdmin();
		}
		pm.close();

		pm = PMFactory.getNonTxnPm();
		try{
			Query dq = null;
			
			dq = pm.newQuery("select id from " + Ngo.class.getName());			
			List<Long> foundIdNgos;
			foundIdNgos = (List<Long>) dq.execute();
			
			Ngo foundNgo = null;
			DBRecordInfo record = null;
			ArrayList<DBRecordInfo> ngoArray = new ArrayList<DBRecordInfo>();
			for (Long ngoId: foundIdNgos){
				if (ngoId != null){
					foundNgo = pm.getObjectById(Ngo.class, ngoId);
					record = Ngo.toInfo(foundNgo,userId);
					if (isAdmin) record.activateMember();
					ngoArray.add(record);	
				}
			}
			 return ngoArray;
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    return null;
	}
	

	@Override
	public void removeDBRecord(DBRecordInfo record){
		
		if (record.getUniqueId() == null) {
			logger.info("UniqueId is empty");
			return;
		}
		
		PersistenceManager pm = PMFactory.getTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		if (user == null) return;
		Long userId = user.getId();
		pm.close();
		
		pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Ngo oneResult = null, detached = null;

	    String uniqueId = record.getUniqueId();

	    Query q = pm.newQuery(Ngo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Ngo) q.execute(uniqueId);
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
	          logger.info("JDOUserException: Ngo table is empty"); 	
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
	      if (!oneResult.isMember(userId)) {
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
