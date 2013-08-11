package com.owb.playhelp.client.view.web.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.WebInfoBandPresenter;

public class WebInfoBandView extends Composite implements WebInfoBandPresenter.Display {

	private static WebInfoBandViewUiBinder uiBinder = GWT
			.create(WebInfoBandViewUiBinder.class);

	interface WebInfoBandViewUiBinder extends UiBinder<Widget, WebInfoBandView> {
	}

	public WebInfoBandView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Button button;
	@UiField Image pictureField;
	@UiField HTML messageField;

	public WebInfoBandView(String buttonName, String message, String pictureName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(buttonName);
		if (pictureName != null) {
			pictureField = new Image(pictureName);
		}
		messageField.setHTML(message);
	}

	public WebInfoBandView(String buttonName, String message, String pictureName, String width, String height) {
		this(buttonName,message,pictureName);
		this.setWidth(width);
		this.setHeight(height);
	}

	@Override
	public Widget asWidget(){
		return this;
	}

	@Override
	public void setButtonName(String text) {
		button.setText(text);
	}

	@Override
	public String getButtonName() {
		return button.getText();
	}

	@Override
	public Button getButton() {
		return button;
	}
	
	@Override
	public String getMessage(){
		return messageField.getText();
	}
	
	@Override
	public void setMessage(String text){
		messageField.setText(text);
	}

}
