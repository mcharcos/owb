/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.service.orphanage.NgoServiceAsync;
import com.owb.playhelp.client.view.ListReportView;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;

public class ShowDetailsNgoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    void hide();
	    HasClickHandlers getOkBut();
	    Label getNameField();
	    Label getDescField();
	    Label getAddressField();
	    Label getPhoneField();
		Label getEmailField();
		Label getWebField();
		ListReportView getMembersField();
		ListReportView getMembersReqField();
		ListReportView getFollowersField();
		ListReportView getAbuseReportField();
	    ListReportView getAdminReportField();
	    ListReportView getNgoReportField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private DBRecordInfo ngo;

	private UserProfileInfo currentUser;
	private final NgoServiceAsync ngoService;
	

	public ShowDetailsNgoPresenter(UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus,Display display) {
		this.currentUser = currentUser;
		this.ngoService = ngoService;
		this.eventBus = eventBus;
		this.display = display;
		this.ngo = null;
	}
	public ShowDetailsNgoPresenter(DBRecordInfo ngo, UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		this(currentUser, ngoService, eventBus, display);
		this.ngo = ngo;
	}

	public void bind() {
	    this.display.getOkBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	display.hide();
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		bind();
		
		if (ngo == null) return;

		this.display.getNameField().setText(this.ngo.getName());
		this.display.getDescField().setText(this.ngo.getDescription());
		this.display.getAddressField().setText(this.ngo.getAddress());
		this.display.getPhoneField().setText(this.ngo.getPhone());
		this.display.getEmailField().setText(this.ngo.getEmail());
		this.display.getWebField().setText(this.ngo.getWebsite());
		
		// Now add the list of members, followers,...
		this.display.getMembersField().setData(ngo.getMemberList());
		this.display.getMembersReqField().setData(ngo.getMemberReqList());
		this.display.getFollowersField().setData(ngo.getFollowerList());
		this.display.getAbuseReportField().setData(ngo.getAbuseReportList());
		this.display.getAdminReportField().setData(ngo.getAdminReportList());
		this.display.getNgoReportField().setData(ngo.getNgoReportList());
	}
	
	
	private String getMemberListField(NgoInfo ngo){
		
		List<String> oList = ngo.getMemberList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	private String getMemberReqListField(NgoInfo ngo){
		
		List<String> oList = ngo.getMemberReqList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	private String getFollowerListField(NgoInfo ngo){
		
		List<String> oList = ngo.getFollowerList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	  
	private String getAbuseReportListField(NgoInfo ngo){
		
		List<String> oList = ngo.getAbuseReportList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	  
	private String getNgoReportListField(NgoInfo ngo){
		
		List<String> oList = ngo.getNgoReportList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	  
}
