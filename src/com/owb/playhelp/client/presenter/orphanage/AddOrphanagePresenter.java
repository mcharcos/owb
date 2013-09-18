/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.dbrecord.DBRecordUpdateEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.client.presenter.AddDBRecordPresenter;
import com.owb.playhelp.client.presenter.Presenter;

public class AddOrphanagePresenter extends AddDBRecordPresenter {

	private final OrphanageServiceAsync orphanageService;
	
	public AddOrphanagePresenter(OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
		this.orphanageService = orphanageService;
	}
	public AddOrphanagePresenter(DBRecordInfo orphanage, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Display display) {
		this(orphanageService, eventBus, display);
		this.record = orphanage;
	}
	  
	@Override
	  protected void doSave(){
	    new RPCCall<DBRecordInfo>() {
	      @Override
	      protected void callService(AsyncCallback<DBRecordInfo> cb) {
	    	  orphanageService.updateDBRecord(record, cb);
	      }

	      @Override
	      public void onSuccess(DBRecordInfo result) {
	        GWT.log("OrphanageAddPresenter: Firing OrphanageUpdateEvent");
	        eventBus.fireEvent(new DBRecordUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving orphanage...");
	        eventBus.fireEvent(new DBRecordUpdateEvent(null)); 
	      }
	    }.retry(3);
	  }

}
