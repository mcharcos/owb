/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.resources.Resources;
import com.owb.playhelp.client.event.NewsHomeEvent;
import com.owb.playhelp.client.event.friend.FriendsHomeEvent;
import com.owb.playhelp.client.event.map.MainHomeEvent;

public class TopCenterPanelPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public Image getNewsPanel();
		public Image getWorldPanel();
		public Image getFriendPanel();
		public Image getOwbPanel();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	//private Resources resources;

	public TopCenterPanelPresenter(SimpleEventBus eventBus,Display display){
		this.eventBus = eventBus;
		this.display = display;
	}
	public void bind() {
		/*  
		this.display.getEmailPanel().addMouseOverHandler(new MouseOverHandler(){
			  public void onMouseOver(MouseOverEvent event){
				  display.getEmailPanel().setResource(Resources.INSTANCE.emailSelLogo());
			  }
		  });
		  this.display.getEmailPanel().addMouseOutHandler(new MouseOutHandler(){
			  public void onMouseOut(MouseOutEvent event){
				  display.getEmailPanel().setResource(Resources.INSTANCE.emailLogo());
			  }
		  });
		  this.display.getEmailPanel().addClickHandler(new ClickHandler(){
				 public void onClick(ClickEvent event){
					 eventBus.fireEvent(new ContactHomeEvent());
				 }
			  });
		  */
		  this.display.getNewsPanel().addMouseOverHandler(new MouseOverHandler(){
			  public void onMouseOver(MouseOverEvent event){
				  display.getNewsPanel().setResource(Resources.INSTANCE.newsSelLogo2());
			  }
		  });
		  this.display.getNewsPanel().addMouseOutHandler(new MouseOutHandler(){
			  public void onMouseOut(MouseOutEvent event){
				  display.getNewsPanel().setResource(Resources.INSTANCE.newsLogo2());
			  }
		  });
		  this.display.getNewsPanel().addClickHandler(new ClickHandler(){
			 public void onClick(ClickEvent event){
				 eventBus.fireEvent(new NewsHomeEvent());
			 }
		  });
		  this.display.getWorldPanel().addMouseOverHandler(new MouseOverHandler(){
			  public void onMouseOver(MouseOverEvent event){
				  display.getWorldPanel().setResource(Resources.INSTANCE.worldSelLogo2());
			  }
		  });
		  this.display.getWorldPanel().addMouseOutHandler(new MouseOutHandler(){
			  public void onMouseOut(MouseOutEvent event){
				  display.getWorldPanel().setResource(Resources.INSTANCE.worldLogo2());
			  }
		  });
		  this.display.getWorldPanel().addClickHandler(new ClickHandler(){
				 public void onClick(ClickEvent event){
					 eventBus.fireEvent(new MainHomeEvent());
				 }
			  });
		  this.display.getFriendPanel().addMouseOverHandler(new MouseOverHandler(){
			  public void onMouseOver(MouseOverEvent event){
				  display.getFriendPanel().setResource(Resources.INSTANCE.friendSelLogo2());
			  }
		  });
		  this.display.getFriendPanel().addMouseOutHandler(new MouseOutHandler(){
			  public void onMouseOut(MouseOutEvent event){
				  display.getFriendPanel().setResource(Resources.INSTANCE.friendLogo2());
			  }
		  });
		  this.display.getFriendPanel().addClickHandler(new ClickHandler(){
				 public void onClick(ClickEvent event){
					 eventBus.fireEvent(new FriendsHomeEvent());
				 }
			  });
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
}
