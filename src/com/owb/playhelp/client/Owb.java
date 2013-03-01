/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client;


import com.owb.playhelp.client.event.user.LoginEvent;
import com.owb.playhelp.client.presenter.BusyIndicatorPresenter;
import com.owb.playhelp.client.presenter.TopCenterPanelPresenter;
import com.owb.playhelp.client.presenter.user.UserSettingPresenter;
import com.owb.playhelp.client.view.BusyIndicatorView;
import com.owb.playhelp.client.view.user.UserSettingView;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.service.LoginService;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.UserService;
import com.owb.playhelp.client.service.UserServiceAsync;
import com.owb.playhelp.client.service.ngo.NgoService;
import com.owb.playhelp.client.service.ngo.NgoServiceAsync;
import com.owb.playhelp.client.service.orphanage.OrphanageService;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.client.service.project.ProjectService;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Owb is the main class that will create:
 * 		- The genereal UiBinder
 * 		- The different panels of the application: 
 * 			+ actionPanel: Contains the user badge and the menu of the webpage
 * 			+ topCenterPanel: Main bar at the top of the page with a few menu items and the logo
 * 			+ centerPanel: Main area where the pages will be shown
 * 			+ statusPanel: Area on the right that we can use to show specific information about things
 * 							happening in the centerPanel, general information, adds, ...
 * 
 * Three presenters are created by Owb and associated to the different panels
 * 		- userBadgePresenter that handles the information of the user
 * 		- webMenuPresenter that handles the menu to navigate to the different web pages
 * 		- topCenterPanelPresenter that handles the top of the page
 * One presenter is not associated to any panel. The busyIndicator presenter will handle the 
 * indicator when the system is waiting for something
 * 
 * Owb also record the current user profile that will be passed when creating the presenters. The current 
 * user is set using the LoginService class. A LoginService instance is created here. Owb also creates
 * an instance of the event buse (SimpleEventBus class). 
 * 
 * In addition, Owb is in charge of creating the application controler and the services associated
 * to the different functionalities including users, organizations, orphanages, projects,... These are 
 * created by the private class initiateOWB which is called each time a user is logged in and the 
 * appropriate view is requested.
 * 
 */
public class Owb implements EntryPoint {
    
	/**
	 * 
	 * @author Miguel Charcos Llorens
	 *
	 */
	
	/*
	 * I define this here to be used on the push channel call but we may want later to 
	 * create a new class similar to Message 
	 */
	public enum Type {
	      NEW_CONTENT_AVAILABLE,
	      TEXT_MESSAGE,
	    }
	
	/**
	 * Define interfaces
	 */
	interface OwbUiBinder extends UiBinder<DockLayoutPanel, Owb> {}
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	public static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	
	SerializationStreamFactory pushServiceStreamFactory;
	
	/**
	 * Layout the window with three frameworks
	 */
	@UiField LeftPanel actionPanel;
	@UiField HorizontalPanel topCenterPanel, barPanel;
	@UiField ScrollPanel centerPanel;
	@UiField VerticalPanel statusPanel;

	RootLayoutPanel rootLayout;
    
	private UserProfileInfo currentUser;
	
	// Definition of presenters
	//private UserBadgePresenter userBadgePresenter = null;
	//private WebMenuPresenter webMenuPresenter = null;
	//private MapMenuPresenter mapMenuPresenter = null;
	private TopCenterPanelPresenter topCenterPanelPresenter = null;
	private UserSettingPresenter userSettingsPresenter = null;
	
	/**
	 * Define variables:
	 *  - eventBus creates a bus where to propagate events
	 *  - binder: not sure yet
	 */
	private SimpleEventBus thePath = new SimpleEventBus();
	BusyIndicatorPresenter busyIndicator = new BusyIndicatorPresenter(thePath, new BusyIndicatorView());
	private static final OwbUiBinder binder = GWT.create(OwbUiBinder.class);
	
	/**
	 * RPC services
	 */
	private LoginServiceAsync loginService = GWT.create(LoginService.class);
	  
	/**
	 * Create a singleton to... 
	 */
	private static Owb singleton;
	  

	/**
	 * Get singleton
	 */
	public static Owb get() {
	  return singleton;
	}
	  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		singleton = this;
		
