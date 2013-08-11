package com.owb.playhelp.client.presenter.web;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.owb.playhelp.client.event.web.ShowWebEvent;
import com.owb.playhelp.client.presenter.WebInfoBandPresenter;

public class WebInfoBandVolunteerPresenter extends WebInfoBandPresenter{


	public WebInfoBandVolunteerPresenter(SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
	}
	
	@Override
	protected void goButtonEvent(){
		eventBus.fireEvent(new ShowWebEvent("getjoinOWBItem"));
	}
	
}
