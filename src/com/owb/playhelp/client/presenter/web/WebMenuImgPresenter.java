package com.owb.playhelp.client.presenter.web;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.web.ShowWebEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.helper.ClickPoint;


public class WebMenuImgPresenter  implements Presenter {
	public interface Display {
		Widget asWidget();
		//HorizontalPanel getBarPanel();
		MenuItem getAddNgoItem();
		MenuItem getAddOrphanageItem();
		MenuItem getMissionItem();
		MenuItem getGoalsItem();
		MenuItem getDifferentItem();
		MenuItem getProblemSolutionItem();
		MenuItem getTeamItem();
		MenuItem getJoinusItem();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	public WebMenuImgPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		
		//LatLng cawkerCity = LatLng.newInstance(39.509, -98.434); 
        //this.map = new MapWidget(cawkerCity, 2); 
	}


	public void bind() {
		this.display.getAddNgoItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowPopupAddNgoEvent(new ClickPoint(100,100)));
			}
		});
		this.display.getAddOrphanageItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowPopupAddOrphanageEvent(new ClickPoint(100,100)));
			}
		});
		this.display.getMissionItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("mission"));
			}
		});
		this.display.getGoalsItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("goals"));
			}
		});
		this.display.getDifferentItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("different"));
			}
		});
		this.display.getProblemSolutionItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("problemsolution"));
			}
		});
		this.display.getTeamItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("team"));
			}
		});
		this.display.getJoinusItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowWebEvent("joinus"));
			}
		});
		
		/*this.display.getAddProjectField().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ProjectAddEvent());
			}
		});*/
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
}
