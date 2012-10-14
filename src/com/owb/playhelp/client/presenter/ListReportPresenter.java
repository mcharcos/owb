/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 * 
 * @author Miguel Charcos Llorens
 * This is a generig class to manage the list of organizations, users, ...
 * The ListReportView shows for any list of strings a unique representation
 * of the information in the list as well as links to manage the list. This presenter
 * does not currently handle any event.
 *
 */
public class ListReportPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		Label getTitleReportField();
		Label getLoadingLabel();
		Hyperlink getAddNewLink();
		void setData(List<String> data);
		List<Integer> getSelectedRows();
	}

	//private final SimpleEventBus eventBus;
	private final Display display;

	//private UserProfileInfo currentUser;

	public ListReportPresenter(SimpleEventBus eventBus, Display display) {
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
