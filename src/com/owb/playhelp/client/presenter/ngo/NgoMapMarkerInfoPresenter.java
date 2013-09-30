/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.event.dbrecord.ShowAddDBRecordEvent;
import com.owb.playhelp.client.event.ngo.NgoUpdateEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupDetailsNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEvent;
import com.owb.playhelp.client.event.ngo.NgoRemoveEvent;
import com.owb.playhelp.client.event.ngo.JoinNgoEvent;
import com.owb.playhelp.client.event.ngo.LeaveNgoEvent;
import com.owb.playhelp.client.event.ngo.NgoUpdateEvent;
import com.owb.playhelp.client.helper.ClickPoint;

public class NgoMapMarkerInfoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HTMLPanel getMainPanel();
		public HasText getWarningMsg();
		public HasText getNgoName();
		public HasText getNgoDescription();
		public HasText getNgoAddress();
		public HasText getNgoPhone();
		public HasText getNgoEmail();
		public Anchor getEditBut();
		public Anchor getRemoveBut();
		public Anchor getReportBut();
		public Anchor getJoinBut();
		public Anchor getFollowBut();
		Anchor getRecommendBut();
		public Anchor getConfirmBut();
		public Anchor getFulldescBut();
	}

	private final SimpleEventBus eventBus;
	public final Display display;

	private UserProfileInfo currentUser;
	private final NgoServiceAsync ngoService;
	private final DBRecordInfo ngo;

	public NgoMapMarkerInfoPresenter(UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, DBRecordInfo ngo, Display display) {
		this.currentUser = currentUser;
		this.ngoService = ngoService;
		this.eventBus = eventBus;
		this.display = display;
		this.ngo = ngo;
	}

	public void bind() {
		eventBus.addHandler(NgoUpdateEvent.TYPE, new NgoUpdateEventHandler(){
	    	  public void onNgoUpdate(NgoUpdateEvent event){
	    		  // update project info in the marker
	    		  if (ngo.getUniqueId() == event.getUpdatedNgo().getUniqueId()){	    			  
	    			  updateNgo(event.getUpdatedNgo());
	    			  updateButtons();
	    		  }	    		  
	    	  }
	      });
		this.display.getEditBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowAddDBRecordEvent(ngo));
	        }
	      });
	    this.display.getRemoveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new NgoRemoveEvent(ngo));
	        }
	      });
	    this.display.getReportBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupReportAbuseNgoEvent(new ClickPoint(100,100),ngo));
	        }
	      });
	    this.display.getJoinBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getConfirmBut().addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {

	        }
	      });
	    this.display.getFollowBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFulldescBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupDetailsNgoEvent(new ClickPoint(100,100), ngo));
	        }
	      });
	}

	private void updateNgo(DBRecordInfo dbRecordInfo){
		ngo.setAdminReportList(dbRecordInfo.getAdminReportList());
	}

	private void hideButtons(){

		display.getEditBut().setVisible(false);
		display.getRemoveBut().setVisible(false);
		display.getJoinBut().setVisible(false);
		display.getConfirmBut().setVisible(false);
		display.getFollowBut().setVisible(false);
		display.getReportBut().setVisible(false);
		display.getRecommendBut().setVisible(false);
		display.getFulldescBut().setVisible(false);
	}
	private void updateButtons(){

		if(!ngo.getValid()){
			display.getWarningMsg().setText("WARNING: This NGO has not been validated !!!");
		} else {
			display.getWarningMsg().setText("");
		}
		
		if (currentUser == null) {

			display.getEditBut().setVisible(false);
			display.getRemoveBut().setVisible(false);
			display.getJoinBut().setVisible(false);
			display.getConfirmBut().setVisible(false);
			display.getFollowBut().setVisible(false);
			
			return;
		}
		
		if (!ngo.getMember()) {
			display.getEditBut().setVisible(false);
			display.getRemoveBut().setVisible(false);
			display.getJoinBut().setText("Join");

			if(!ngo.getConfirmed()){
				display.getConfirmBut().setText("Confirm");
			} else {
				display.getConfirmBut().setText("UnConfirm");
			}
			if(!ngo.getFollower()){
				display.getFollowBut().setText("Follow");
			} else {
				display.getFollowBut().setText("Unfollow");
			}
		} else {
			display.getJoinBut().setText("Leave");
			display.getConfirmBut().setVisible(false);
			display.getFollowBut().setVisible(false);
		}
		
		
	}
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		display.getNgoName().setText(ngo.getName());
		display.getNgoAddress().setText(ngo.getAddress());
		display.getNgoDescription().setText(ngo.getDescription());
		display.getNgoPhone().setText(ngo.getPhone());
		display.getNgoEmail().setText(ngo.getEmail());
		
		//updateButtons();
		hideButtons();
		
		bind();
	}

}
