package com.owb.playhelp.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.user.UserSettingPresenter;

public class UserSettingView extends Composite implements UserSettingPresenter.Display {

	private static UserSettingViewUiBinder uiBinder = GWT
			.create(UserSettingViewUiBinder.class);

	interface UserSettingViewUiBinder extends UiBinder<Widget, UserSettingView> {
	}

	public UserSettingView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Image loginout, preferences;

	public UserSettingView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Image getPreferencesImage() {
	  return preferences;
	}
	@Override
	public Image getLogoutImage() {
	  return loginout;
	}
	/*
	@Override
	public HasText getUserNameLabel() {
	  return userNameLabel;
	}
	@Override
	public HasText getUserEmailLabel() {
	  return userEmailLabel;
	}
	@Override
	public HasText getUserTypeLabel() {
	  return userTypeLabel;
	}
	*/


}
