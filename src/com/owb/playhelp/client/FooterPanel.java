package com.owb.playhelp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.FooterPanelPresenter;

public class FooterPanel extends Composite implements FooterPanelPresenter.Display {

	private static FooterPanelUiBinder uiBinder = GWT
			.create(FooterPanelUiBinder.class);

	interface FooterPanelUiBinder extends UiBinder<Widget, FooterPanel> {
	}

	public FooterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public FooterPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
