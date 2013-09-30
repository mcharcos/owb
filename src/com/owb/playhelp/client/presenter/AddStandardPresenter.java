package com.owb.playhelp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.orphanage.AddOrphanageCancelEvent;
import com.owb.playhelp.client.event.orphanage.AddOrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.NgoServiceAsync;
import com.owb.playhelp.client.service.OrphanageServiceAsync;
import com.owb.playhelp.client.service.StandardServiceAsync;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class AddStandardPresenter implements Presenter{
	public interface Display {
		Widget asWidget();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		Label getNameField();
		HasValue<String> getWaterField();
		HasValue<String> getFoodField();
		HasValue<String> getShelterField();
		HasValue<String> getClothingField();
		HasValue<String> getMedicineField();
		HasValue<String> getHygieneField();
		HasValue<String> getSafetyField();
		HasValue<String> getActivityField();
		HasValue<String> getEducationField();
		HasValue<String> getGuidanceField();
		HasValue<String> getResponsibilityField();
		HasValue<String> getDisciplineField();
		HasValue<String> getLoveField();
		HasValue<String> getCompassionField();
		HasValue<String> getJoyField();
		HasValue<String> getHopeField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private StandardInfo standard;
	private final StandardServiceAsync standardService;
	
	public AddStandardPresenter(StandardInfo standard, StandardServiceAsync standardService, SimpleEventBus eventBus, Display display){
		this.standard = standard;
		this.standardService = standardService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
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
		
		if (standard == null) return;
        
		if (standard!=null){
			this.display.getWaterField().setValue(String.valueOf(standard.getWaterStatus()));
			this.display.getFoodField().setValue(String.valueOf(standard.getFoodStatus()));
			this.display.getShelterField().setValue(String.valueOf(standard.getShelterStatus()));
			this.display.getClothingField().setValue(String.valueOf(standard.getClothingStatus()));
			this.display.getMedicineField().setValue(String.valueOf(standard.getMedicineStatus()));
			this.display.getHygieneField().setValue(String.valueOf(standard.getHygieneStatus()));
			this.display.getSafetyField().setValue(String.valueOf(standard.getSafetyStatus()));
			this.display.getActivityField().setValue(String.valueOf(standard.getActivityStatus()));
			this.display.getEducationField().setValue(String.valueOf(standard.getEducationStatus()));
			this.display.getGuidanceField().setValue(String.valueOf(standard.getGuidanceStatus()));
			this.display.getResponsibilityField().setValue(String.valueOf(standard.getResponsibilityStatus()));
			this.display.getDisciplineField().setValue(String.valueOf(standard.getDisciplineStatus()));
			this.display.getLoveField().setValue(String.valueOf(standard.getLoveStatus()));
			this.display.getCompassionField().setValue(String.valueOf(standard.getCompassionStatus()));
			this.display.getJoyField().setValue(String.valueOf(standard.getJoyStatus()));
			this.display.getHopeField().setValue(String.valueOf(standard.getHopeStatus()));
		}
	}
    
	protected void setTitleName(){
		this.display.getNameField().setText("Standard");
	}
	  private void doSave() {
		  standard.setWater(Long.valueOf(this.display.getWaterField().getValue()),"");
		  standard.setFood(Long.valueOf(this.display.getFoodField().getValue()),"");
		  standard.setShelter(Long.valueOf(this.display.getShelterField().getValue()),"");
		  standard.setClothing(Long.valueOf(this.display.getClothingField().getValue()),"");
		  standard.setMedicine(Long.valueOf(this.display.getMedicineField().getValue()),"");
		  standard.setHygiene(Long.valueOf(this.display.getHygieneField().getValue()),"");
		  standard.setSafety(Long.valueOf(this.display.getSafetyField().getValue()),"");
		  standard.setActivity(Long.valueOf(this.display.getActivityField().getValue()),"");
		  standard.setEducation(Long.valueOf(this.display.getEducationField().getValue()),"");
		  standard.setGuidance(Long.valueOf(this.display.getGuidanceField().getValue()),"");
		  standard.setResponsibility(Long.valueOf(this.display.getResponsibilityField().getValue()),"");
		  standard.setDiscipline(Long.valueOf(this.display.getDisciplineField().getValue()),"");
		  standard.setLove(Long.valueOf(this.display.getLoveField().getValue()),"");
		  standard.setCompassion(Long.valueOf(this.display.getCompassionField().getValue()),"");
		  standard.setJoy(Long.valueOf(this.display.getJoyField().getValue()),"");
		  standard.setHope(Long.valueOf(this.display.getHopeField().getValue()),"");
		  

	    new RPCCall<StandardInfo>() {
	      @Override
	      protected void callService(AsyncCallback<StandardInfo> cb) {
	    	  standardService.updateStandard(standard, cb);
	      }

	      @Override
	      public void onSuccess(StandardInfo result) {
	        //GWT.log("OrphanageAddPresenter: Firing ShowPopupAddOrphanageStatusEvent");
	        //eventBus.fireEvent(new OrphanageUpdateEvent((OrphanageInfo) result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving standard...");
	      }
	    }.retry(3);
	  }
}
