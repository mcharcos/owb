package com.owb.playhelp.client.view;

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
import com.owb.playhelp.client.presenter.AddStandardPresenter;

public class AddStandardView extends Composite implements AddStandardPresenter.Display {


	private static AddOrphanageStatusViewUiBinder uiBinder = GWT
			.create(AddOrphanageStatusViewUiBinder.class);

	interface AddOrphanageStatusViewUiBinder extends UiBinder<Widget, AddStandardView> {
	}

	public AddStandardView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	//@UiField 
	Label nameField = new Label();
	@UiField TextBox waterField,foodField,shelterField,clothingField;
	@UiField TextBox medicineField, hygieneField,safetyField, activityField;
	@UiField TextBox educationField,guidanceField,responsibilityField,disciplineField;
	@UiField TextBox joyField,compassionField,loveField,hopeField;
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
	  public HasValue<String> getWaterField(){
		  return waterField;
	  }
	  @Override
	  public HasValue<String> getFoodField(){
		  return foodField;
	  }
	  @Override
	  public HasValue<String> getShelterField(){
		  return shelterField;
	  }
	  @Override
	  public HasValue<String> getClothingField(){
		  return clothingField;
	  }
	  @Override
	  public HasValue<String> getMedicineField(){
		  return medicineField;
	  }
	  @Override
	  public HasValue<String> getHygieneField(){
		  return hygieneField;
	  }
	  @Override
	  public HasValue<String> getSafetyField(){
		  return safetyField;
	  }
	  @Override
	  public HasValue<String> getActivityField(){
		  return activityField;
	  }
	  @Override
	  public HasValue<String> getEducationField(){
		  return educationField;
	  }
	  @Override
	  public HasValue<String> getGuidanceField(){
		  return guidanceField;
	  }
	  @Override
	  public HasValue<String> getResponsibilityField(){
		  return responsibilityField;
	  }
	  @Override
	  public HasValue<String> getDisciplineField(){
		  return disciplineField;
	  }
	  @Override
	  public HasValue<String> getLoveField(){
		  return loveField;
	  }
	  @Override
	  public HasValue<String> getCompassionField(){
		  return compassionField;
	  }
	  @Override
	  public HasValue<String> getJoyField(){
		  return joyField;
	  }
	  @Override
	  public HasValue<String> getHopeField(){
		  return hopeField;
	  }

}
