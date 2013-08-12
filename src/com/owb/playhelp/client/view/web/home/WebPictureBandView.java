/**
 * Code inspired in http://www.enjava2.com/2012/05/slider-basico-en-gwt.html
 */

package com.owb.playhelp.client.view.web.home;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;


import com.owb.playhelp.client.presenter.web.WebPictureBandPresenter;

public class WebPictureBandView extends Composite implements WebPictureBandPresenter.Display {

	private static WebPictureBandViewUiBinder uiBinder = GWT
			.create(WebPictureBandViewUiBinder.class);

	interface WebPictureBandViewUiBinder extends
			UiBinder<Widget, WebPictureBandView> {
	}
	
	private List<Image> listImage;
    private Timer timer;

    @UiField DeckLayoutPanel panelSlider;
    @UiField Label nextLeft, nextRight;
    
	public WebPictureBandView(ArrayList<Image> imagenes) {
		initWidget(uiBinder.createAndBindUi(this));
		listImage=imagenes;
		initComponents();
	}

	private void initComponents(){     
        //panelSlider.setAnimationEnabled(true);
        panelSlider.setAnimationVertical(false);
        panelSlider.setAnimationDuration(2000);
        Iterator<Image> i=listImage.iterator();
        while(i.hasNext()){
            panelSlider.add(i.next());                  
        }
        panelSlider.showWidget(0);
        stlSlider();
        stlDeckPanel();
        stlNextLeft();
        stlNextRigth();
        timer=new Timer(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int index = panelSlider.getVisibleWidgetIndex();
                index++;
                if (index == panelSlider.getWidgetCount()) index = 0;
                panelSlider.showWidget(index);
            }          
        };
        timer.scheduleRepeating(5000);
    }


    private void stlNextLeft(){
    	nextLeft.setVisible(false);
    	/*
    	nextLeft.getElement().getStyle().setWidth(10,Unit.PCT);
        nextLeft.getElement().getStyle().setHeight(100,Unit.PCT);
        nextLeft.getElement().getStyle().setFloat(Style.Float.LEFT);
        nextLeft.getElement().getStyle().setProperty("background", "url("+Resources.INSTANCE.getBack().getSafeUri().asString()+") no-repeat scroll 0% 0% transparent");
        nextLeft.getElement().getStyle().setProperty("backgroundPosition","center");
        nextLeft.getElement().getStyle().setBorderWidth(0, Unit.PX);
        nextLeft.getElement().getStyle().setFontSize(30, Unit.PX);
        */
    }
   
    private void stlNextRigth(){
    	nextRight.setVisible(false);
    	/*
        nextRight.getElement().getStyle().setWidth(10,Unit.PCT);
        nextRight.getElement().getStyle().setHeight(100,Unit.PCT);
        nextRight.getElement().getStyle().setFloat(Style.Float.LEFT);
        nextRight.getElement().getStyle().setProperty("background", "url("+Resources.INSTANCE.getNext().getSafeUri().asString()+") no-repeat scroll 0% 0% transparent");
        nextRight.getElement().getStyle().setProperty("backgroundPosition","center");
        nextRight.getElement().getStyle().setBorderWidth(0, Unit.PX);
        nextRight.getElement().getStyle().setFontSize(30, Unit.PX);
        */
    }
   
    private void stlSlider(){
        this.getElement().getStyle().setWidth(100,Unit.PCT);
        this.getElement().getStyle().setHeight(100,Unit.PCT);
        this.getElement().getStyle().setMargin(0, Unit.PX);      
        this.getElement().getStyle().setPadding(0, Unit.PX);
        this.getElement().getStyle().setBorderWidth(0, Unit.PX);
    }
   
    private void stlDeckPanel(){
        panelSlider.getElement().getStyle().setWidth(100,Unit.PCT);
        panelSlider.getElement().getStyle().setHeight(100,Unit.PCT);
        panelSlider.getElement().getStyle().setFloat(Style.Float.LEFT);
    }
   
   
   
    //Getters y Setters
   @Override
    public DeckLayoutPanel getPanelSlider() {
        return panelSlider;
    }

   @Override
    public void setPanelSlider(DeckLayoutPanel panelSlider) {
        this.panelSlider = panelSlider;
    }
   
   @Override
    public List<Image> getListImage() {
        return listImage;
    }

   @Override
    public void setListImage(List<Image> listImage) {
        this.listImage = listImage;
    }

   @Override
    public Label getNextLeft() {
        return nextLeft;
    }

   @Override
    public void setNextLeft(Label nextLeft) {
        this.nextLeft = nextLeft;
    }

   @Override
    public Label getNextRight() {
        return nextRight;
    }

   @Override
    public void setNextRight(Label nextRight) {
        this.nextRight = nextRight;
    }

   @Override
    public Timer getTimer() {
        return timer;
    }

   @Override
    public void setTimer(Timer timer) {
        this.timer = timer;
    }


}
