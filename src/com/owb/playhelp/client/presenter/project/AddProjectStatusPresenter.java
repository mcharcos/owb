package com.owb.playhelp.client.presenter.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class AddProjectStatusPresenter implements Presenter{
	public interface Display {
		Widget asWidget();
	    void hide();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		Label getNameField();
		HasValue<String> getHealthField();
		HasValue<String> getEducationField();
		HasValue<String> getNutritionField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private OrphanageInfo orphanage;

	private final OrphanageServiceAsync orphanageService;
	
	public AddProjectStatusPresenter(OrphanageInfo orphanage,OrphanageServiceAsync orphanageService, SimpleEventBus eventBus, Display display){
		this.orphanage = orphanage;
		this.orphanageService = orphanageService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
		      doSave();
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          display.hide();
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		bind();
		
		if (orphanage == null) return;
        
		StandardInfo status = this.orphanage.getStatus();
		this.display.getNameField().setText(this.orphanage.getName());
		if (status!=null){
			this.display.getHealthField().setValue(String.valueOf(status.getHealth()));
			this.display.getEducationField().setValue(String.valueOf(status.getEducation()));
			this.display.getNutritionField().setValue(String.valueOf(status.getNutrition()));
		}
	}

	  private void doSave() {
		  
		  StandardInfo status = new StandardInfo();
		  status.setHealth(Float.valueOf(this.display.getHealthField().getValue()));
		  status.setEducation(Float.valueOf(this.display.getEducationField().getValue()));
		  status.setNutrition(Float.valueOf(this.display.getNutritionField().getValue()));
		  
		  this.orphanage.setStatus(status);

	    new RPCCall<DBRecordInfo>() {
	      @Override
	      protected void callService(AsyncCallback<DBRecordInfo> cb) {
	    	  orphanageService.updateDBRecord(orphanage, cb);
	      }

	      @Override
	      public void onSuccess(DBRecordInfo result) {
	    	  display.hide();
	        GWT.log("OrphanageAddPresenter: Firing ShowPopupAddOrphanageStatusEvent");
	        eventBus.fireEvent(new OrphanageUpdateEvent((OrphanageInfo) result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
}
