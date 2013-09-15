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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;


import com.owb.playhelp.client.presenter.web.WebPictureBandPresenter;
import com.owb.playhelp.client.resources.Resources;

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

	public WebPictureBandView(ArrayList<Image> imagenes, boolean animate, boolean manual) {
		initWidget(uiBinder.createAndBindUi(this));
		listImage=imagenes;
				
		initComponents(animate, manual);
	}

	
	public WebPictureBandView(ArrayList<Image> imagenes) {
		this(imagenes, true, false);
	}

	private void initComponents(boolean animate, boolean manual){     
        //panelSlider.setAnimationEnabled(true);
        panelSlider.setAnimationVertical(false);
        panelSlider.setAnimationDuration(2000);
        Iterator<Image> i=listImage.iterator();
        while(i.hasNext()){
            panelSlider.add(i.next());                  
        }
        panelSlider.showWidget(0);
        setSize(manual);
        
        if (animate){
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
    }


    private void stlNextLeft(int width, int height){
    	nextLeft.getElement().getStyle().setWidth(width,Unit.PCT);
        nextLeft.getElement().getStyle().setHeight(height,Unit.PCT);
        nextLeft.getElement().getStyle().setFloat(Style.Float.LEFT);
        nextLeft.getElement().getStyle().setProperty("background", "url("+Resources.INSTANCE.getBack().getSafeUri().asString()+") no-repeat scroll 0% 0% transparent");
        nextLeft.getElement().getStyle().setProperty("backgroundPosition","center");
        nextLeft.getElement().getStyle().setBorderWidth(0, Unit.PX);
        nextLeft.getElement().getStyle().setFontSize(30, Unit.PX);
    }
   
    private void stlNextRigth(int width, int height){
        nextRight.getElement().getStyle().setWidth(width,Unit.PCT);
        nextRight.getElement().getStyle().setHeight(height,Unit.PCT);
        nextRight.getElement().getStyle().setFloat(Style.Float.LEFT);
        nextRight.getElement().getStyle().setProperty("background", "url("+Resources.INSTANCE.getNext().getSafeUri().asString()+") no-repeat scroll 0% 0% transparent");
        nextRight.getElement().getStyle().setProperty("backgroundPosition","center");
        nextRight.getElement().getStyle().setBorderWidth(0, Unit.PX);
        nextRight.getElement().getStyle().setFontSize(30, Unit.PX);
    }
   
    private void stlSlider(int width, int height){
        this.getElement().getStyle().setWidth(width,Unit.PCT);
        this.getElement().getStyle().setHeight(height,Unit.PCT);
        this.getElement().getStyle().setMargin(0, Unit.PX);      
        this.getElement().getStyle().setPadding(0, Unit.PX);
        this.getElement().getStyle().setBorderWidth(0, Unit.PX);
    }
   
    private void stlDeckPanel(int width, int height){
        panelSlider.getElement().getStyle().setWidth(width,Unit.PCT);
        panelSlider.getElement().getStyle().setHeight(height,Unit.PCT);
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
   public void setSize(boolean manual){
	   	nextLeft.setVisible(manual);
		nextRight.setVisible(manual);

		int centerwidth = 100;
		int centerheight = 100;
		
		if (manual){
			centerwidth= 80;
			int sidewidth = 10;
			stlNextLeft(sidewidth,centerheight);
	        stlNextRigth(sidewidth,centerheight);
			
		}
        stlSlider(100,100);
        stlDeckPanel(centerwidth,centerheight);
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
