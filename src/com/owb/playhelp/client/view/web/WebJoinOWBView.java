package com.owb.playhelp.client.view.web;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.volunteer.AddVolunteerPresenter;

public class WebJoinOWBView extends Composite implements AddVolunteerPresenter.Display {

	private static WebJoinOWBViewUiBinder uiBinder = GWT
			.create(WebJoinOWBViewUiBinder.class);

	interface WebJoinOWBViewUiBinder extends UiBinder<Widget, WebJoinOWBView> {
	}

	public WebJoinOWBView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public WebJoinOWBView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@UiField TextBox nameField,addressField,phoneField,emailField,webField;
	@UiField TextArea descField;
	@UiField CheckBox question1, question2, question3, question4, question5, question6, question7, question8, question9, question10;
	@UiField
	Anchor saveBut;
	@UiField
	Anchor cancelBut;

	private ArrayList<CheckBox> checkboxList;
	
	
	  @Override
	  public Widget asWidget() {
		  checkboxList = new ArrayList<CheckBox>(
					Arrays.asList(question1,question2,question3,question4,question5,question6,question7,question8,question9,question10));
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
	  @Override
	  public ArrayList<CheckBox> getCheckBoxList(){
		  return checkboxList;
	  }



}
