/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.contribution;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.Presenter;

public class ContributionUserDetailPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasClickHandlers getDoneLink();
		void hide();
	}

	//private final SimpleEventBus eventBus;
	private final Display display;
	//private final ContributionServiceAsync contributeService;
	
	//private UserProfileInfo currentUser;
	//private ContributionInfo currentContribution;

	public ContributionUserDetailPresenter(Display display) {
		/*
		this.currentUser = currentUser;
		this.currentContribution = currentContribution;
		this.contributeService = contributeService;
		this.eventBus = eventBus;*/
		this.display = display;
		bind();
	}

	public void bind() {
	    this.display.getDoneLink().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
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
