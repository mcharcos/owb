/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.Presenter;

public class ProjectContributeNotLoggedPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasClickHandlers getDoneLink();
		void hide();
	}

	private final Display display;

	public ProjectContributeNotLoggedPresenter(Display display) {
		this.display = display;
		bind();
	}

	public void bind() {
		this.display.getDoneLink().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				display.hide();
			}
		});
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
