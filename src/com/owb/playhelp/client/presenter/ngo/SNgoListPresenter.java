package com.owb.playhelp.client.presenter.ngo;

import java.util.List;
import java.util.ArrayList;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.StandardInfoPresenter;
import com.owb.playhelp.client.presenter.StandardListPresenter;
import com.owb.playhelp.client.service.StandardServiceAsync;
import com.owb.playhelp.client.view.StandardInfoView;
import com.owb.playhelp.shared.StandardInfo;

public class SNgoListPresenter extends StandardListPresenter {

	protected List<StandardInfo> recordList;
	private final StandardServiceAsync rpcService;
	
	public SNgoListPresenter(StandardServiceAsync rpcService, SimpleEventBus eventBus, Display display) {
		super(eventBus,display);
		this.rpcService = rpcService;
	}
	
	@Override
	protected void fetchDBRecordList() {
	    new RPCCall<ArrayList<StandardInfo>>() {
	      @Override protected void callService(AsyncCallback<ArrayList<StandardInfo>> cb) {
	        rpcService.getNgoList(cb);
	      }

	      @Override public void onSuccess(ArrayList<StandardInfo> result) {
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

		StandardInfoPresenter currentInfoPresenter = null;
		
		for (StandardInfo rec: recordList){
			currentInfoPresenter = new StandardInfoPresenter(eventBus, rec, new StandardInfoView());
			currentInfoPresenter.go(this.display.getListField());
		}
		
	}


}
