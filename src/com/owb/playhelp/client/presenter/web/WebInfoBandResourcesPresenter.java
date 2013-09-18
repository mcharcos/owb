package com.owb.playhelp.client.presenter.web;

import com.google.gwt.event.shared.SimpleEventBus;
import com.owb.playhelp.client.event.dbrecord.ShowAddDBRecordEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.presenter.WebInfoBandPresenter;

public class WebInfoBandResourcesPresenter extends WebInfoBandPresenter{


	public WebInfoBandResourcesPresenter(SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
	}
	
	@Override
	protected void goButtonEvent(){
		eventBus.fireEvent(new ShowAddDBRecordEvent());
	}
	
}
