/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.web;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.Presenter;

public class WebPagesPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	}

	private final Display display;

	public WebPagesPresenter(Display display) {
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
