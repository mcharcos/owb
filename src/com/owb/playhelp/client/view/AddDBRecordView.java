package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Composite;
import com.owb.playhelp.client.presenter.AddDBRecordPresenter;

public class AddDBRecordView extends Composite implements AddDBRecordPresenter.Display {


	private static AddDBRecordViewUiBinder uiBinder = GWT
			.create(AddDBRecordViewUiBinder.class);

	interface AddDBRecordViewUiBinder extends UiBinder<Widget, AddDBRecordView> {
	}

	public AddDBRecordView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField Label pageTitle;
	@UiField HTML pageDescription;
	@UiField TextBox nameField,addressField,phoneField,emailField,webField;
	@UiField TextArea descField;
	@UiField
	Anchor saveBut;
	@UiField
	Anchor cancelBut;

	  @Override
	  public Widget asWidget() {
	    return this;
	  }

	  public void go(HasWidgets container) {
		
	    
	  }

	  @Override
	  public HasClickHandlers getSaveBut(){
		  return saveBut;
	  }
	  @Override
	  public HasClickHandlers getCancelBut(){
		  return cancelBut;
	  }

	  @Override
	  public HasValue<String> getNameField(){
		  return nameField;
	  }
	  @Override
	  public HasValue<String> getDescField(){
		  return descField;
	  }
	  @Override
	  public HasValue<String> getAddressField(){
		  return addressField;
	  }
	  @Override
	  public HasValue<String> getPhoneField(){
		  return phoneField;
	  }
	  @Override
	  public HasValue<String> getEmailField(){
		  return emailField;
	  }
	  @Override
	  public HasValue<String> getWebField(){
		  return webField;
	  }
	  
	  
	  protected Label getPageTitleField(){
		  return pageTitle;
	  }
	  protected HTML getPageDescriptionField(){
		  return pageDescription;
	  }


}
