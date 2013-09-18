package com.owb.playhelp.client.presenter;

import java.util.List;
import java.util.ArrayList;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.view.DBRecordInfoView;
import com.owb.playhelp.shared.DBRecordInfo;

public class DBRecordListPresenter implements Presenter {
	public interface Display {
	Widget asWidget();
	VerticalPanel getListField();
	void setListField(VerticalPanel listField);
	}

	protected List<DBRecordInfo> recordList;
	protected final SimpleEventBus eventBus;
	public final Display display;
	
	public DBRecordListPresenter(SimpleEventBus eventBus, Display display){
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public void bind() {
	
	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
		fetchDBRecordList();
	};
	
	protected void fetchDBRecordList() {
	}


}