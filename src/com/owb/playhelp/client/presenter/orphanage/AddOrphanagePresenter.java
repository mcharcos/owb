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

import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.client.presenter.Presenter;

public class AddOrphanagePresenter implements Presenter {
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

	private final SimpleEventBus eventBus;
	private final Display display;
	private DBRecordInfo orphanage;

	private final OrphanageServiceAsync orphanageService;
	
	public AddOrphanagePresenter(OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Display display) {
		this.orphanageService = orphanageService;
		this.eventBus = eventBus;
		this.display = display;
		this.orphanage = null;
	}
	public AddOrphanagePresenter(DBRecordInfo orphanage, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Display display) {
		this(orphanageService, eventBus, display);
		this.orphanage = orphanage;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateOrphanage();
	        	/*
	        	if (orphanage.getStandard() == null){
	        		
	        		// We activate member so we can edit the status when we call the event
	        		orphanage.activateMember();
	        		
	        		GWT.log("OrphanageAddPresenter: Firing ShowPopupAddOrphanageStatusEvent");
	    	        eventBus.fireEvent(new ShowPopupAddOrphanageStatusEvent(new ClickPoint(100,100),orphanage));
	    	        return;
	        	}*/
        		eventBus.fireEvent(new AddOrphanageUpdateEvent());
	        	doSave();
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new AddOrphanageCancelEvent());
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
		
		if (orphanage == null) return;

		this.display.getNameField().setValue(this.orphanage.getName());
		this.display.getDescField().setValue(this.orphanage.getDescription());
		this.display.getAddressField().setValue(this.orphanage.getAddress());
		this.display.getPhoneField().setValue(this.orphanage.getPhone());
		this.display.getEmailField().setValue(this.orphanage.getEmail());
		this.display.getWebField().setValue(this.orphanage.getWebsite());
	}
	  
	  private void updateOrphanage() {
		  
		  double lat = -1.0;
		  double lng = -1.0;
		  
          if (orphanage == null){
			  orphanage = new OrphanageInfo(display.getNameField().getValue().trim(),
					  	display.getDescField().getValue().trim(),
					  	display.getAddressField().getValue().trim(), lat, lng, 
					  	display.getPhoneField().getValue().trim(),
					  	display.getEmailField().getValue().trim(),
					  	display.getWebField().getValue().trim());
          } else {
        	  orphanage.setDescription(display.getDescField().getValue().trim());
        	  orphanage.setAddress(display.getAddressField().getValue().trim(), lat, lng);
        	  orphanage.setPhone(display.getPhoneField().getValue().trim());
        	  orphanage.setEmail(display.getEmailField().getValue().trim());
        	  orphanage.setWebsite(display.getWebField().getValue().trim());
          }
          
	  }
	  
	  private void doSave(){
	    new RPCCall<DBRecordInfo>() {
	      @Override
	      protected void callService(AsyncCallback<DBRecordInfo> cb) {
	    	  orphanageService.updateDBRecord(orphanage, cb);
	      }

	      @Override
	      public void onSuccess(DBRecordInfo result) {
	        GWT.log("OrphanageAddPresenter: Firing OrphanageUpdateEvent");
	        eventBus.fireEvent(new OrphanageUpdateEvent((OrphanageInfo) result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }

}
