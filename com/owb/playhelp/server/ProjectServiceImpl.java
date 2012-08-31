/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.domain.project.Project;
import com.owb.playhelp.server.domain.project.ProjectItem;
import com.owb.playhelp.client.service.project.ProjectService;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.shared.project.ProjectItemInfo;

@SuppressWarnings("serial")
public class ProjectServiceImpl extends RemoteServiceServlet implements ProjectService {
	
	private static Logger logger = Logger.getLogger(ProjectServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;

	@Override
	public ProjectInfo updateProject(String uniqueId, ProjectInfo projectInfo){

		Project addedProject = addProject(uniqueId, projectInfo);
		
		// do something to store the information
		// probably creating a Project from ProjectInfo and
		// store it if it does not exist already
		return Project.toInfo(addedProject);
	}
		
	@Override
	public ProjectInfo getProject(String id){
		ProjectInfo fakeProjectInfo = new ProjectInfo();
		return fakeProjectInfo;
	}
	
	@Override
	public String deleteProject(String id) throws NoUserException {
		// should delete the project
		return "projectDeleted";
	}
	private Project addProject(String uniqueId, ProjectInfo projectInfo){
		PersistenceManager pm = PMFactory.getTxnPm();
		Project project = null;
		try{
				pm.currentTransaction().begin();
				project = new Project(uniqueId,projectInfo);
				pm.makePersistent(project);
				try{
					pm.currentTransaction().commit();
				} catch (JDOCanRetryException e1){
					throw e1;
				}
			
		}catch (Exception e) {
			project = null;
		}finally{
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				project = null;
			}
			pm.close();
		}
		return project;
	}
	

	public ArrayList<ProjectItemInfo> getUserProjectList(){
		ArrayList<ProjectItemInfo> projectInfoList = new ArrayList<ProjectItemInfo>();
		PersistenceManager pm = PMFactory.getNonTxnPm();
		try{
		      UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		      if (user == null)
		        return null;
		      
		      Set<ProjectItem> projects = user.getProjects();
		      
		      if (projects == null) return null;
		      for (ProjectItem project:projects){
		    	  projectInfoList.add(project.toInfo());
		      }
		}// end try
	    finally {
	        pm.close();
	      }
	    return projectInfoList;
	}

	public ArrayList<ProjectInfo> getProjectList(){
		ArrayList<ProjectInfo> projectList = new ArrayList<ProjectInfo>();
		PersistenceManager pm = PMFactory.getNonTxnPm();
		try{
			String qstring = null;
			Query dq = null;
			qstring = "";
			
			dq = pm.newQuery("select id from " + Project.class.getName());			
			List<Long> foundIdProjects;
			foundIdProjects = (List<Long>) dq.execute();
			
			Project foundProject = null;
			ArrayList<ProjectInfo> projArray = new ArrayList<ProjectInfo>();
			for (Long projectId: foundIdProjects){
				if (projectId != null){
					foundProject = pm.getObjectById(Project.class, projectId);
					projArray.add(Project.toInfo(foundProject));					
				}
			}
			 return projArray;
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    return null;
	}
		
	private ProjectItem addUserProject(ProjectItemInfo projectItemInfo){
		
		PersistenceManager pm = PMFactory.getTxnPm();
		ProjectItem projectItem = null;
		try{
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				UserProfile currentUser = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
				projectItem = new ProjectItem(projectItemInfo);
				currentUser.getProjects().add(projectItem);
				pm.makePersistent(currentUser);
				try{
					logger.fine("commiting...");
					pm.currentTransaction().commit();
					logger.fine("commited!");
					break;
				} catch (JDOCanRetryException e1){
					if (i == (NUM_RETRIES - 1)) {
			            throw e1;
			          }
				}
			} // endfor 
			
		}catch (Exception e){
			logger.warning(e.getMessage());
			projectItem = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				projectItem = null;
				logger.warning("did transaction rollback");
			}
			pm.close();
		}
		return projectItem;
	}
}

