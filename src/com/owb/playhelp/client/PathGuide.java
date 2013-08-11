/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.friend.FriendsHomePresenter;
import com.owb.playhelp.client.presenter.map.MainHomePresenter;
import com.owb.playhelp.client.presenter.map.MapMenuPresenter;
import com.owb.playhelp.client.presenter.news.NewsHomePresenter;
import com.owb.playhelp.client.presenter.ngo.AddNgoPresenter;
import com.owb.playhelp.client.presenter.ngo.ReportAbuseNgoPresenter;
import com.owb.playhelp.client.presenter.ngo.ShowDetailsNgoPresenter;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanagePresenter;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanageStatusPresenter;
import com.owb.playhelp.client.presenter.project.AddProjectPresenter;
import com.owb.playhelp.client.presenter.user.UserPreferenceEditPresenter;
import com.owb.playhelp.client.presenter.volunteer.AddVolunteerPresenter;
import com.owb.playhelp.client.presenter.web.ContactHomePresenter;
import com.owb.playhelp.client.presenter.web.WebHomePresenter;
import com.owb.playhelp.client.presenter.web.WebMenuPresenter;
import com.owb.playhelp.client.presenter.web.WebPagesPresenter;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.service.volunteer.VolunteerServiceAsync;
import com.owb.playhelp.client.service.UserServiceAsync;
import com.owb.playhelp.client.service.ngo.NgoServiceAsync;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.client.view.ContactHomeView;
import com.owb.playhelp.client.view.friend.FriendsHomeView;
import com.owb.playhelp.client.view.map.MainHomeView;
import com.owb.playhelp.client.view.map.MapMenuView;
import com.owb.playhelp.client.view.news.NewsHomeView;
import com.owb.playhelp.client.view.ngo.AddNgoView;
import com.owb.playhelp.client.view.ngo.ReportAbuseNgoView;
import com.owb.playhelp.client.view.ngo.ShowDetailsNgoView;
import com.owb.playhelp.client.view.orphanage.AddOrphanageView;
import com.owb.playhelp.client.view.orphanage.AddOrphanageStatusView;
import com.owb.playhelp.client.view.project.AddProjectView;
import com.owb.playhelp.client.view.user.UserPreferenceEditView;
import com.owb.playhelp.client.view.web.WebHowChildrenView;
import com.owb.playhelp.client.view.web.WebHowDoWeHelpView;
import com.owb.playhelp.client.view.web.WebHowIndividualsView;
import com.owb.playhelp.client.view.web.WebJoinNetworkView;
import com.owb.playhelp.client.view.web.WebMenuView;
import com.owb.playhelp.client.view.web.WebOurCommunityIndexView;
import com.owb.playhelp.client.view.web.WebTheTeamView;
import com.owb.playhelp.client.view.web.WebMainIndexView;
import com.owb.playhelp.client.view.web.aboutus.WebAboutUsIndexView;
import com.owb.playhelp.client.view.web.aboutus.WebAboutUsView;
import com.owb.playhelp.client.view.web.aboutus.WebContextView;
import com.owb.playhelp.client.view.web.aboutus.WebJoinOWBView;
import com.owb.playhelp.client.view.web.aboutus.WebOurMissionView;
import com.owb.playhelp.client.view.web.aboutus.WebOurViewView;
import com.owb.playhelp.client.view.web.home.WebHomeView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatCleanWaterView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatClothingView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatCompassionateView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatDisciplineView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatDoWeDoIndexView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatDoWeDoView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatEducationView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatExcerciseView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatFoodView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatGuidanceView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatHealthView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatHopeOfFutureView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatHygieneView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatJoyView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatLoveView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatResponsabilitiesView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatSafetyView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatShelterView;
import com.owb.playhelp.client.event.volunteer.ShowAddVolunteerEvent;
import com.owb.playhelp.client.event.volunteer.ShowAddVolunteerEventHandler;
import com.owb.playhelp.client.event.web.ContactHomeEvent;
import com.owb.playhelp.client.event.web.ContactHomeEventHandler;
import com.owb.playhelp.client.event.web.ShowWebEvent;
import com.owb.playhelp.client.event.web.ShowWebEventHandler;
import com.owb.playhelp.client.event.user.PreferencesEditEvent;
import com.owb.playhelp.client.event.user.PreferencesEditEventHandler;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEvent;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEventHandler;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEvent;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEventHandler;
import com.owb.playhelp.client.event.friend.FriendsHomeEvent;
import com.owb.playhelp.client.event.friend.FriendsHomeEventHandler;
import com.owb.playhelp.client.event.map.MainHomeEvent;
import com.owb.playhelp.client.event.map.MainHomeEventHandler;
import com.owb.playhelp.client.event.news.NewsHomeEvent;
import com.owb.playhelp.client.event.news.NewsHomeEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupDetailsNgoEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEventHandler;
import com.owb.playhelp.client.event.ngo.NgoRemoveEvent;
import com.owb.playhelp.client.event.ngo.NgoRemoveEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupDetailsNgoEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEventHandler;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEventHandler;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEventHandler;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEventHandler;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEvent;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEventHandler;
import com.owb.playhelp.client.helper.RPCCall;

