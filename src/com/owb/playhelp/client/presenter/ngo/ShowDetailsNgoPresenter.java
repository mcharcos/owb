/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import com.google.gwt.event.shared.SimpleEventBus;
import com.owb.playhelp.client.presenter.ShowDetailsDBRecordPresenter;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;

public class ShowDetailsNgoPresenter extends ShowDetailsDBRecordPresenter {
	
	private final NgoServiceAsync ngoService;
	
	public ShowDetailsNgoPresenter(UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus,Display display) {
		super(currentUser,eventBus,display);
		this.ngoService = ngoService;
	}
	public ShowDetailsNgoPresenter(DBRecordInfo ngo, UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		super(ngo,currentUser, eventBus, display);
		this.ngoService = ngoService;
	}


}
