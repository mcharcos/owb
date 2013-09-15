/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.ngo.NgoUpdateEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.orphanage.NgoServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;

public class AddNgoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		HasValue<String> getNameField();
		HasValue<String> getDescField();
		HasValue<String> getAddressField();
		HasValue<String> getPhoneField();
		HasValue<String> getEmailField();
		HasValue<String> getWebField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private DBRecordInfo ngo;

	//private UserProfileInfo currentUser;
	private final NgoServiceAsync ngoService;
	
	/*
	private String address;
	private double lat,lng;
	private boolean isApiLoaded;
	private final Geocoder geocoder;*/

	public AddNgoPresenter(NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		//this.currentUser = currentUser;
		this.ngoService = ngoService;
		this.eventBus = eventBus;
		//this.geocoder = geocoder;
		this.display = display;
		this.ngo = null;
	}
	public AddNgoPresenter(DBRecordInfo ngo, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		this(ngoService, eventBus, display);
		this.ngo = ngo;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateNgo();
	        	doSave();
	        	eventBus.fireEvent(new AddOrphanageUpdateEvent());
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new AddOrphanageCancelEvent());
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
		
		if (ngo == null) return;

		this.display.getNameField().setValue(this.ngo.getName());
		this.display.getDescField().setValue(this.ngo.getDescription());
		this.display.getAddressField().setValue(this.ngo.getAddress());
		this.display.getPhoneField().setValue(this.ngo.getPhone());
		this.display.getEmailField().setValue(this.ngo.getEmail());
		this.display.getWebField().setValue(this.ngo.getWebsite());
	}
	  
	  private void updateNgo(){
		  double lat = -1.0;
		  double lng = -1.0;
		  //SolveAddress();
		  if (ngo == null){
			  ngo = new DBRecordInfo(DBRecordInfo.ORGANIZATION, display.getNameField().getValue().trim(),
					  	display.getDescField().getValue().trim(),
					  	display.getAddressField().getValue().trim(), lat, lng, 
					  	display.getPhoneField().getValue().trim(),
					  	display.getEmailField().getValue().trim(),
					  	display.getWebField().getValue().trim());
		  } else {
			  ngo.setName(display.getNameField().getValue().trim());
			  ngo.setDescription(display.getDescField().getValue().trim());
			  ngo.setAddress(display.getAddressField().getValue().trim(),lat,lng);
			  ngo.setPhone(display.getPhoneField().getValue().trim());
			  ngo.setEmail(display.getEmailField().getValue().trim());
			  ngo.setWebsite(display.getWebField().getValue().trim());
		  }

	  }
	  
	  private void doSave() {
	    new RPCCall<DBRecordInfo>() {
	      @Override
	      protected void callService(AsyncCallback<DBRecordInfo> cb) {
	    	  ngoService.updateDBRecord(ngo, cb);
	      }

	      @Override
	      public void onSuccess(DBRecordInfo result) {
	        GWT.log("NgoAddPresenter: Firing NgoUpdateEvent");
	        eventBus.fireEvent(new NgoUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
	  
	  // Some how dhit does not work
	  /*
	  private void SolveAddress(){
	        geocoder.getLatLng(address, new LatLngCallback() {
	  	      public void onFailure() {
	  	    	  Window.alert(address + " cannot be resolved"); 
	  	      }

	  	      public void onSuccess(LatLng point) {
	  	    	lat = point.getLatitude();
	  	    	lng = point.getLongitude();
	  	    	isApiLoaded = true;
	  	    	Window.alert("Latitude: "+lat+", Longitude: "+lng);
	  	      }
	  	    });

			try{
				Timer apiLoadedTimer = new Timer() {
					@Override
					public void run() {
						if (isApiLoaded) {
							cancel();
						}
						
					}
				};
				apiLoadedTimer.scheduleRepeating(3000);
				Window.alert("Time out!!");
			}// end try
		    catch (Exception e) {
		        e.printStackTrace();
		        Window.alert("Time out: "+address + " cannot be resolved"); 
		      } 
	  }
	  private void SolveAddress2(){
		  isApiLoaded = false;
			Maps.loadMapsApi(MapHelper.MapKEY, "2", false, new Runnable() {
				   public void run() { 
					   Geocoder geocoder = new Geocoder();
					   //this.lat = map.getPoint(this).getLatitude();
					   //this.lng = map.getPoint(this).getLongitude();
				        //isApiLoaded = true;
				        geocoder.getLatLng(address, new LatLngCallback() {
				  	      public void onFailure() {
				  	    	  Window.alert(address + " cannot be resolved"); 
				  	      }

				  	      public void onSuccess(LatLng point) {
				  	    	lat = point.getLatitude();
				  	    	lng = point.getLongitude();
				  	    	isApiLoaded = true;
				  	    	Window.alert("Latitude: "+lat+", Longitude: "+lng);
				  	      }
				  	    });
				   }
			    });

			try{
				Timer apiLoadedTimer = new Timer() {
					@Override
					public void run() {
						if (isApiLoaded) {
							cancel();
						}
						
					}
				};
				apiLoadedTimer.scheduleRepeating(3000);
				Window.alert("Time out!!");
			}// end try
		    catch (Exception e) {
		        e.printStackTrace();
		        Window.alert("Time out: "+address + " cannot be resolved"); 
		      } 
	  }

	  */
}
