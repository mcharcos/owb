/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.web;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.Presenter;

public class WebPagesPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		ArrayList<HTMLPanel> getViewList();
		ArrayList<Anchor> getLinkList();
	}

	private final Display display;

	public WebPagesPresenter(Display display) {
		this.display = display;
	}

	public void bind() {
		final ArrayList<Anchor> linkList = this.display.getLinkList();
		
		if (linkList != null){
			for (Anchor link:linkList){
				addLinkHandler(link,linkList);
			}
		}
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		setView(0);
		bind();
	}
	
	private void setView(int i){
		ArrayList<HTMLPanel> viewList = this.display.getViewList();
		if (viewList != null){
			for (HTMLPanel view:viewList){
				view.setVisible(false);
			}
			viewList.get(i).setVisible(true);
		}
	}
	
	private void addLinkHandler(final int i, ArrayList<Anchor> linkList){
		linkList.get(i).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				setView(i);
			}
		});
	}
	private void addLinkHandler(Anchor link, ArrayList<Anchor> linkList){
		final int i = linkList.indexOf(link);
		addLinkHandler(i,linkList);
	}

	
}
