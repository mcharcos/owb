package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.presenter.ShowDetailsDBRecordPresenter;
import com.google.gwt.user.client.ui.PopupPanel;

public class ShowDetailsDBRecordView extends PopupPanel implements ShowDetailsDBRecordPresenter.Display {

	  @UiTemplate("ShowDetailsDBRecordView.ui.xml")
	  interface Binder extends UiBinder<Widget, ShowDetailsDBRecordView> {
	  }

	  private static final Binder binder = GWT.create(Binder.class);
	
	  
	public ShowDetailsDBRecordView() {
	    super(true);
	    add(binder.createAndBindUi(this));
	    startView();
	    setPopupPosition(285, 60);
	    setHeight("500px");
	    setWidth("800px");
	    //this.setStyleName("backgroundall");
	    //this.center();
	    //this.center();
	    show();
	}
	
	public ShowDetailsDBRecordView(ClickPoint location) {
	    super(true);
	    add(binder.createAndBindUi(this));
	    startView();
	    setPopupPosition(location.getLeft(), location.getTop());
	    show();
	}
	
	@UiField Label nameField,descField,addressField,phoneField,emailField,webField;
	@UiField ListReportView membersReqField,followersField,abuseReportField;
	@UiField ListReportView membersField,adminReportField,ngoReportField;
	@UiField
	Anchor okBut;

	  private void startView(){
		  this.membersField.getTitleReportField().setText("Members");
		  this.membersField.getAddNewLink().setText("Add Member");
		  this.membersReqField.getTitleReportField().setText("Member Requests");
		  this.membersReqField.getAddNewLink().setText("Join Request");
		  this.followersField.getTitleReportField().setText("Followers");
		  this.followersField.getAddNewLink().setText("Follow");
		  this.abuseReportField.getTitleReportField().setText("Abuse Reports");
		  this.abuseReportField.getAddNewLink().setText("Report Abuse");
		  this.adminReportField.getTitleReportField().setText("Administrator Reports");
		  this.adminReportField.getAddNewLink().setText("Confirm");
		  this.ngoReportField.getTitleReportField().setText("NGO Reports");
		  this.ngoReportField.getAddNewLink().setText("Add report");
	  }
	  
	  @Override
	  public Widget asWidget() {
	    return this;
	  }

	  @Override
	  public void hide() {
	    super.hide();
	  }

	  public void go(HasWidgets container) {
		
	    
	  }

	  @Override
	  public HasClickHandlers getOkBut(){
		  return okBut;
	  }

	  @Override
	  public Label getNameField(){
		  return nameField;
	  }
	  @Override
	  public Label getDescField(){
		  return descField;
	  }
	  @Override
	  public Label getAddressField(){
		  return addressField;
	  }
	  @Override
	  public Label getPhoneField(){
		  return phoneField;
	  }
	  @Override
	  public Label getEmailField(){
		  return emailField;
	  }
	  @Override
	  public Label getWebField(){
		  return webField;
	  }
	  
	  @Override
	  public ListReportView getMembersField(){
		  return membersField;
	  }
	  @Override
	  public ListReportView getMembersReqField(){
		  return membersReqField;
	  }
	  @Override
	  public ListReportView getFollowersField(){
		  return followersField;
	  }
	  @Override
	  public ListReportView getAbuseReportField(){
		  return abuseReportField;
	  }
	  @Override
	  public ListReportView getAdminReportField(){
		  return adminReportField;
	  }
	  @Override
	  public ListReportView getNgoReportField(){
		  return ngoReportField;
	  }


}
