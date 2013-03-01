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

public class WebHowOrganizationsView extends Composite implements HasText {

	private static WebHowOrganizationsViewUiBinder uiBinder = GWT
			.create(WebHowOrganizationsViewUiBinder.class);

	interface WebHowOrganizationsViewUiBinder extends
			UiBinder<Widget, WebHowOrganizationsView> {
	}

	public WebHowOrganizationsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public WebHowOrganizationsView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
