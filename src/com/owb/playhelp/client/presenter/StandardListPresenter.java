package com.owb.playhelp.client.presenter;

import java.util.List;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.shared.StandardInfo;

public class StandardListPresenter implements Presenter {
	public interface Display {
	Widget asWidget();
	VerticalPanel getListField();
	void setListField(VerticalPanel listField);
	}

	protected List<StandardInfo> recordList;
	protected final SimpleEventBus eventBus;
	public final Display display;
	
	public StandardListPresenter(SimpleEventBus eventBus, Display display){
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public void bind() {
	
	}
	
	public void go(final HasWidgets container) {
		//container.clear();
		container.add(display.asWidget());
		bind();
		fetchDBRecordList();
	};
	
	protected void fetchDBRecordList() {
	}


}