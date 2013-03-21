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
import com.owb.playhelp.client.service.volunteer.VolunteerService;
import com.owb.playhelp.server.domain.ConfirmationBadge;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.domain.volunteer.Volunteer;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

public class VolunteerServiceImpl extends RemoteServiceServlet implements VolunteerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2351679826873531508L;
	private static Logger logger = Logger.getLogger(VolunteerServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;
  
	@Override
	public VolunteerInfo requestMemberVolunteer(VolunteerInfo volunteerInfo){

		Volunteer volunteer = Volunteer.findOrCreateVolunteer(new Volunteer(volunteerInfo));
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    
        
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
		/*
	    if (volunteer.getMembers().size() == 0){
			volunteer.addMember(userUniqueId);
	    }*/
		
	    if (volunteer.isMember(userUniqueId)) return null;
	    
	    //volunteer.requestMember(userUniqueId);
	    
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
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Volunteer addedVolunteer = addVolunteer(volunteerInfo);
		
		// do something to store the information
		// probably creating a Volunteer from VolunteerInfo and
		// store it if it does not exist already
		//return Volunteer.toInfo(addedVolunteer);
	}
	
	
	@Override
	public VolunteerInfo updateVolunteer(VolunteerInfo volunteerInfo){

		Volunteer volunteer = Volunteer.findOrCreateVolunteer(new Volunteer(volunteerInfo));
		if (volunteer == null) return volunteerInfo;
		volunteer.reEdit(volunteerInfo);
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
		/*
	    if (volunteer.getMembers().size() == 0){
			volunteer.addMember(userUniqueId);
	    }*/
		
	    if (!volunteer.isMember(userUniqueId)) return null;
	    
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
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Volunteer addedVolunteer = addVolunteer(volunteerInfo);
		
		// do something to store the information
		// probably creating a Volunteer from VolunteerInfo and
		// store it if it does not exist already
		//return Volunteer.toInfo(addedVolunteer);
	}
	
    @Override
    public VolunteerInfo reportAbuseVolunteer(VolunteerInfo volunteerInfo, String report){

		Volunteer volunteer = Volunteer.findOrCreateVolunteer(new Volunteer(volunteerInfo));
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
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
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Volunteer addedVolunteer = addVolunteer(volunteerInfo);
		
		// do something to store the information
		// probably creating a Volunteer from VolunteerInfo and
		// store it if it does not exist already
		//return Volunteer.toInfo(addedVolunteer);
	}
	
    @Override
    public VolunteerInfo confirmVolunteer(VolunteerInfo volunteerInfo){

		Volunteer volunteer = Volunteer.findOrCreateVolunteer(new Volunteer(volunteerInfo));
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		//volunteer.addConfirmation(user);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
				
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
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
			    
        // Update the volunteerInfo that will be returned
        if (volunteerInfo != null) volunteerInfo = Volunteer.toInfo(volunteer,user.getUniqueId());
		return volunteerInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Volunteer addedVolunteer = addVolunteer(volunteerInfo);
		
		// do something to store the information
		// probably creating a Volunteer from VolunteerInfo and
		// store it if it does not exist already
		//return Volunteer.toInfo(addedVolunteer);
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<VolunteerInfo> getVolunteerList(){
		//ArrayList<VolunteerInfo> volunteerList = new ArrayList<VolunteerInfo>();
		
		PersistenceManager pm = PMFactory.getNonTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		String userUniqueId = null;
		if (user != null) userUniqueId = user.getUniqueId();
		pm.close();

		pm = PMFactory.getNonTxnPm();
		try{
			Query dq = null;
			
			dq = pm.newQuery("select id from " + Volunteer.class.getName());			
			List<Long> foundIdVolunteers;
			foundIdVolunteers = (List<Long>) dq.execute();
			
			Volunteer foundVolunteer = null;
			VolunteerInfo volunteerInfo = null;
			ArrayList<VolunteerInfo> volunteerArray = new ArrayList<VolunteerInfo>();
			for (Long volunteerId: foundIdVolunteers){
				if (volunteerId != null){
					foundVolunteer = pm.getObjectById(Volunteer.class, volunteerId);
					//pm.deletePersistent(pm.getObjectById(Volunteer.class, volunteerId));
					volunteerInfo = Volunteer.toInfo(foundVolunteer,userUniqueId);
					if (user != null){
						if (volunteerInfo.getConfirmed() || user.isAdmin() || foundVolunteer.isMember(userUniqueId)){
							volunteerArray.add(volunteerInfo);
						}
					} else {
						if (volunteerInfo.getConfirmed()){
							volunteerArray.add(volunteerInfo);
						}
					}
											
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
	public void removeVolunteer(VolunteerInfo volunteerInfo){
		
		if (volunteerInfo.getUniqueId() == null) {
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
	        	if (oneResult.isMember(userUniqueId)) pm.deletePersistent(oneResult);
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
