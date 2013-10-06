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
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.presenter.AddStandardPresenter;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.map.MainHomePresenter;
import com.owb.playhelp.client.presenter.news.NewsHomePresenter;
import com.owb.playhelp.client.presenter.ngo.AddNgoPresenter;
import com.owb.playhelp.client.presenter.ngo.NgoListPresenter;
import com.owb.playhelp.client.presenter.ngo.ReportAbuseNgoPresenter;
import com.owb.playhelp.client.presenter.ngo.SNgoListPresenter;
import com.owb.playhelp.client.presenter.ngo.ShowDetailsNgoPresenter;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanagePresenter;
import com.owb.playhelp.client.presenter.orphanage.OrphanageListPresenter;
import com.owb.playhelp.client.presenter.orphanage.ShowDetailsOrphanagePresenter;
import com.owb.playhelp.client.presenter.user.UserPreferenceEditPresenter;
import com.owb.playhelp.client.presenter.volunteer.AddVolunteerPresenter;
import com.owb.playhelp.client.presenter.volunteer.VolunteerListPresenter;
import com.owb.playhelp.client.presenter.web.ContactHomePresenter;
import com.owb.playhelp.client.presenter.web.WebHomePresenter;
import com.owb.playhelp.client.presenter.web.WebMenuPresenter;
import com.owb.playhelp.client.presenter.web.WebPagesPresenter;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.client.service.OrphanageServiceAsync;
import com.owb.playhelp.client.service.StandardServiceAsync;
import com.owb.playhelp.client.service.UserServiceAsync;
import com.owb.playhelp.client.service.VolunteerServiceAsync;
import com.owb.playhelp.client.view.AddStandardView;
import com.owb.playhelp.client.view.ContactHomeView;
import com.owb.playhelp.client.view.DBRecordListView;
import com.owb.playhelp.client.view.ShowDetailsDBRecordView;
import com.owb.playhelp.client.view.StandardListView;
import com.owb.playhelp.client.view.map.MainHomeView;
import com.owb.playhelp.client.view.news.NewsHomeView;
import com.owb.playhelp.client.view.ngo.AddNgoView;
import com.owb.playhelp.client.view.ngo.ReportAbuseNgoView;
import com.owb.playhelp.client.view.orphanage.AddOrphanageView;
import com.owb.playhelp.client.view.user.UserPreferenceEditView;
import com.owb.playhelp.client.view.web.WebHowChildrenView;
import com.owb.playhelp.client.view.web.WebHowDoWeHelpView;
import com.owb.playhelp.client.view.web.WebHowIndividualsView;
import com.owb.playhelp.client.view.web.WebJoinNetworkView;
import com.owb.playhelp.client.view.web.WebMenuView;
import com.owb.playhelp.client.view.web.WebTheTeamView;
import com.owb.playhelp.client.view.web.WebMainIndexView;
import com.owb.playhelp.client.view.web.aboutus.WebAboutUsView;
import com.owb.playhelp.client.view.web.aboutus.WebContextView;
import com.owb.playhelp.client.view.web.aboutus.WebJoinOWBView;
import com.owb.playhelp.client.view.web.aboutus.WebOurViewView;
import com.owb.playhelp.client.view.web.home.WebHomeView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatCleanWaterView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatClothingView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatCompassionateView;
import com.owb.playhelp.client.view.web.whatdowedo.WebWhatDisciplineView;
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
import com.owb.playhelp.client.event.standard.ShowAddStandardEvent;
import com.owb.playhelp.client.event.standard.ShowAddStandardEventHandler;
import com.owb.playhelp.client.event.user.PreferencesEditEvent;
import com.owb.playhelp.client.event.user.PreferencesEditEventHandler;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEvent;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEventHandler;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEvent;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEventHandler;
import com.owb.playhelp.client.event.dbrecord.DBRecordUpdateEvent;
import com.owb.playhelp.client.event.dbrecord.DBRecordUpdateEventHandler;
import com.owb.playhelp.client.event.dbrecord.ShowAddDBRecordEvent;
import com.owb.playhelp.client.event.dbrecord.ShowAddDBRecordEventHandler;
import com.owb.playhelp.client.event.dbrecord.ShowDetailsDBRecordEvent;
import com.owb.playhelp.client.event.dbrecord.ShowDetailsDBRecordEventHandler;
import com.owb.playhelp.client.event.dbrecord.ShowListDBRecordEvent;
import com.owb.playhelp.client.event.dbrecord.ShowListDBRecordEventHandler;
import com.owb.playhelp.client.event.friend.FriendsHomeEvent;
import com.owb.playhelp.client.event.friend.FriendsHomeEventHandler;
import com.owb.playhelp.client.event.map.MainHomeEvent;
import com.owb.playhelp.client.event.map.MainHomeEventHandler;
import com.owb.playhelp.client.event.news.NewsHomeEvent;
import com.owb.playhelp.client.event.news.NewsHomeEventHandler;
import com.owb.playhelp.client.event.ngo.SNgoRemoveEvent;
import com.owb.playhelp.client.event.ngo.SNgoRemoveEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEventHandler;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEventHandler;
import com.owb.playhelp.client.event.orphanage.DBRecordRemoveEvent;
import com.owb.playhelp.client.event.orphanage.DBRecordRemoveEventHandler;
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
	private final StandardServiceAsync standardService;
	private final ProjectServiceAsync projectService;
	private final UserServiceAsync userService;
	private final LoginServiceAsync loginService;
	
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
			StandardServiceAsync standardService, ProjectServiceAsync projectService, VolunteerServiceAsync volunteerService, LoginServiceAsync loginService, SimpleEventBus thePath, UserProfileInfo currentUser){
		this.volunteerService = volunteerService;
		this.userService = userService;
		this.ngoService = ngoService;
		this.orphanageService = orphanageService;
		this.standardService = standardService;
		this.projectService = projectService;
		this.loginService = loginService;
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
		 * Listen to an event requesting showing list of DB records.
		 */
		thePath.addHandler(ShowListDBRecordEvent.TYPE, new ShowListDBRecordEventHandler(){
			public void onShowListDBRecord(ShowListDBRecordEvent event){
				lastView = History.getToken();
				if (event.getDBRecord().getDBType() == DBRecordInfo.ORGANIZATION){
					History.newItem("listNgo");
				}
				if (event.getDBRecord().getDBType() == DBRecordInfo.ORPHANAGE){
				History.newItem("listOrphanage");
				}
				if (event.getDBRecord().getDBType() == DBRecordInfo.VOLUNTEER){
				History.newItem("listVolunteer");
				}
			}
		});

		
		/*
		 * Listen to requests of pop-up view containing the details of the ngo
		 */
		thePath.addHandler(ShowDetailsDBRecordEvent.TYPE, new ShowDetailsDBRecordEventHandler(){
			public void onShowDetailsDBRecord(ShowDetailsDBRecordEvent event){

				if (event.getDBRecord() == null){
					return;
				}

				if (event.getDBRecord().getDBType() == DBRecordInfo.ORGANIZATION){
					History.newItem("detailsNgo");
					ShowDetailsNgoPresenter showDetailsNgoPresenter = new ShowDetailsNgoPresenter(event.getDBRecord(),currentUser, ngoService, thePath,new ShowDetailsDBRecordView());
					showDetailsNgoPresenter.go(Owb.get().getMainPanel());
				}
				if (event.getDBRecord().getDBType() == DBRecordInfo.ORPHANAGE){
					History.newItem("detailsOrphanage");
					ShowDetailsOrphanagePresenter showDetailsOrphanagePresenter = new ShowDetailsOrphanagePresenter(event.getDBRecord(),currentUser, orphanageService, thePath,new ShowDetailsDBRecordView());
					showDetailsOrphanagePresenter.go(Owb.get().getMainPanel());
				}
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
		 * Listen to an event requesting updating or adding the standard of a DB record
		 */
		thePath.addHandler(ShowAddStandardEvent.TYPE, new ShowAddStandardEventHandler(){
			public void onShowAddStandard(ShowAddStandardEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update status.");
					return;
				}
				lastView = History.getToken();
				AddStandardPresenter addStandardPresenter = new AddStandardPresenter(event.getDBRecord().getStandard(), standardService, thePath, new AddStandardView());
				addStandardPresenter.go(Owb.get().getMainPanel());
			}
		});
		
		/*
		 * Listen to an event requesting starting an organization. It will show a popup 
		 * window where the user can enter the information of the organization.
		 */
		thePath.addHandler(ShowAddDBRecordEvent.TYPE, new ShowAddDBRecordEventHandler(){
			public void onShowAddDBRecord(ShowAddDBRecordEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getDBRecord() == null){
					Window.alert("Problem selecting elemet to be updated. Contact the administrator.");
					return;
				}
				if (!event.getDBRecord().getMember()){
					Window.alert("You can't update records if you are not a member.");
					return;
				}
				lastView = History.getToken();
				if (event.getDBRecord().getDBType() == DBRecordInfo.ORGANIZATION){
					History.newItem("addNgo");
					AddNgoPresenter addNgoPresenter = new AddNgoPresenter(event.getDBRecord(), ngoService,thePath,new AddNgoView());
			        addNgoPresenter.go(Owb.get().getMainPanel());
				}

				if (event.getDBRecord().getDBType() == DBRecordInfo.ORPHANAGE){
					History.newItem("addOrphanage");
					AddOrphanagePresenter addOrphanagePresenter = new AddOrphanagePresenter(event.getDBRecord(), orphanageService,thePath,new AddOrphanageView());
					addOrphanagePresenter.go(Owb.get().getMainPanel());
				}
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
		/*thePath.addHandler(ShowPopupAddOrphanageStatusEvent.TYPE, new ShowPopupAddOrphanageStatusEventHandler(){
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
		});*/
		
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
				//AddProjectPresenter addProjectPresenter = new AddProjectPresenter(event.getProject(), currentUser, projectService,thePath,new AddProjectView(event.getClickPoint()));
				//addProjectPresenter.go(Owb.get().getMainPanel());
			}
		});

		/*
		 * The user updated or cancelled the update of the DB record
		 */
		thePath.addHandler(DBRecordUpdateEvent.TYPE, new DBRecordUpdateEventHandler(){
			public void onDBRecordUpdate(DBRecordUpdateEvent event){
				History.newItem("updateDBRecord");
			}
		});
		
		/*
		 * Listen to an event requesting removing an organization or orphanage. 
		 */
		thePath.addHandler(DBRecordRemoveEvent.TYPE, new DBRecordRemoveEventHandler(){
			public void onDBRecordRemove(DBRecordRemoveEvent event){
				if (currentUser == null){
					Window.alert("You must log in to remove a record");
					return;
				}
				if (event.getRecord() != null){
					if (!event.getRecord().getMember()){
						Window.alert("You can't remove a record if you are not a member ");
						return;
					}
				}
				
				final DBRecordInfo delDBRecord = event.getRecord();
				
				if (delDBRecord.getDBType() == DBRecordInfo.ORPHANAGE){
					removeOrphanage(delDBRecord);
				}
				if (delDBRecord.getDBType() == DBRecordInfo.ORGANIZATION){
					removeNgo(delDBRecord);
				}
			}
		});

		/*
		 * Listen to an event requesting removing a Ngo standard. 
		 */
		thePath.addHandler(SNgoRemoveEvent.TYPE, new SNgoRemoveEventHandler(){
			public void onSNgoRemove(SNgoRemoveEvent event){
				if (currentUser == null){
					Window.alert("You must log in to remove a standard");
					return;
				}
				if (event.getSNgo() != null){
					if (!event.getSNgo().getMember()){
						Window.alert("You can't remove a standard if you are not a member ");
						return;
					}
				}
				
				final StandardInfo delSNgo = event.getSNgo();
				removeSNgo(delSNgo);
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
				WebMenuPresenter webMenuPresenter = new WebMenuPresenter(thePath, loginService, new WebMenuView());
				webMenuPresenter.go(Owb.get().getBarPanel());
				
				WebMainIndexView webMain = new WebMainIndexView();
				
				Owb.get().getMainPanel().clear();
				if (token.equals("webgethomeItem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					WebHomePresenter webHomePresenter = new WebHomePresenter(thePath, new WebHomeView());
					webHomePresenter.go(webMain.getAreaField());
		        return;
		        } 
				if (token.equals("webgetourMissionItem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
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
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebTheTeamView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetjoinOWBItem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebJoinOWBView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetcontextItem")) {
					//webMain.getIndexField().add(new WebAboutUsIndexView());
					Owb.get().getMainPanel().add(webMain);
					WebPagesPresenter webpagesPresenter = new WebPagesPresenter(new WebContextView());
					webpagesPresenter.go(webMain.getAreaField());
		        return;
		        } 
				if (token.equals("webgetwhatDoWeDoItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatDoWeDoView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethealthItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatHealthView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetexcerciseItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatExcerciseView()).asWidget());
		        return;
		        } 
				if (token.equals("webgeteducationItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatEducationView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetfoodItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatFoodView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetcleanWaterItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatCleanWaterView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetshelterItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatShelterView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetclothingItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatClothingView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethygieneItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatHygieneView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetjoyItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatJoyView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethopeOfFutureItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatHopeOfFutureView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetloveItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatLoveView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetresponsabilitiesItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatResponsabilitiesView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetsafetyItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatSafetyView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetguidanceItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatGuidanceView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetcompassionateEnvironementItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatCompassionateView()).asWidget());
		        return;
		        } 
				if (token.equals("webgetdisciplineItem")) {
					//webMain.getIndexField().add(new WebWhatDoWeDoIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebWhatDisciplineView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethowDoWeHelpItem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebHowDoWeHelpView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethowChildrentem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
					Owb.get().getMainPanel().add(webMain);
					webMain.getAreaField().add((new WebHowChildrenView()).asWidget());
		        return;
		        } 
				if (token.equals("webgethowIndividualsItem")) {
					//webMain.getIndexField().add(new WebOurCommunityIndexView());
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
			if (token.equals("listOrphanage")) {
				//Owb.get().getMainTitle().setText("User Preferences");
				presenter = new OrphanageListPresenter(orphanageService,thePath,new DBRecordListView());
				presenter.go(Owb.get().getMainPanel());	
	        return;
	        } 
			if (token.equals("listNgo")) {
				//Owb.get().getMainTitle().setText("User Preferences");
				presenter = new NgoListPresenter(ngoService,thePath,new DBRecordListView());
				presenter.go(Owb.get().getMainPanel());	
				//presenter = new SNgoListPresenter(standardService,thePath,new StandardListView());
				//presenter.go(Owb.get().getMainPanel());	
	        return;
	        } 
			if (token.equals("listVolunteer")) {
				//Owb.get().getMainTitle().setText("User Preferences");
				presenter = new VolunteerListPresenter(volunteerService,thePath,new DBRecordListView());
				presenter.go(Owb.get().getMainPanel());	
	        return;
	        } 
			if (token.equals("map")) {
				//MapMenuPresenter mapMenuPresenter = new MapMenuPresenter(thePath, new MapMenuView());
				//mapMenuPresenter.go(Owb.get().getBarPanel());
				WebMenuPresenter webMenuPresenter = new WebMenuPresenter(thePath, loginService, new WebMenuView());
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
			if (token.equals("contactus")) {
				Owb.get().getBarPanel().clear();
				presenter = new ContactHomePresenter(new ContactHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("removeDBRecord")) {
				History.newItem(lastView);
	        return;
	      } 
			if (token.equals("removeSNgo")) {
				History.newItem(lastView);
	        return;
	      } 
			if (token.equals("updateDBRecord")) {
				History.newItem(lastView);
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

	private void removeOrphanage(final DBRecordInfo delRecord){
		new RPCCall<Void>() {
		      @Override
		      protected void callService(AsyncCallback<Void> cb) {
		    	  orphanageService.removeDBRecord(delRecord, cb);
		      }

		      @Override
		      public void onSuccess(Void result) {
		        GWT.log("PathGuide: Orphanage was removed");
				lastView = History.getToken();
				History.newItem("removeDBRecord");
		      }

		      @Override
		      public void onFailure(Throwable caught) {
		        Window.alert("Error removing Orphanage...");
				lastView = History.getToken();
				History.newItem("removeDBRecord");
		      }
		    }.retry(3);
	}
	private void removeNgo(final DBRecordInfo delRecord){
		new RPCCall<Void>() {
		      @Override
		      protected void callService(AsyncCallback<Void> cb) {
		    	  ngoService.removeDBRecord(delRecord, cb);
		      }

		      @Override
		      public void onSuccess(Void result) {
		        GWT.log("PathGuide: Ngo was removed");
				lastView = History.getToken();
				History.newItem("removeDBRecord");
		      }

		      @Override
		      public void onFailure(Throwable caught) {
		        Window.alert("Error removing Ngo...");
				lastView = History.getToken();
				History.newItem("removeDBRecord");
		      }
		    }.retry(3);
	}
	private void removeSNgo(final StandardInfo delRecord){
		new RPCCall<Void>() {
		      @Override
		      protected void callService(AsyncCallback<Void> cb) {
		    	  standardService.removeStandard(delRecord, cb);
		      }

		      @Override
		      public void onSuccess(Void result) {
		        GWT.log("PathGuide: SNgo was removed");
				lastView = History.getToken();
				History.newItem("removeSNgo");
		      }

		      @Override
		      public void onFailure(Throwable caught) {
		        Window.alert("Error removing SNgo...");
				lastView = History.getToken();
				History.newItem("removeSNgo");
		      }
		    }.retry(3);
	}
}
