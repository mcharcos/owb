package com.owb.playhelp.client.view.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ShowWebMissionView extends Composite implements HasText {

	private static ShowWebMissionViewUiBinder uiBinder = GWT
			.create(ShowWebMissionViewUiBinder.class);

	interface ShowWebMissionViewUiBinder extends
			UiBinder<Widget, ShowWebMissionView> {
	}

	public ShowWebMissionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public ShowWebMissionView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
