package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.StandardListPresenter;

public class StandardListView extends Composite implements StandardListPresenter.Display {

	private static DBRecordListViewUiBinder uiBinder = GWT
			.create(DBRecordListViewUiBinder.class);

	interface DBRecordListViewUiBinder extends
			UiBinder<Widget, StandardListView> {
	}

	public StandardListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	VerticalPanel listField;

	@Override
	public void setListField(VerticalPanel listField){
		this.listField = listField;
	}

	@Override
	public VerticalPanel getListField(){
		return this.listField;
	}
}
