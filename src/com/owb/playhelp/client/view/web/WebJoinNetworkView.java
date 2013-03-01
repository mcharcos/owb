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

public class WebJoinNetworkView extends Composite implements HasText {

	private static WebJoinNetworkViewUiBinder uiBinder = GWT
			.create(WebJoinNetworkViewUiBinder.class);

	interface WebJoinNetworkViewUiBinder extends
			UiBinder<Widget, WebJoinNetworkView> {
	}

	public WebJoinNetworkView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public WebJoinNetworkView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
