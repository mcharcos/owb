/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.dbrecord.ShowDetailsDBRecordEvent;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;

public class MapMarkerPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HasText getRecordName();
		public HasText getRecordDescription();
		public Anchor getDetailsBut();
	}

	private final SimpleEventBus eventBus;
	public final Display display;

	private UserProfileInfo currentUser;
	private final DBRecordInfo record;

	public MapMarkerPresenter(UserProfileInfo currentUser, 
			SimpleEventBus eventBus, DBRecordInfo record, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
		this.record = record;
	}

	public void bind() {
		/*
		eventBus.addHandler(NgoUpdateEvent.TYPE, new NgoUpdateEventHandler(){
	    	  public void onNgoUpdate(NgoUpdateEvent event){
	    		  if (ngo.getUniqueId() == event.getUpdatedNgo().getUniqueId()){	    			  
	    			  updateNgo(event.getUpdatedNgo());
	    			  updateButtons();
	    		  }	    		  
	    	  }
	      });
	      */
		this.display.getDetailsBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowDetailsDBRecordEvent(record));
	        }
	      });
	}

	/*
	private void updateNgo(DBRecordInfo dbRecordInfo){
		record.setAdminReportList(dbRecordInfo.getAdminReportList());
	}
	*/

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		display.getRecordName().setText(record.getName());
		display.getRecordDescription().setText(record.getDescription());
		bind();
	}

}
