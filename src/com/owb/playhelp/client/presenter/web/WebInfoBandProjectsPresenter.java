package com.owb.playhelp.client.presenter.web;

import com.google.gwt.event.shared.SimpleEventBus;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.presenter.WebInfoBandPresenter;

public class WebInfoBandProjectsPresenter extends WebInfoBandPresenter{


	public WebInfoBandProjectsPresenter(SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
	}
	
	@Override
	protected void goButtonEvent(){
		eventBus.fireEvent(new ShowPopupAddOrphanageEvent(new ClickPoint(100,100)));
	}
	
}
