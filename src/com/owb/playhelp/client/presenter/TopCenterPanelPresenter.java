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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.Owb;
import com.owb.playhelp.client.resources.Resources;
import com.owb.playhelp.client.event.WinSizeUpdateEvent;
import com.owb.playhelp.client.event.WinSizeUpdateEventHandler;
import com.owb.playhelp.client.event.friend.FriendsHomeEvent;
import com.owb.playhelp.client.event.map.MainHomeEvent;
import com.owb.playhelp.client.event.news.NewsHomeEvent;
import com.owb.playhelp.client.event.web.ShowWebEvent;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class manage the view of the top bane that contains the icon and the main views
 * which are map, news and friends. It handles clicks of the icon representing this views
 * and visualization of different icons when the mouse pass over each icon.
 *
 */
public class TopCenterPanelPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		/*public Image getNewsPanel();
		public Image getWorldPanel();
		public Image getFriendPanel();*/
		public Image getOwbPanel();
		public HorizontalPanel getUserSettings();
		Image getBeforeUserPanel();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	//private Resources resources;

	public TopCenterPanelPresenter(SimpleEventBus eventBus,Display display){
		this.eventBus = eventBus;
		this.display = display;
	}
	public void bind() {

		eventBus.addHandler(WinSizeUpdateEvent.TYPE, new WinSizeUpdateEventHandler(){
			  @Override public void onWinSizeUpdate(WinSizeUpdateEvent event){
				  updateSizes();
			  }
		  });
		  this.display.getOwbPanel().addClickHandler(new ClickHandler(){
				 public void onClick(ClickEvent event){
					 eventBus.fireEvent(new ShowWebEvent("gethomeItem"));
				 }
			  });
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
		
		/*
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
		  */
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		updateSizes();
		bind();
	}

	private void updateSizes(){
		  //Window.alert(Integer.toString(Owb.get().getWidthCenter())+" "+Integer.toString(display.getOwbPanel().getWidth())+" "+Integer.toString(display.getUserSettings().getOffsetWidth()));
		  int widthdiff = Owb.get().getWidthCenter() - display.getOwbPanel().getWidth() - display.getUserSettings().getOffsetWidth();
		  if (widthdiff >0){
			  display.getBeforeUserPanel().setWidth(widthdiff+"px");
		  } else {
			  display.getBeforeUserPanel().setVisible(false);
		  }
	}
}
