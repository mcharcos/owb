package com.owb.playhelp.client.presenter.map;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.web.ShowWebEvent;
import com.owb.playhelp.client.presenter.Presenter;

public class MapMenuPresenter   implements Presenter {
	public interface Display {
		Widget asWidget();
		MenuBar getmainMenu();
		MenuItem getmapItem();
	}


	private final SimpleEventBus eventBus;
	private final Display display;

	public MapMenuPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {

		/*
		this.display.getAddNgoItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowPopupAddNgoEvent(new ClickPoint(100,100)));
			}
		});
		*/
		this.display.getmapItem().setCommand(new Command() {
			@Override
			public void execute() {
				//eventBus.fireEvent(new ShowWebEvent("getmapItem"));
			}
		});
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
