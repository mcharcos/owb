/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;

public class ReportAbuseNgoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    void hide();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		Label getNameField();
		HasValue<String> getDescField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private DBRecordInfo ngo;
	private String report;

	private final NgoServiceAsync ngoService;

	public ReportAbuseNgoPresenter(NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		this.ngoService = ngoService;
		this.eventBus = eventBus;
		this.display = display;
		this.ngo = null;
	}
	public ReportAbuseNgoPresenter(DBRecordInfo ngo, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		this(ngoService, eventBus, display);
		this.ngo = ngo;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	//doReport();
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          display.hide();
	        }
	      });
	}

	public void go(final HasWidgets container) {
		bind();
		
		if (ngo == null) return;

		this.display.getNameField().setText(this.ngo.getName());
	}

	/*
	  private void doReport() {
	      report = this.display.getDescField().getValue();
		  new RPCCall<NgoInfo>() {
	      @Override
	      protected void callService(AsyncCallback<NgoInfo> cb) {
	    	  ngoService.reportAbuseNgo(ngo, report, cb);
	      }
	
	      @Override
	      public void onSuccess(NgoInfo result) {
	    	  display.hide();
	        GWT.log("NgoAddPresenter: Firing NgoUpdateEvent");
	        eventBus.fireEvent(new ReportAbuseNgoEvent(result)); 
	      }
	
	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
	  */

}
