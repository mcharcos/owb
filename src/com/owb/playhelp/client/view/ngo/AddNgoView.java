package com.owb.playhelp.client.view.ngo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Composite;
import com.owb.playhelp.client.presenter.ngo.AddNgoPresenter;

public class AddNgoView extends Composite implements AddNgoPresenter.Display {


	private static AddNgoViewUiBinder uiBinder = GWT
			.create(AddNgoViewUiBinder.class);

	interface AddNgoViewUiBinder extends UiBinder<Widget, AddNgoView> {
	}

	public AddNgoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField TextBox nameField,descField,addressField,phoneField,emailField,webField;
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


}
