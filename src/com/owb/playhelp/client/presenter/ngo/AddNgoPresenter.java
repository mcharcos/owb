/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.owb.playhelp.client.event.dbrecord.DBRecordUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.client.presenter.AddDBRecordPresenter;

public class AddNgoPresenter extends AddDBRecordPresenter {

	//private UserProfileInfo currentUser;
	private final NgoServiceAsync ngoService;

	public AddNgoPresenter(NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
		this.ngoService = ngoService;
	}
	public AddNgoPresenter(DBRecordInfo ngo, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		this(ngoService, eventBus, display);
		this.record = ngo;
	}

	  @Override
	  protected void doSave() {
	    new RPCCall<DBRecordInfo>() {
	      @Override
	      protected void callService(AsyncCallback<DBRecordInfo> cb) {
	    	  ngoService.updateDBRecord(record, cb);
	      }

	      @Override
	      public void onSuccess(DBRecordInfo result) {
	        GWT.log("NgoAddPresenter: Firing NgoUpdateEvent");
	        eventBus.fireEvent(new DBRecordUpdateEvent(result));
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving ngo...");
	        eventBus.fireEvent(new DBRecordUpdateEvent(null)); 
	      }
	    }.retry(3);
	  }
	  
}
