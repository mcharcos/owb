package com.owb.playhelp.client.view.web.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.web.WebHomePresenter;

public class WebHomeGifView extends Composite implements WebHomePresenter.Display {

	private static WebHomeViewUiBinder uiBinder = GWT
			.create(WebHomeViewUiBinder.class);

	interface WebHomeViewUiBinder extends UiBinder<Widget, WebHomeGifView> {
	}

	@UiField Image imgField;
	@UiField HorizontalPanel infoBandField;
	@UiField HTMLPanel newsPanel1, newsPanel2;
	
	public WebHomeGifView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HorizontalPanel getInfoBandField() {
		return infoBandField;
	}
	@Override 
	public HTMLPanel getNewsPanel1(){
		return newsPanel1;
	}
	@Override 
	public HTMLPanel getNewsPanel2(){
		return newsPanel2;
	}
	@Override
	public Image getImageField(){
		return imgField;
	}

}