/**
 * 
 * @author Miguel Charcos Llorens
 * 
 * PathGuide is the application controler. It manages the different views in the central panel and allows
 * the users to go back to the previous page when pressing the back page button. The PathGuide class 
 * has the different services as attributes. The services are defined when creating an instance of the
 * class. They are passed as input variables by the Owb class. These are used to create presenters that
 * need these services to handle the views and connect to the back end. The current user is also passed
 * by the Owb class. For instance, a new PathGuide instance is created for each user. The current user
 * is used by PathGuide to manage actions that require to be logged in and also to create presenters.
 * 
 * The PathGuide class mainly listen to events and shows the presenter that correspond to specific events.
 *
 */
public class PathGuide implements ValueChangeHandler<String>  {
	
	/*
	 * This is the event bus to which we will be listening to. The Owb main entry point
	 * pass the event bus that presenters use when they are created
	 */
	private final SimpleEventBus thePath;
	
	/*
	 * We store the current user because some visualization depnds on the user.
	 * For instance, the user settings won't be the same when the user is logged out
	 * or when there is a specific user since the view shows the name of the user 
	 */
	private final UserProfileInfo currentUser;
	
	/*
	 * Services for Ngo, Orphanages, projects and users are defined below
	 */
	private final VolunteerServiceAsync volunteerService;
	private final NgoServiceAsync ngoService;
	private final OrphanageServiceAsync orphanageService;
	private final ProjectServiceAsync projectService;
	private final UserServiceAsync userService;
	
	/*
	 * the Presenter instance will be used when creating new presenters
	 * We use the generic class since we can create different types of presenters
	 */
	Presenter presenter = null;

	/* 
	 * We store the last view so we can go back to the previous view if required
	 * This happens specially when there is a form for users or organizations to update
	 * the information and we want to go back to where the user previously was.
	 */
	private String lastView = "0";
	
	public PathGuide(UserServiceAsync userService, NgoServiceAsync ngoService, OrphanageServiceAsync orphanageService, 
			ProjectServiceAsync projectService, VolunteerServiceAsync volunteerService, SimpleEventBus thePath, UserProfileInfo currentUser){
		this.volunteerService = volunteerService;
		this.userService = userService;
		this.ngoService = ngoService;
		this.orphanageService = orphanageService;
		this.projectService = projectService;
		this.thePath = thePath;
		this.currentUser = currentUser;
		bind();
	}
	
