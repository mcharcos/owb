package com.owb.playhelp.client.presenter.web;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.volunteer.ShowAddVolunteerEvent;
import com.owb.playhelp.client.event.web.ShowWebEvent;
import com.owb.playhelp.client.event.dbrecord.ShowAddDBRecordEvent;
import com.owb.playhelp.client.event.dbrecord.ShowListDBRecordEvent;
import com.owb.playhelp.client.event.map.MainHomeEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.resources.Resources;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;


public class WebMenuPresenter  implements Presenter {
	public interface Display {
		Widget asWidget();
		MenuBar getmainMenu();
		MenuItem gethomeItem();
		MenuItem getaboutUsItem();
		MenuItem getourMissionItem();
		MenuItem getourViewItem();
		MenuItem gettheTeamItem() ;
		MenuItem getjoinOWBItem() ;
		MenuItem getresourcesItem();
		MenuItem getjoinNetworkItem();
		MenuItem getsearchResourcesItem();
		MenuItem getneedsItem();
		MenuItem getshareProjectItem();
		MenuItem getsearchProjectsItem();
		MenuItem getmapItem();
		ImageElement getaboutUsImg();
		MenuItem getInternalMenu();
		MenuItem getVolunteerItem();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LoginServiceAsync loginService;

	public WebMenuPresenter(SimpleEventBus eventBus, LoginServiceAsync loginService, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.loginService = loginService;
		
		//LatLng cawkerCity = LatLng.newInstance(39.509, -98.434); 
        //this.map = new MapWidget(cawkerCity, 2); 
	}


	public void bind() {
		this.display.getVolunteerItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowListDBRecordEvent(new DBRecordInfo(DBRecordInfo.VOLUNTEER)));
			}
		});
		this.display.gethomeItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethomeItem"));
			}
		});
		/*
		this.display.getaboutUsItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getaboutUsItem"));
			}
		}); */
		
		this.display.getourMissionItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getourMissionItem"));
			}
		});
		/*this.display.getourViewItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getourViewItem"));
			}
		});*/
		this.display.gettheTeamItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gettheTeamItem"));
			}
		});
		this.display.getjoinOWBItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowAddVolunteerEvent());
			}
		});
		this.display.getjoinNetworkItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowAddDBRecordEvent(new DBRecordInfo(DBRecordInfo.ORGANIZATION)));
			}
		});
		this.display.getsearchResourcesItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowListDBRecordEvent(new DBRecordInfo(DBRecordInfo.ORGANIZATION)));
			}
		});
		this.display.getshareProjectItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowAddDBRecordEvent(new DBRecordInfo(DBRecordInfo.ORPHANAGE)));
			}
		});
		this.display.getsearchProjectsItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowListDBRecordEvent(new DBRecordInfo(DBRecordInfo.ORPHANAGE)));
			}
		});
		this.display.getmapItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new MainHomeEvent());
			}
		});
		/*
		this.display.getcontextItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getcontextItem"));
			}
		});
		this.display.getwhatDoWeDoItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getwhatDoWeDoItem"));
			}
		});
		this.display.gethealthItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethealthItem"));
			}
		});
		this.display.getexcerciseItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getexcerciseItem"));
			}
		});
		this.display.geteducationItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("geteducationItem"));
			}
		});
		this.display.getfoodItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getfoodItem"));
			}
		});
		this.display.getcleanWaterItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getcleanWaterItem"));
			}
		});
		this.display.getshelterItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getshelterItem"));
			}
		});
		this.display.getclothingItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getclothingItem"));
			}
		});
		this.display.gethygieneItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethygieneItem"));
			}
		});
		this.display.getjoyItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getjoyItem"));
			}
		});
		this.display.gethopeOfFutureItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethopeOfFutureItem"));
			}
		});
		this.display.getloveItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getloveItem"));
			}
		});
		this.display.getresponsabilitiesItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getresponsabilitiesItem"));
			}
		});
		this.display.getsafetyItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getsafetyItem"));
			}
		});
		this.display.getguidanceItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getguidanceItem"));
			}
		});
		this.display.getcompassionateEnvironementItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getcompassionateEnvironementItem"));
			}
		});
		this.display.getdisciplineItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("getdisciplineItem"));
			}
		});
		this.display.gethowDoWeHelpItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethowDoWeHelpItem"));
			}
		});
		this.display.gethowChildrenItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethowChildrentem"));
			}
		});
		this.display.gethowOrganizationsItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethowOrganizationsItem"));
			}
		});
		this.display.gethowProjectsItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethowProjectsItem"));
			}
		});
		this.display.gethowIndividualsItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("gethowIndividualsItem"));
			}
		});
		*/
	}

	final Image icon = new Image(Resources.INSTANCE.progressBar());
	final String image = "<img src='"+icon.getUrl() + "' height='25px' width='25px'/>";

    SafeHtml addActivityImagePath = new SafeHtml() {
        @Override
        public String asString() {
            return image;
        }
    };
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		WebMenuPresenter.this.display.getmainMenu().setAutoOpen(true);
		
		this.display.getInternalMenu().setVisible(false);
		new RPCCall<UserProfileInfo>() {
		    @Override protected void callService(AsyncCallback<UserProfileInfo> cb) {
			  // the call returns a generic guest user if no logged in
		      loginService.getCurrentUserInfo(cb);
		    }

		    @Override public void onSuccess(UserProfileInfo userInfo) {
		    	if (userInfo==null) {
		    		bind();
		    		return;
		    	}
		    	if (userInfo.getIsAdmin()){
		    		display.getInternalMenu().setVisible(true);
		    	}
	    		bind();
		    }

		    @Override public void onFailure(Throwable caught) {
		      Window.alert("Error: " + caught.getMessage());
				bind();
		    }
		  }.retry(3);
		  
		//WebMenuPresenter.this.display.getmainMenu().addItem(new MenuItem(addActivityImagePath,WebMenuPresenter.this.display.getmainMenu()));
	}
	
}
