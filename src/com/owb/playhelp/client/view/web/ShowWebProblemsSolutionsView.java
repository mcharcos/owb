package com.owb.playhelp.client.view.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ShowWebProblemsSolutionsView extends Composite implements HasText {

	private static ShowWebMissionViewUiBinder uiBinder = GWT
			.create(ShowWebMissionViewUiBinder.class);

	interface ShowWebMissionViewUiBinder extends
			UiBinder<Widget, ShowWebProblemsSolutionsView> {
	}

	public ShowWebProblemsSolutionsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