	/*
	 * Listen to the events happening in the main bus and add a new element in the history
	 * OnValueChange manage the view when there is a change on the history.
	 */
	private void bind(){
		
		/*
		 * Handle events related to the web menu The history is updated with "web" plus the
		 * web page name that was requested. OnValueChange method manage what view 
		 * is selected depending on the name that is added to the history.
		 */
		History.addValueChangeHandler(this);
		thePath.addHandler(ShowWebEvent.TYPE, new ShowWebEventHandler(){
			public void onShowWeb(ShowWebEvent event){
				History.newItem("web"+event.getPage());
				// I should user History.newItem(lastView); to return to the last view
				// before clicking preference link. But I am not sure how to handle this yet
			}
		});
		
		/*
		 * Edit user request
		 */
		thePath.addHandler(PreferencesEditEvent.TYPE, new PreferencesEditEventHandler(){
			public void onEditPreference(PreferencesEditEvent event){
				lastView = History.getToken();
				History.newItem("edituser");
			}
		});
		
		/*
		 * User preferences were updated and we want to return to previous view.
		 */
		thePath.addHandler(UserPreferenceUpdateEvent.TYPE, new UserPreferenceUpdateEventHandler(){
			public void onUserPreferenceUpdate(UserPreferenceUpdateEvent event){
				History.newItem(lastView);
			}
		});
		

		/*
		 * User preference editing was cancelled and we want to return to previous view.
		 */
		thePath.addHandler(UserPreferenceEditCancelEvent.TYPE, new UserPreferenceEditCancelEventHandler(){
			public void onUserPreferenceEditCancel(UserPreferenceEditCancelEvent event){
				History.newItem(lastView);
			}
		});
		
		/*
		 * The user requested to see the main page of the news. 
		 */
		thePath.addHandler(NewsHomeEvent.TYPE, new NewsHomeEventHandler(){
			public void onNewsHome(NewsHomeEvent event){
				lastView = History.getToken();
				History.newItem("webgethomeItem");
			}
		});
		
		/*
		 * The user requested to see the map view
		 */
		thePath.addHandler(MainHomeEvent.TYPE, new MainHomeEventHandler(){
			public void onMainHomeRequest(MainHomeEvent event){
				lastView = History.getToken();
				History.newItem("map");
			}
		});
		
		/*
		 * The user requested to see the friend view
		 */
		thePath.addHandler(FriendsHomeEvent.TYPE, new FriendsHomeEventHandler(){
			public void onFriendsHome(FriendsHomeEvent event){
				lastView = History.getToken();
				History.newItem("friends");
			}
		});
		
		/*
		 * The user requested to see the view about our contact information
		 * I think this should be included on the left bar menu and therefore
		 * the handler would go away since it would be handled with ShowWebEvent 
		 */
		thePath.addHandler(ContactHomeEvent.TYPE, new ContactHomeEventHandler(){
			public void onContactHome(ContactHomeEvent event){
				lastView = History.getToken();
				History.newItem("contactus");
			}
		});
		

		/*
		 * Listen to an event requesting starting an organization. It will show a popup 
		 * window where the user can enter the information of the organization.
		 */
		thePath.addHandler(ShowAddVolunteerEvent.TYPE, new ShowAddVolunteerEventHandler(){
			public void onShowPopupAddVolunteer(ShowAddVolunteerEvent event){
				lastView = History.getToken();
				History.newItem("addVolunteer");
				AddVolunteerPresenter addVolunteerPresenter = new AddVolunteerPresenter(event.getVolunteer(), volunteerService,thePath,new WebJoinOWBView());
		        addVolunteerPresenter.go(Owb.get().getMainPanel());
			}
		});
		
		/*
		 * Listen to requests of pop-up view containing the details of the ngo
		 */
		thePath.addHandler(ShowPopupDetailsNgoEvent.TYPE, new ShowPopupDetailsNgoEventHandler(){
			public void onShowPopupDetailsNgo(ShowPopupDetailsNgoEvent event){
				ShowDetailsNgoPresenter showDetailsNgoPresenter = new ShowDetailsNgoPresenter(event.getNgo(),currentUser, ngoService, thePath,new ShowDetailsNgoView());
				showDetailsNgoPresenter.go(Owb.get().getMainPanel());
			}
		});
		
		/*
		 * Listen to request for writing a report of abouse about the organization.
		 * It will show a popup window where the user can enters the information
		 * about the abuse.
		 */
		thePath.addHandler(ShowPopupReportAbuseNgoEvent.TYPE, new ShowPopupReportAbuseNgoEventHandler(){
			public void onShowPopupReportAbuseNgo(ShowPopupReportAbuseNgoEvent event){
				if (currentUser == null){
					Window.alert("You must log in to report an Organization");
					return;
				}

				ReportAbuseNgoPresenter reportNgoPresenter = new ReportAbuseNgoPresenter(event.getNgo(), ngoService,thePath,new ReportAbuseNgoView(event.getClickPoint()));
				reportNgoPresenter.go(Owb.get().getMainPanel());
			}
		});
		
		/*
		 * Listen to an event requesting starting an organization. It will show a popup 
		 * window where the user can enter the information of the organization.
		 */
		thePath.addHandler(ShowPopupAddNgoEvent.TYPE, new ShowPopupAddNgoEventHandler(){
			public void onShowPopupAddNgo(ShowPopupAddNgoEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getNgo() != null){
					if (!event.getNgo().getMember()){
						Window.alert("You can't update an Organization if you are not a member ");
						return;
					}
				}
				lastView = History.getToken();
				History.newItem("addNgo");
				AddNgoPresenter addNgoPresenter = new AddNgoPresenter(event.getNgo(), ngoService,thePath,new AddNgoView());
		        addNgoPresenter.go(Owb.get().getMainPanel());
			}
		});
		

