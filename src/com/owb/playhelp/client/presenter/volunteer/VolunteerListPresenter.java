package com.owb.playhelp.client.presenter.volunteer;

import java.util.List;
import java.util.ArrayList;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.DBRecordInfoPresenter;
import com.owb.playhelp.client.presenter.DBRecordListPresenter;
import com.owb.playhelp.client.service.VolunteerServiceAsync;
import com.owb.playhelp.client.view.DBRecordInfoView;
import com.owb.playhelp.shared.DBRecordInfo;

public class VolunteerListPresenter extends DBRecordListPresenter {

	protected List<DBRecordInfo> recordList;
	private final VolunteerServiceAsync rpcService;
	
	public VolunteerListPresenter(VolunteerServiceAsync rpcService, SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
		this.rpcService = rpcService;
	}
	
	@Override
	protected void fetchDBRecordList() {
	    new RPCCall<ArrayList<DBRecordInfo>>() {
	      @Override protected void callService(AsyncCallback<ArrayList<DBRecordInfo>> cb) {
	        rpcService.getDBRecordList(cb);
	      }

	      @Override public void onSuccess(ArrayList<DBRecordInfo> result) {
	    	recordList = result;
	    	updateData();
	      }

	      @Override public void onFailure(Throwable caught) {
	        Window.alert("Error fetching Ngo list: " + caught.getMessage());
	      }
	    }.retry(3);

	  }
	
	private void updateData(){
		this.display.getListField().clear();
		if (recordList == null){
			this.display.getListField().add(new Label("No entry"));
			return;
		}

		DBRecordInfoPresenter currentInfoPresenter = null;
		
		for (DBRecordInfo rec: recordList){
			currentInfoPresenter = new VolunteerInfoPresenter(eventBus, rec, new DBRecordInfoView());
			currentInfoPresenter.go(this.display.getListField());
		}
		
	}


}
