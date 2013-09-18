/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.map;

import java.util.ArrayList;

import com.google.gwt.event.shared.SimpleEventBus;

import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.InfoWindowContent; 
import com.google.gwt.maps.client.MapWidget; 
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl; 
import com.google.gwt.maps.client.geom.LatLng; 
import com.google.gwt.maps.client.overlay.Marker; 
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.MarkerOptions; 
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.geom.Size;



import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.ngo.NgoUpdateEvent;
import com.owb.playhelp.client.event.ngo.NgoUpdateEventHandler;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEventHandler;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.orphanage.NgoServiceAsync;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.helper.MapHelper;
import com.owb.playhelp.client.resources.Resources;
import com.owb.playhelp.client.view.MapMarkerView;
import com.owb.playhelp.client.view.ngo.NgoMapMarkerInfoView;
import com.owb.playhelp.client.presenter.MapMarkerPresenter;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.ngo.NgoMapMarkerInfoPresenter;
import com.owb.playhelp.client.view.orphanage.OrphanageMapMarkerInfoView;
import com.owb.playhelp.client.presenter.orphanage.OrphanageMapMarkerInfoPresenter;

public class MainHomePresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HorizontalPanel getMapPanel();
	}

	public final static String MapKEY = "AIzaSyDVrMIiTG45-TVel7LCWCH1i6Cqab12FgE";

	private final NgoServiceAsync ngoService;
	private final OrphanageServiceAsync orphanageService;
	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;
	private ArrayList<DBRecordInfo> ngoList = null;
	private ArrayList<DBRecordInfo> orphanageList = null;
 
	private MapWidget map = null; 
	private Geocoder geocoder = null; 


	public MainHomePresenter(UserProfileInfo currentUser, NgoServiceAsync ngoService, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.ngoService = ngoService;
		this.orphanageService = orphanageService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		eventBus.addHandler(NgoUpdateEvent.TYPE, new NgoUpdateEventHandler(){
	    	  public void onNgoUpdate(NgoUpdateEvent event){
	    		  // update project with new need resource and update table
	    		  ngoRetrieve();
	    	  }
	      });
		eventBus.addHandler(OrphanageUpdateEvent.TYPE, new OrphanageUpdateEventHandler(){
	    	  public void onOrphanageUpdate(OrphanageUpdateEvent event){
	    		  // update project with new need resource and update table
	    		  orphanageRetrieve();
	    	  }
	      });
		
		
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		Maps.loadMapsApi(MapHelper.MapKEY, "2", false, new Runnable() {
			   public void run() { 
					map = new MapWidget(LatLng.newInstance(0, 0), 2); 
					
					//map.setSize("100%", "100%");
			        map.setSize("600px", "600px"); 
			        map.addControl(new LargeMapControl()); 
			        map.setDoubleClickZoom(true);
			        map.setDraggable(true);
				    map.setGoogleBarEnabled(true);
			        
			        display.getMapPanel().add(map);
			        geocoder = new Geocoder();
			        bind();
			        doRetrieve();
			   }
		    });
	}

	public Geocoder getGeocoder(){
		return geocoder;
	}
	
	private void updateMapNgo(){

  	  Icon icon = Icon.DEFAULT_ICON;
  	  icon.setImageURL(Resources.INSTANCE.ngoMapIcon().getURL());
  	  icon.setIconSize(Size.newInstance(30, 30));
  	  
		if (ngoList != null) {
			for (DBRecordInfo ngo:ngoList){
				showRecord(ngo,icon,"Ngo");
			}
		}
		
			        
	}
	
	private void updateMapOrphanage(){

	  	  Icon icon = Icon.DEFAULT_ICON;
	  	  icon.setImageURL(Resources.INSTANCE.orphanageMapIcon().getURL());
	  	  icon.setIconSize(Size.newInstance(30, 30));
	  	  
		if (orphanageList != null){
			for (DBRecordInfo orphanage:orphanageList){
			    //showOrphanage(orphanage);
				Window.alert(orphanage.getName());
			    showRecord(orphanage,icon,"Orphanage");
			}
		}
			        
	}
	
	private void doRetrieve(){
        ngoRetrieve();  
        orphanageRetrieve(); 
	}
	
	private void ngoRetrieve(){
	    new RPCCall<ArrayList<DBRecordInfo>>() {
	      @Override
	      protected void callService(AsyncCallback<ArrayList<DBRecordInfo>> cb) {
	    	  ngoService.getDBRecordList(cb);
	      }

	      @Override
	      public void onSuccess(ArrayList<DBRecordInfo> result) {
	    	  ngoList = result;
	    	  updateMapNgo();
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving Ngo...");
	      }
	    }.retry(3);
	}
	
	private void orphanageRetrieve(){
	    new RPCCall<ArrayList<DBRecordInfo>>() {
	      @Override
	      protected void callService(AsyncCallback<ArrayList<DBRecordInfo>> cb) {
	    	  orphanageService.getDBRecordList(cb);
	      }

	      @Override
	      public void onSuccess(ArrayList<DBRecordInfo> result) {
	    	  orphanageList = result;
	    	  updateMapOrphanage();
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving Ngo...");
	      }
	    }.retry(3);
	}
	

	
	private void showRecord(final DBRecordInfo record, final Icon icon, final String title) {
		//final String address = ngo.getAddress();

		//Window.alert(ngo.getName()+" => "+ngo.getUniqueId());
		final InfoWindow info = map.getInfoWindow();
		MapMarkerPresenter markPresenter = new MapMarkerPresenter(this.currentUser, this.eventBus, record, new MapMarkerView());
		VerticalPanel container = new VerticalPanel();
		markPresenter.go(container);
		final InfoWindowContent infoContent = new InfoWindowContent(container);
		
	    final MarkerOptions markerOptions = MarkerOptions.newInstance();
        
		if (record.getLatitude() == -1 || record.getLatitude() == 0 ) {
			geocoder.getLatLng(record.getAddress(), new LatLngCallback() {
			      public void onFailure() {
			    	  //alert(address + " not found"); 
			      }

			      @SuppressWarnings("deprecation")
				public void onSuccess(LatLng point) {
			    	  record.setPoint(point.getLatitude(),point.getLongitude());
			    	  markerOptions.setDraggable(false);
			    	  
			    	  markerOptions.setIcon(icon);
					  markerOptions.setTitle(title);  
					   
			    	  final Marker marker = new Marker(LatLng.newInstance(record.getLatitude(),record.getLongitude()),markerOptions);
			    	  marker.addMarkerClickHandler(new MarkerClickHandler() {
			    			public void onClick(MarkerClickEvent event) {
			    				info.open(marker, infoContent);
			    			}
			    		});

			          map.addOverlay(marker);
			          
			          //info.open(marker, infoContent);
			      }
			    });
		} else{
	        Marker marker = new Marker(LatLng.newInstance(record.getLatitude(),record.getLongitude()),markerOptions);
	        map.addOverlay(marker);
	        info.open(marker, new InfoWindowContent(record.getName()));			
		}
				
	  }
	
	private void showNgo(final DBRecordInfo ngo) {
		//final String address = ngo.getAddress();

		//Window.alert(ngo.getName()+" => "+ngo.getUniqueId());
		final InfoWindow info = map.getInfoWindow();
		NgoMapMarkerInfoPresenter markPresenter = new NgoMapMarkerInfoPresenter(this.currentUser, this.ngoService, this.eventBus, ngo, new NgoMapMarkerInfoView());
		VerticalPanel container = new VerticalPanel();
		markPresenter.go(container);
		final InfoWindowContent infoContent = new InfoWindowContent(container);
		
	    final MarkerOptions markerOptions = MarkerOptions.newInstance();
		
		/*  With Callback
		geocoder.getLatLng(address, new LatLngCallback() {
	      public void onFailure() {
	        Window.alert(address + " not found");
	      }

	      public void onSuccess(LatLng point) {
	        //map.setCenter(point, 13);
	        Marker marker = new Marker(point);
	        map.addOverlay(marker);
	        info.open(marker, new InfoWindowContent(ngo.getName()));
	      }
	    });
	    */
        
		if (ngo.getLatitude() == -1 || ngo.getLatitude() == 0 ) {
			geocoder.getLatLng(ngo.getAddress(), new LatLngCallback() {
			      public void onFailure() {
			    	  //alert(address + " not found"); 
			      }

			      @SuppressWarnings("deprecation")
				public void onSuccess(LatLng point) {
			    	  ngo.setPoint(point.getLatitude(),point.getLongitude());
			    	  // update Ngo in database using ngoService.updateNgo(ngo);
			    	  
			    	  markerOptions.setDraggable(false);
			    	  //Icon icon = Icon.newInstance("../resources/NgoMapIcon.gif");
			    	  //icon.setIconSize(Size.newInstance(20, 34));
			    	  //Icon icon = Icon.newInstance(Resources.INSTANCE.ngoMapIcon().getURL());
			    	  Icon icon = Icon.DEFAULT_ICON;
			    	  icon.setImageURL(Resources.INSTANCE.ngoMapIcon().getURL());

			    	  //icon.setShadowURL("http://labs.google.com/ridefinder/images/mm_20_shadow.png");
			    	  icon.setIconSize(Size.newInstance(30, 30));
			    	  //icon.setShadowSize(Size.newInstance(22, 20));
			    	//icon.setIconAnchor(Point.newInstance(6, 20));
			    	//icon.setInfoWindowAnchor(Point.newInstance(5, 1));

			    	  
			    	  //MarkerOptions ops = MarkerOptions.newInstance(icon);
			    	  //Marker marker = new Marker(point, ops);
			    	  
			    	  markerOptions.setIcon(icon);
					  markerOptions.setTitle("Ngo");  
					   
			    	  final Marker marker = new Marker(LatLng.newInstance(ngo.getLatitude(),ngo.getLongitude()),markerOptions);
			    	  marker.addMarkerClickHandler(new MarkerClickHandler() {
			    			public void onClick(MarkerClickEvent event) {
			    				info.open(marker, infoContent);
			    			}
			    		});

			          map.addOverlay(marker);
			          
			          //info.open(marker, infoContent);
			      }
			    });
		} else{
	        Marker marker = new Marker(LatLng.newInstance(ngo.getLatitude(),ngo.getLongitude()),markerOptions);
	        map.addOverlay(marker);
	        info.open(marker, new InfoWindowContent(ngo.getName()));			
		}
				
	  }
	
	private void showOrphanage(final DBRecordInfo orphanage) {
		//final String address = ngo.getAddress();
		
		//Window.alert(orphanage.getName()+" => "+orphanage.getUniqueId());
		final InfoWindow info = map.getInfoWindow();
		OrphanageMapMarkerInfoPresenter markPresenter = new OrphanageMapMarkerInfoPresenter(this.eventBus, orphanage, new OrphanageMapMarkerInfoView());
		VerticalPanel container = new VerticalPanel();
		markPresenter.go(container);
		final InfoWindowContent infoContent = new InfoWindowContent(container);
		
	    final MarkerOptions markerOptions = MarkerOptions.newInstance();
        
		if (orphanage.getLatitude() == -1 || orphanage.getLatitude() == 0 ) {
			geocoder.getLatLng(orphanage.getAddress(), new LatLngCallback() {
			      public void onFailure() {
			    	  //alert(address + " not found"); 
			      }

			      @SuppressWarnings("deprecation")
				public void onSuccess(LatLng point) {
			    	  orphanage.setPoint(point.getLatitude(),point.getLongitude());
			    	  
			    	  markerOptions.setDraggable(false);
			    	  Icon icon = Icon.DEFAULT_ICON;
			    	  icon.setImageURL(Resources.INSTANCE.orphanageMapIcon().getURL());
			    	  icon.setIconSize(Size.newInstance(30, 30));
			    	  markerOptions.setIcon(icon);
					  markerOptions.setTitle("Orphanage");  
					   
			    	  final Marker marker = new Marker(LatLng.newInstance(orphanage.getLatitude(),orphanage.getLongitude()),markerOptions);
			    	  marker.addMarkerClickHandler(new MarkerClickHandler() {
			    			public void onClick(MarkerClickEvent event) {
			    				info.open(marker, infoContent);
			    			}
			    		});

			          map.addOverlay(marker);
			          
			          //info.open(marker, infoContent);
			      }
			    });
		} else{
	        Marker marker = new Marker(LatLng.newInstance(orphanage.getLatitude(),orphanage.getLongitude()),markerOptions);
	        map.addOverlay(marker);
	        info.open(marker, new InfoWindowContent(orphanage.getName()));			
		}
				
	  }
	


}