		/*
		 * Listen to an event requesting starting an orphanage. It will show in the main panel 
		 * where the user can enter the information of the orphanage.
		 */
		thePath.addHandler(ShowPopupAddOrphanageEvent.TYPE, new ShowPopupAddOrphanageEventHandler(){
			public void onShowPopupAddOrphanage(ShowPopupAddOrphanageEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getOrphanage() != null){
					if (!event.getOrphanage().getMember()){
						Window.alert("You can't update an Orphanage if you are not a member ");
						return;
					}
				}
				lastView = History.getToken();
				History.newItem("addOrphanage");
				AddOrphanagePresenter addOrphanagePresenter = new AddOrphanagePresenter(event.getOrphanage(), orphanageService,thePath,new AddOrphanageView());
				addOrphanagePresenter.go(Owb.get().getMainPanel());
			}
		});

		/*
		 * Listen to an event cancelling the creation of an orphanage or ngo
		 */
		thePath.addHandler(AddOrphanageCancelEvent.TYPE, new AddOrphanageCancelEventHandler(){
			public void onAddOrphanageCancel(AddOrphanageCancelEvent event){
				History.newItem(lastView);
			}
		});
		
		/*
		 * Listen to an event saving during the creation of an orphanage or ngo
		 */
		thePath.addHandler(AddOrphanageUpdateEvent.TYPE, new AddOrphanageUpdateEventHandler(){
			public void onAddOrphanageUpdate(AddOrphanageUpdateEvent event){
				History.newItem(lastView);
			}
		});
		
		/*
		 * Listen to events requesting a view for entering the needs of the orphanage
		 * The view will be set in the main panel
		 */
		thePath.addHandler(ShowPopupAddOrphanageStatusEvent.TYPE, new ShowPopupAddOrphanageStatusEventHandler(){
			public void onShowPopupAddOrphanageStatus(ShowPopupAddOrphanageStatusEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getOrphanage() != null){
					if (!event.getOrphanage().getMember()){
						Window.alert("You can't update the orphanage status if you are not a member ");
						return;
					}
				}
				History.newItem("addOrphanageStatus");
				AddOrphanageStatusPresenter addOrphanageStatusPresenter = new AddOrphanageStatusPresenter(event.getOrphanage(), orphanageService,thePath,new AddOrphanageStatusView());
				addOrphanageStatusPresenter.go(Owb.get().getMainPanel());
			}
		});
		
		/*
		 * Listen to event requesting adding a project. A panel appears in the main panel to allow the
		 * user to enter the information of the project.
		 */
		thePath.addHandler(ShowPopupAddProjectEvent.TYPE, new ShowPopupAddProjectEventHandler(){
			public void onShowPopupAddProject(ShowPopupAddProjectEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update a Project");
					return;
				}
				if (event.getProject() != null){
					if (!event.getProject().getMember()){
						Window.alert("You can't update an Project if you are not a member ");
						return;
					}
				}
				AddProjectPresenter addProjectPresenter = new AddProjectPresenter(event.getProject(), currentUser, projectService,thePath,new AddProjectView(event.getClickPoint()));
				addProjectPresenter.go(Owb.get().getMainPanel());
			}
		});
		
		/*
		 * Listen to an event requesting removing an organization. I am not sure why
		 * this is here instead of inside the presenter that has the remove button
		 * The main advantage would be to handle in a single place the removal if 
		 * there are more than one way to remove an organization (which there will be for sure)
		 */
		thePath.addHandler(NgoRemoveEvent.TYPE, new NgoRemoveEventHandler(){
			public void onNgoRemove(NgoRemoveEvent event){
				if (currentUser == null){
					Window.alert("You must log in to remove an Organization");
					return;
				}
				if (event.getNgo() != null){
					if (!event.getNgo().getMember()){
						Window.alert("You can't remove an Organization if you are not a member ");
						return;
					}
				}
				final NgoInfo delNgo = event.getNgo();
				new RPCCall<Void>() {
				      @Override
				      protected void callService(AsyncCallback<Void> cb) {
				    	  ngoService.removeNgo(delNgo, cb);
				      }

				      @Override
				      public void onSuccess(Void result) {
				        GWT.log("PathGuide: Ngo was removed");
				      }

				      @Override
				      public void onFailure(Throwable caught) {
				        Window.alert("Error removing Ngo...");
				      }
				    }.retry(3);
			}
		});
		
		
		/*
		thePath.addHandler(LogoutEvent.TYPE, new LogoutEventHandler(){
			public void onLogout(LogoutEvent event){
				GWT.log("PathGuide: Logout event received");
				History.newItem("loginout");
			}
		});*/
	}
	
	/**
	 * This method will fire the event of the state of the history
	 */
	public void go() {
		if ("".equals(History.getToken())) {
		    History.newItem("webgethomeItem");
		  } else {
		    History.fireCurrentHistoryState();
		  }
	}
	
	/**
	 * This method will create presenters depending on the value of the input event. The input event
	 * happens when the fireCurrentHistoryState is fired. This happens after a new item is added
	 * to the history by the bind method. 
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if (token != null) {
			Presenter presenter = null;
			if (token.equals("0")) token = "webgethomeItem";
			if (token.contains("web")) {
				//Owb.get().setWebMenu();
				WebMenuPresenter webMenuPresenter = new WebMenuPresenter(thePath, new WebMenuView());
				webMenuPresenter.go(Owb.get().getBarPanel());
				
				WebMainIndexView webMain = new WebMainIndexView();
				
				Owb.get().getMainPanel().clear();
				if (token.equals("webgethomeItem")) {
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					WebHomePresenter webHomePresenter = new WebHomePresenter(thePath, new WebHomeView());
					webHomePresenter.go(webMain.getAreaField());
		        return;
		        } 
				if (token.equals("webgetourMissionItem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebAboutUsView()).asWidget());
					
					//Owb.get().getMainPanel().add(webMain);
					//WebPagesPresenter webpagesPresenter = new WebPagesPresenter(new WebOurMissionView());
					//webpagesPresenter.go(webMain.getAreaField());
		        return;
		        } 
				if (token.equals("webgetourViewItem")) {
					Owb.get().getMainPanel().add(webMain);
					WebPagesPresenter webpagesPresenter = new WebPagesPresenter(new WebOurViewView());
					webpagesPresenter.go(webMain.getAreaField());
					
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					//Owb.get().getMainPanel().add(webMain);
					//webMain.getAreaField().add((new WebOurViewView()).asWidget());
		        return;
		        } 
				if (token.equals("webgettheTeamItem")) {
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebTheTeamView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetjoinOWBItem")) {
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebJoinOWBView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetcontextItem")) {
					webMain.getIndexField().add(new WebAboutUsIndexView());
					Owb.get().getMainPanel().add(webMain);
					WebPagesPresenter webpagesPresenter = new WebPagesPresenter(new WebContextView());
					webpagesPresenter.go(webMain.getAreaField());
		        return;
		        } 
				if (token.equals("webgetwhatDoWeDoItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatDoWeDoView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethealthItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatHealthView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetexcerciseItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatExcerciseView()).asWidget());
		        return;
		        } 
				if (token.equals("webgeteducationItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatEducationView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetfoodItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatFoodView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetcleanWaterItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatCleanWaterView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetshelterItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatShelterView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetclothingItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatClothingView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethygieneItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatHygieneView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetjoyItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatJoyView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethopeOfFutureItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatHopeOfFutureView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetloveItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatLoveView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetresponsabilitiesItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatResponsabilitiesView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetsafetyItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatSafetyView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetguidanceItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatGuidanceView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetcompassionateEnvironementItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatCompassionateView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetdisciplineItem")) {
					webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatDisciplineView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethowDoWeHelpItem")) {
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebHowDoWeHelpView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethowChildrentem")) {
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebHowChildrenView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethowIndividualsItem")) {
					webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebHowIndividualsView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetjoinNetworkItem")) {
					Owb.get().getMainPanel().add((new WebJoinNetworkView()).asWidget());
		        return;
		        } 
				return;
	        } 
			if (token.equals("edituser")) {
				//Owb.get().getMainTitle().setText("User Preferences");
				presenter = new UserPreferenceEditPresenter(currentUser, userService, thePath, new UserPreferenceEditView());
				presenter.go(Owb.get().getMainPanel());	
	        return;
	      } 
			if (token.equals("map")) {
				//MapMenuPresenter mapMenuPresenter = new MapMenuPresenter(thePath, new MapMenuView());
				//mapMenuPresenter.go(Owb.get().getBarPanel());
				WebMenuPresenter webMenuPresenter = new WebMenuPresenter(thePath, new WebMenuView());
				webMenuPresenter.go(Owb.get().getBarPanel());
				presenter = new MainHomePresenter(currentUser,ngoService,orphanageService,thePath,new MainHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("news")) {
				Owb.get().getBarPanel().clear();
				presenter = new NewsHomePresenter(currentUser,thePath,new NewsHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("friends")) {
				WebMainIndexView webMain = new WebMainIndexView();
				Owb.get().getBarPanel().clear();
				webMain.getIndexField().add(new WebOurCommunityIndexView());
				Owb.get().getMainPanel().add(webMain);
				presenter = new FriendsHomePresenter(currentUser,thePath,new FriendsHomeView());
				presenter.go(webMain.getAreaField());
	        return;
	      } 
			if (token.equals("contactus")) {
				Owb.get().getBarPanel().clear();
				presenter = new ContactHomePresenter(new ContactHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			/*
			if (token.equals("loginout")) {
				Owb.get().loggedOutView();
	        return;
	      } 
			if (token.equals("restart")) {
				Owb.get().showCurrentUserView();
	        return;
	      } */
			
		}
		//lastView = token;
	}
}