		// set the title of the browser window
		if (Document.get() != null) {
			Document.get().setTitle("Orphanage Without Borders"); // TODO move it to properties file
		}
		showCurrentUserView();
	}

	/*
	public void doSuperUser() {
	  new RPCCall<Void>() {
	    @Override protected void callService(AsyncCallback<Void> cb) {
		  // the call returns a generic guest user if no logged in
	      loginService.doSuperUser(cb);
	    }

	    @Override public void onSuccess(Void cb) {
		      Window.alert("Super user successcully created");
	    }

	    @Override public void onFailure(Throwable caught) {
	      Window.alert("Error Creating super user --- " + caught.getMessage());
	    }
	  }.retry(3);
	} */
	
	/*
	public void loggedOutView(){
		  rootLayout = RootLayoutPanel.get();
		  rootLayout.clear();
		  LoggedOutPresenter loggedOutPresenter = new LoggedOutPresenter(thePath,new LoggedOutView());
		  loggedOutPresenter.go(rootLayout);
	}*/
	
	/**
	 * Check the current user and call the downloadCode to show the corresponding view
	 */
	private void showCurrentUserView() {
	  new RPCCall<UserProfileInfo>() {
	    @Override protected void callService(AsyncCallback<UserProfileInfo> cb) {
		  // the call returns a generic guest user if no logged in
	      loginService.getCurrentUserInfo(cb);
	    }

	    @Override public void onSuccess(UserProfileInfo userInfo) {
	    	setUser(userInfo);	
	    	downloadCode();
	    }

	    @Override public void onFailure(Throwable caught) {
	      Window.alert("Error: " + caught.getMessage());
	    }
	  }.retry(3);
	}
	
	/**
	 * Retrieve the code and if user exists (currentUser!=null) it shows the view
	 * of the user and fire and event to say that the user is logged in
	 */
	private void downloadCode(){
		GWT.runAsync(new RunAsyncCallback() {
		      @Override public void onFailure(Throwable reason) {
		        Window.alert("Code download error: " + reason.getMessage());
		      }

		      @Override public void onSuccess() {
		    	  createCurrentUserView();
		        if (currentUser != null) thePath.fireEvent(new LoginEvent(currentUser));
		      }
		    });
	}
	
	/**
	 * Clean the window and set the main layout. Then, it calls initiateOWB to initiate the views.
	 */
	private void createCurrentUserView() {	  
	  DockLayoutPanel outer = binder.createAndBindUi(this);
	  rootLayout = RootLayoutPanel.get();
	  rootLayout.clear();
	  rootLayout.add(outer);
	  
	  initiateOWB();
	}
	
	/**
	 * Creates the services for user, ngo, orphanages and projects. The main application controler is
	 * created for these services. This method also creates the user badge presenter in the actionPanel,
	 * the web menu presenter in the action panel and the top center panel presenter associated to 
	 * topCenterPanel which is the main area for showing information.
	 */
	private void initiateOWB() {
	  UserServiceAsync userService = GWT.create(UserService.class);
	  NgoServiceAsync ngoService = GWT.create(NgoService.class);
	  OrphanageServiceAsync orphanageService = GWT.create(OrphanageService.class);
	  ProjectServiceAsync projectService = GWT.create(ProjectService.class);
	  PathGuide guide = new PathGuide(userService, ngoService, orphanageService, projectService, thePath, currentUser);
	  guide.go();
	  
	  
	  //userBadgePresenter = new UserBadgePresenter(loginService, thePath, new UserBadgeView());
	  //userBadgePresenter.go(actionPanel.getProfilePanel());

	  //webMenuPresenter = new WebMenuPresenter(thePath, new WebMenuView());
	  //mapMenuPresenter = new MapMenuPresenter(thePath, new MapMenuView());

	  TopCenterPanel tcpanel = new TopCenterPanel();
	  topCenterPanelPresenter = new TopCenterPanelPresenter(thePath, tcpanel);
	  topCenterPanelPresenter.go(topCenterPanel);
	  
	  userSettingsPresenter = new UserSettingPresenter(loginService,thePath, new UserSettingView());
	  userSettingsPresenter.go(tcpanel.getUserSettings());
	  
	  }
	
	/**
	 * Returns the event bus that will be listen by the presenters. There is only one single event 
	 * bus in this application that is created by the Entry point class.
	 * 
	 * @return Event bus
	 */
	public SimpleEventBus getThePath(){
		return thePath;
	}
	
	/*
	 * Set the user profile
	 */
	private void setUser(UserProfileInfo currentUser){
		this.currentUser = currentUser;
	}
	
	/**
	 * Returns the central panel where all the views will be shown
	 * @return main panel 
	 */
	public ScrollPanel getMainPanel() {
		return centerPanel;
	}
	
	/**
	 * Returns the panel where we will set the tabs
	 * @return main panel 
	 */
	public HorizontalPanel getBarPanel() {
		return barPanel;
	}
	
	/**
	 * Returns the left panel where the information about the user, the character and the main menu
	 * will be set
	 * 
	 * @return Action Panel
	 */
	public LeftPanel getActionPanel() {
		return actionPanel;
	}
	
	
}
