package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.MapMarkerPresenter;

public class MapMarkerView extends Composite implements MapMarkerPresenter.Display {

	private static NgoMapMarkerInfoViewUiBinder uiBinder = GWT
			.create(NgoMapMarkerInfoViewUiBinder.class);

	interface NgoMapMarkerInfoViewUiBinder extends
			UiBinder<Widget, MapMarkerView> {
	}

	public MapMarkerView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTML recordName, recordDescription;
	@UiField
	Anchor detailsBut;

	  @Override
	public Widget asWidget(){
		return this;
	}
	  @Override
	public HTML getRecordName(){
		return recordName;
	} 
	  @Override
	public HTML getRecordDescription(){
		return recordDescription;
	}
	@Override
	public Anchor getDetailsBut(){
	  return detailsBut;
	}
}
