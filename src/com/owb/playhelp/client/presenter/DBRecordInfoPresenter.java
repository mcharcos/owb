package com.owb.playhelp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.orphanage.DBRecordRemoveEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageRemoveEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.project.ProjectInfo;

public class DBRecordInfoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HTMLPanel getMainPanel();
		public HasText getRecordName();
		public HasText getRecordDescription();
		public Anchor getEditBut();
		public Anchor getRemoveBut();
		public Anchor getReportBut();
		public Anchor getFollowBut();
		public Anchor getFulldescBut();
		public Anchor getAddprojBut();
	}

	private final SimpleEventBus eventBus;
	public final Display display;
	
	private final DBRecordInfo record;

	public DBRecordInfoPresenter(SimpleEventBus eventBus, DBRecordInfo record, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.record = record;
	}

	public void bind() {
	    this.display.getEditBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupAddOrphanageEvent(new ClickPoint(100,100),(OrphanageInfo) record));
	        }
	      });
	    this.display.getRemoveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new DBRecordRemoveEvent(record));
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
		container.add(display.asWidget());
		display.getRecordName().setText(record.getName());
		display.getRecordDescription().setText(record.getDescription());
		
		//if (!Orphanage.getMember()) {
			display.getEditBut().setVisible(false);
			display.getRemoveBut().setVisible(true);
			display.getReportBut().setVisible(false);
			display.getFollowBut().setVisible(false);
			display.getFulldescBut().setVisible(false);
			display.getAddprojBut().setVisible(false);
		//}
		bind();
	};

}
