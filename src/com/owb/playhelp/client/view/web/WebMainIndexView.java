package com.owb.playhelp.client.view.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WebMainIndexView extends Composite implements HasText {

	private static WebMainIndexViewUiBinder uiBinder = GWT
			.create(WebMainIndexViewUiBinder.class);

	interface WebMainIndexViewUiBinder extends
			UiBinder<Widget, WebMainIndexView> {
	}

	@UiField VerticalPanel areaField, indexField;
	
	public WebMainIndexView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}
	
	public VerticalPanel getAreaField(){
		return areaField;
	}
	
	public VerticalPanel getIndexField(){
		return indexField;
	}

}
