/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class manages the left panel view. There is not event handling currently implemented.
 *
 */
public class FooterPanelPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	}

	//private final SimpleEventBus eventBus;
	private final Display display;
	private final SimpleEventBus eventBus;

	//private UserProfileInfo currentUser;

	public FooterPanelPresenter(SimpleEventBus eventBus,Display display) {
		//this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
		  /*this.display.getOWBIcon().addClickHandler(new ClickHandler(){
				 public void onClick(ClickEvent event){
					 eventBus.fireEvent(new ShowWebEvent("gethomeItem"));
				 }
			  });*/
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
