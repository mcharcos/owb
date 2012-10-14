/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class manages the left panel view. There is not event handling currently implemented.
 *
 */
public class LeftPanelPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		VerticalPanel getProfilePanel();
		VerticalPanel getTestPanel();
		VerticalPanel getMenuPanel();
	}

	//private final SimpleEventBus eventBus;
	private final Display display;

	//private UserProfileInfo currentUser;

	public LeftPanelPresenter(Display display) {
		//this.currentUser = currentUser;
		//this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
