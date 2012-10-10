/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.maps.client.geocode.Geocoder;

import com.owb.playhelp.client.event.project.ProjectUpdateEvent;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectStatusEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.client.presenter.Presenter;

public class AddProjectPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    void hide();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		HasValue<String> getNameField();
		HasValue<String> getDescField();
		HasValue<String> getWebField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private ProjectInfo project;

	private final ProjectServiceAsync projectService;
	
	public AddProjectPresenter(ProjectServiceAsync projectService,
			SimpleEventBus eventBus, Display display) {
		this.projectService = projectService;
		this.eventBus = eventBus;
		this.display = display;
		this.project = null;
	}
	public AddProjectPresenter(ProjectInfo project, UserProfileInfo currentUser, ProjectServiceAsync projectService,
			SimpleEventBus eventBus, Geocoder geocoder, Display display) {
		this(projectService, eventBus, display);
		this.project = project;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateProject();
	        	if (project.getStandard() == null){
	        		display.hide();
	        		
	        		// We activate member so we can edit the status when we call the event
	        		project.activateMember();
	        		
	        		GWT.log("ProjectAddPresenter: Firing ShowPopupAddProjectStatusEvent");
	    	        eventBus.fireEvent(new ShowPopupAddProjectStatusEvent(new ClickPoint(100,100),project)); 
	    	        return;
	        	}
	        	doSave();
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          display.hide();
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		bind();
		
		if (project == null) return;

		this.display.getNameField().setValue(this.project.getName());
		this.display.getDescField().setValue(this.project.getDescription());
		this.display.getWebField().setValue(this.project.getWebsite());
	}
	  
	  private void updateProject() {		  
          if (project == null){
			  project = new ProjectInfo(display.getNameField().getValue().trim(),
					  	display.getDescField().getValue().trim(),
					  	display.getWebField().getValue().trim());
          } else {
        	  project.setDescription(display.getDescField().getValue().trim());
        	  project.setWebsite(display.getWebField().getValue().trim());
          }
          
	  }
	  
	  private void doSave(){
	    new RPCCall<ProjectInfo>() {
	      @Override
	      protected void callService(AsyncCallback<ProjectInfo> cb) {
	    	  projectService.updateProject(project, cb);
	      }

	      @Override
	      public void onSuccess(ProjectInfo result) {
	    	  display.hide();
	        GWT.log("projectAddPresenter: Firing projectUpdateEvent");
	        eventBus.fireEvent(new ProjectUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
	  

}
