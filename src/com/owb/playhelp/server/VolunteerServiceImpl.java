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
import com.owb.playhelp.client.service.VolunteerService;
import com.owb.playhelp.server.domain.Volunteer;
import com.owb.playhelp.server.domain.user.UserProfile;
import com.owb.playhelp.shared.DBRecordInfo;

public class VolunteerServiceImpl extends RemoteServiceServlet implements VolunteerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6040792375246657307L;
	private static Logger logger = Logger.getLogger(VolunteerServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;

	@Override
	public DBRecordInfo updateDBRecord(DBRecordInfo volunteerInfo){

		Volunteer volunteer = Volunteer.findOrCreateDBRecord(new Volunteer(volunteerInfo));
		volunteer.reEdit(volunteerInfo);
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(volunteer);
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
		      volunteerInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				volunteerInfo = null;
			}
			pm.close();
		}
		
		return volunteerInfo;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DBRecordInfo> getDBRecordList(){
		PersistenceManager pm = PMFactory.getNonTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		Long userId = null;
		if (user != null) userId = user.getId();
		pm.close();

		pm = PMFactory.getNonTxnPm();
		try{
			Query dq = null;
			
			dq = pm.newQuery("select id from " + Volunteer.class.getName());			
			List<Long> foundIdVolunteers;
			foundIdVolunteers = (List<Long>) dq.execute();
			
			Volunteer foundVolunteer = null;
			DBRecordInfo volunteerInfo = null;
			ArrayList<DBRecordInfo> volunteerArray = new ArrayList<DBRecordInfo>();
			for (Long volunteerId: foundIdVolunteers){
				if (volunteerId != null){
					foundVolunteer = pm.getObjectById(Volunteer.class, volunteerId);
					volunteerInfo = Volunteer.toInfo(foundVolunteer,userId);
					volunteerArray.add(volunteerInfo);	
				}
			}
			 return volunteerArray;
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    return null;
	}

	@Override
	public void removeDBRecord(DBRecordInfo volunteerInfo){
		
		if (volunteerInfo.getUniqueId() == null) {
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
	    Volunteer oneResult = null;

	    String uniqueId = volunteerInfo.getUniqueId();

	    Query q = pm.newQuery(Volunteer.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Volunteer) q.execute(uniqueId);
	        if (oneResult != null) {
	        	logger.info("Found object with uniqueId: " + uniqueId);
	        	if (oneResult.isMember(userId)) pm.deletePersistent(oneResult);
	        	else logger.info("UserProfile " + uniqueId + " is not a member");
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
	          logger.info("JDOUserException: Volunteer table is empty"); 	
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
	}
	
		
}
