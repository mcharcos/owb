/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.orphanage;

import com.google.gwt.event.shared.SimpleEventBus;
import com.owb.playhelp.client.presenter.ShowDetailsDBRecordPresenter;
import com.owb.playhelp.client.service.OrphanageServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;

public class ShowDetailsOrphanagePresenter extends ShowDetailsDBRecordPresenter {
	
	private final OrphanageServiceAsync orphanageService;
	
	public ShowDetailsOrphanagePresenter(UserProfileInfo currentUser, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus,Display display) {
		super(currentUser,eventBus,display);
		this.orphanageService = orphanageService;
	}
	public ShowDetailsOrphanagePresenter(DBRecordInfo ngo, UserProfileInfo currentUser, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Display display) {
		super(ngo,currentUser, eventBus, display);
		this.orphanageService = orphanageService;
	}


}
