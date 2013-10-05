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
import com.owb.playhelp.client.service.StandardService;
import com.owb.playhelp.server.domain.SNgo;
import com.owb.playhelp.server.domain.user.UserProfile;
import com.owb.playhelp.shared.StandardInfo;

public class StandardServiceImpl extends RemoteServiceServlet implements StandardService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6040792375246657307L;
	private static Logger logger = Logger.getLogger(StandardServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;

	@Override
	public StandardInfo updateStandard(StandardInfo stdInfo){
		logger.info("------------------- Starting standard update ---------------------------");
		
		if (stdInfo == null){
			logger.warning("Input information is null");
			return null;
		}
		logger.info("Standard type is "+stdInfo.getDBType().toString());
	    StandardInfo result = stdInfo;
	    
	    if (stdInfo.getDBType() != StandardInfo.ORGANIZATION &&
	    	stdInfo.getDBType() != StandardInfo.ORPHANAGE	){
	    	logger.fine("Type "+stdInfo.getDBType()+" is not recognized");
	    	return result;
	    }
	    if (stdInfo.getDBType() == StandardInfo.ORGANIZATION){
		    logger.fine("Request update of type Ngo -> updateStdNgo");
	    	result = updateStdNgo(stdInfo);
	    }
	    return result;
	}
	
	private StandardInfo updateStdNgo(StandardInfo stdInfo){
		logger.info("------------------- Starting Ngo standard update ---------------------------");
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) {
	    	logger.warning("User is not logged in");
	    	pm.close();
	    	return null;
	    }
	    pm.close();
	    
	    Long userId = user.getId();

	    SNgo standard = SNgo.findOrCreate(new SNgo(stdInfo),userId);
	    
	    logger.fine("Standard unique id: "+standard.getUniqueId());
	    
	    // Here there is a problem updating the standard. It is like 
	    // the standard embedded AreaStandard attributes where not detached
	    // or may be the standard. The funny thing is that it only sends the error message
	    // for the first attribute it tries to update. And within standard.add, the attributes
	    // are updated.
	    // The warning is:
	    // WARNING: You have just attempted to access field "water" yet this field was not detached when you detached the object. Either dont access this field, or detach it when detaching the object.
	    try{
	    	standard.add(stdInfo);
	    }catch (Exception e) {
	    	logger.warning(e.getMessage());
	    }
		
	    
	    pm = PMFactory.getTxnPm();
		
		// If the user is not an admin and it is not a member the ngo information could not
		// be updated
		if (!user.isAdmin()){
	      if (!standard.isMember(userId)) {
	    	  pm.close();
	    	  return null;
	      }
		}
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(standard);
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
		      stdInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				stdInfo = null;
			}
			pm.close();
		}
		
		return stdInfo;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<StandardInfo> getNgoList(){
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
			
			logger.info("Retrieving ids from "+SNgo.class.getName());
			dq = pm.newQuery("select id from " + SNgo.class.getName());			
			List<Long> foundIdNgos;
			foundIdNgos = (List<Long>) dq.execute();
			
			SNgo foundNgo = null;
			StandardInfo record = null;
			ArrayList<StandardInfo> ngoArray = new ArrayList<StandardInfo>();
			for (Long ngoId: foundIdNgos){
				if (ngoId != null){
					foundNgo = pm.getObjectById(SNgo.class, ngoId);
					record = SNgo.toInfo(foundNgo,userId);
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
	public void removeStandard(StandardInfo standard){
		
		if (standard.getUniqueId() == null) {
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
	    SNgo oneResult = null, detached = null;

	    String uniqueId = standard.getUniqueId();

	    Query q = pm.newQuery(SNgo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (SNgo) q.execute(uniqueId);
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
