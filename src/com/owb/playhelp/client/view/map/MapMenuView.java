package com.owb.playhelp.client.view.map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.map.MapMenuPresenter;

public class MapMenuView extends Composite implements MapMenuPresenter.Display {

	private static MapMenuViewUiBinder uiBinder = GWT
			.create(MapMenuViewUiBinder.class);

	interface MapMenuViewUiBinder extends UiBinder<Widget, MapMenuView> {
	}

	@UiField
	MenuBar mainMenu;
	@UiField
	MenuItem mapItem;

	public MapMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public MapMenuView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public MenuBar getmainMenu() {
		return mainMenu;
	}

	@Override
	public MenuItem getmapItem() {
		return mapItem;
	}

}
