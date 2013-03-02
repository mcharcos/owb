package com.owb.playhelp.client.view.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Composite;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanageStatusPresenter;

public class AddOrphanageStatusView extends Composite implements AddOrphanageStatusPresenter.Display {


	private static AddOrphanageStatusViewUiBinder uiBinder = GWT
			.create(AddOrphanageStatusViewUiBinder.class);

	interface AddOrphanageStatusViewUiBinder extends UiBinder<Widget, AddOrphanageStatusView> {
	}

	public AddOrphanageStatusView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	@UiField Label nameField;
	@UiField TextBox healthField,educationField,foodField,shelterField,clothingField,hygieneField,joyField;
	@UiField TextBox hopeField,loveField,responsabilitiesField,safetyField,guidanceField,compassionateField,disciplineField;
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
	  public Label getNameField(){
		  return nameField;
	  }

	  @Override
	  public HasValue<String> getHealthField(){
		  return healthField;
	  }
	  @Override
	  public HasValue<String> getEducationField(){
		  return educationField;
	  }
	  @Override
	  public HasValue<String> getFoodField(){
		  return foodField;
	  }

}
