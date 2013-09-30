/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import java.util.HashSet;
import java.util.Set;

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
import com.owb.playhelp.client.event.ngo.NgoUpdateEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class AddDBRecordPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		HasValue<String> getNameField();
		HasValue<String> getDescField();
		HasValue<String> getAddressField();
		HasValue<String> getPhoneField();
		HasValue<String> getEmailField();
		HasValue<String> getWebField();
	}

	protected final SimpleEventBus eventBus;
	protected final Display display;
	protected DBRecordInfo record;

	public AddDBRecordPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.record = null;
	}
	public AddDBRecordPresenter(DBRecordInfo record, SimpleEventBus eventBus, Display display) {
		this(eventBus, display);
		this.record = record;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	if (!checkRequiredFields()) return;
	        	updateDBRecord();
	        	doSave();
		        eventBus.fireEvent(new DBRecordUpdateEvent(null)); 
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
		        eventBus.fireEvent(new DBRecordUpdateEvent(null)); 
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
		
		if (record == null) return;

		this.display.getNameField().setValue(this.record.getName());
		this.display.getDescField().setValue(this.record.getDescription());
		this.display.getAddressField().setValue(this.record.getAddress());
		this.display.getPhoneField().setValue(this.record.getPhone());
		this.display.getEmailField().setValue(this.record.getEmail());
		this.display.getWebField().setValue(this.record.getWebsite());
	}
	  
	  protected void updateDBRecord(){
		  double lat = -1.0;
		  double lng = -1.0;
		  //SolveAddress();
		  if (record == null){
			  record = new DBRecordInfo(DBRecordInfo.ORGANIZATION, display.getNameField().getValue().trim(),
					  	display.getDescField().getValue().trim(),
					  	display.getAddressField().getValue().trim(), lat, lng, 
					  	display.getPhoneField().getValue().trim(),
					  	display.getEmailField().getValue().trim(),
					  	display.getWebField().getValue().trim());
		  } else {
			  record.setName(display.getNameField().getValue().trim());
			  record.setDescription(display.getDescField().getValue().trim());
			  record.setAddress(display.getAddressField().getValue().trim(),lat,lng);
			  record.setPhone(display.getPhoneField().getValue().trim());
			  record.setEmail(display.getEmailField().getValue().trim());
			  record.setWebsite(display.getWebField().getValue().trim());
		  }

	  }
	  
	  protected void doSave() {
	    eventBus.fireEvent(new DBRecordUpdateEvent(record)); 
	  }

	  protected boolean checkRequiredFields(){
		  Set<String> missedKeys = new HashSet<String>(); 
		  if (display.getNameField().getValue().trim() == ""){
			  missedKeys.add("Name");
		  }
		  if (display.getEmailField().getValue().trim() == ""){
			  missedKeys.add("Email");
		  }
		  if (display.getAddressField().getValue().trim() == ""){
			  missedKeys.add("Address");
		  }
		  
		  if (missedKeys.isEmpty()) return true;
		  
		  String msg = "The following fields are required: ";
		  for (String keys:missedKeys) {
			  msg = msg+"\n    - "+keys;
		  }
		  Window.alert(msg);
		  
		  return false;
		  
	  }
	  
}
