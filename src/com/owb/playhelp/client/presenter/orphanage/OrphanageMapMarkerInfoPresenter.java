/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.orphanage;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageRemoveEvent;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEvent;
import com.owb.playhelp.client.helper.ClickPoint;

public class OrphanageMapMarkerInfoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HTMLPanel getMainPanel();
		public HasText getOrphanageName();
		public HasText getOrphanageDescription();
		public Anchor getEditBut();
		public Anchor getRemoveBut();
		public Anchor getReportBut();
		public Anchor getFollowBut();
		public Anchor getFulldescBut();
		public Anchor getAddprojBut();
	}

	private final SimpleEventBus eventBus;
	public final Display display;

	private final DBRecordInfo Orphanage;

	public OrphanageMapMarkerInfoPresenter(SimpleEventBus eventBus, DBRecordInfo Orphanage, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.Orphanage = Orphanage;
	}

	public void bind() {
	    this.display.getEditBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupAddOrphanageEvent(new ClickPoint(100,100),Orphanage));
	        }
	      });
	    this.display.getRemoveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new OrphanageRemoveEvent(Orphanage));
	        }
	      });
	    this.display.getReportBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFollowBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFulldescBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getAddprojBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupAddProjectEvent(new ClickPoint(100,100),new ProjectInfo()));
	        }
	      });
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		display.getOrphanageName().setText(Orphanage.getName());
		display.getOrphanageDescription().setText(Orphanage.getDescription());
		
		//if (!Orphanage.getMember()) {
			display.getEditBut().setVisible(false);
			display.getRemoveBut().setVisible(false);
			display.getReportBut().setVisible(false);
			display.getFollowBut().setVisible(false);
			display.getFulldescBut().setVisible(false);
			display.getAddprojBut().setVisible(false);
		//}
		bind();
	}

}
