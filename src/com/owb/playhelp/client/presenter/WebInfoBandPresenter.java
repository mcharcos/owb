package com.owb.playhelp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class WebInfoBandPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		void setButtonName(String text);
		String getButtonName();
		Button getButton();
		String getMessage();
		void setMessage(String text);
	}

	private final Display display;
	protected final SimpleEventBus eventBus;
	
	public WebInfoBandPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public void bind() {
		this.display.getButton().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				goButtonEvent();
			}
		});
	}

	public void go(final HasWidgets container) {
		//container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	public void go(final HasWidgets container, boolean clear) {
		if (clear) {
			container.clear();
		}
		container.add(display.asWidget());
		bind();
	}
	
	protected void goButtonEvent(){
	}
	

}
