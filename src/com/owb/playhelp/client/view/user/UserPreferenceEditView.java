package com.owb.playhelp.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.FileUpload;
//import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
//import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.user.UserPreferenceEditPresenter;

public class UserPreferenceEditView extends Composite implements UserPreferenceEditPresenter.Display {

	private static UserPreferenceEditViewUiBinder uiBinder = GWT
			.create(UserPreferenceEditViewUiBinder.class);

	interface UserPreferenceEditViewUiBinder extends
			UiBinder<Widget, UserPreferenceEditView> {
	}

	public UserPreferenceEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button cancelButton, saveButton;  //, pictureButton;
	@UiField
	TextBox nameField, emailField;
	//@UiField
	//Image profilePicture;
	
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";
	//@UiField
	//FileUpload uploaderField;
	//@UiField
	//FormPanel formPanel;

	@Override
	public Widget asWidget() {
		/*formPanel.setAction(UPLOAD_ACTION_URL);
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
				
		uploaderField.setName("uploadFormElement");*/
		return this;
	}

	/*
	@Override
	public Image getProfilePictureFrame() {
		return profilePicture;
	}*/
	@Override
	public HasValue<String> getNameLabel() {
	  return nameField;
	}
	@Override
	public HasValue<String> getEmailLabel() {
	  return emailField;
	}
	@Override
	public HasClickHandlers getCancelButton() {
	  return cancelButton;
	}
	@Override
	public HasClickHandlers getSaveButton() {
	  return saveButton;
	}
	/*
	@Override
	public FileUpload getUploadFile() {
		return uploaderField;
	}
	
	@Override
	public FormPanel getFormPanel(){
		return formPanel;
	}*/

}
