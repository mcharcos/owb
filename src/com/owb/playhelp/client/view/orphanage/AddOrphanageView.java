package com.owb.playhelp.client.view.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanagePresenter;
import com.google.gwt.user.client.ui.PopupPanel;

public class AddOrphanageView extends PopupPanel implements AddOrphanagePresenter.Display {

	  @UiTemplate("AddOrphanageView.ui.xml")
	  interface Binder extends UiBinder<Widget, AddOrphanageView> {
	  }

	  private static final Binder binder = GWT.create(Binder.class);
	  
	public AddOrphanageView() {
	    super(true);
	    add(binder.createAndBindUi(this));
		//initWidget(uiBinder.createAndBindUi(this));
	}
	
	public AddOrphanageView(ClickPoint location) {
	    super(true);
	    add(binder.createAndBindUi(this));
	    setPopupPosition(location.getLeft(), location.getTop());
	    show();
	}
	
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

	  @Override
	  public void hide() {
	    super.hide();
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
