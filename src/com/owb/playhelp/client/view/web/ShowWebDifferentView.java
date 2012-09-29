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

public class ShowWebDifferentView extends Composite implements HasText {

	private static ShowWebDifferentViewUiBinder uiBinder = GWT
			.create(ShowWebDifferentViewUiBinder.class);

	interface ShowWebDifferentViewUiBinder extends
			UiBinder<Widget, ShowWebDifferentView> {
	}

	public ShowWebDifferentView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ShowWebDifferentView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
