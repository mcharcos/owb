package com.owb.playhelp.client.presenter.web;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.Presenter;

public class WebPictureBandPresenter implements Presenter{
	public interface Display {
		Widget asWidget();
		DeckLayoutPanel getPanelSlider();
		void setTimer(Timer timer);
		Label getNextRight();
		void setNextLeft(Label nextLeft);
		Label getNextLeft();
		void setListImage(List<Image> listImage);
		void setNextRight(Label nextRight);
		Timer getTimer();
		void setPanelSlider(DeckLayoutPanel panelSlider);
		List<Image> getListImage();
		void setSize(boolean manual);
	}

	private final Display display;
	private final SimpleEventBus eventBus;

	public WebPictureBandPresenter(Display display) {
		this.eventBus = null;
		this.display = display;
	}
	
	public WebPictureBandPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}


	public void bind() {
		this.display.getNextLeft().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateToLeft();
	        }
	      });
		this.display.getNextRight().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateToRight();
	        }
	      });
		
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	private void updateToLeft(){
		int tamanno=this.display.getListImage().size();
        int indexActual=this.display.getPanelSlider().getVisibleWidgetIndex();
        if(indexActual==0){
        	this.display.getPanelSlider().showWidget(tamanno-1);
        }else{
        	this.display.getPanelSlider().showWidget(indexActual-1);
        }
	}
	private void updateToRight(){
		int tamanno=this.display.getListImage().size();
        int indexActual=this.display.getPanelSlider().getVisibleWidgetIndex();
        if(indexActual==tamanno-1){
        	this.display.getPanelSlider().showWidget(0);
        }else{
        	this.display.getPanelSlider().showWidget(indexActual+1);
        }
	}
}
