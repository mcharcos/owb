package com.owb.playhelp.client.view.web.home;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.web.WebHomePresenter;
import com.owb.playhelp.client.resources.Resources;

public class WebHomeView extends Composite implements WebHomePresenter.Display {

	private static WebHomeViewUiBinder uiBinder = GWT
			.create(WebHomeViewUiBinder.class);

	interface WebHomeViewUiBinder extends UiBinder<Widget, WebHomeView> {
	}

	//@UiField TabLayoutPanel sliderPanel;
	@UiField HorizontalPanel sliderPanel, infoBandField;
	
	// Image slider
	WebPictureBandView imageSlider;
	TabLayoutPanel tabSlider = new TabLayoutPanel(1.50, Unit.PX);
	
	// List of images that will be shown in the slider
	ArrayList<Image> images=new ArrayList<Image>();
	final private Image image1=new Image(Resources.INSTANCE.homeimg1());
	final private Image image2=new Image(Resources.INSTANCE.homeimg2());
	final private Image image3=new Image(Resources.INSTANCE.homeimg3());
	final private Image image4=new Image(Resources.INSTANCE.homeimg4());
	
	public WebHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
			
		// Define the properties of the slider
		tabSlider.setAnimationDuration(1000);  // This is for tableLayout
		images.add(image1);
		images.add(image2);
		images.add(image3);
		images.add(image4);
		imageSlider = new WebPictureBandView(images);
		tabSlider.add(imageSlider);

		// Set table properties
		tabSlider.getElement().getStyle().setWidth(820,Unit.PX);
		tabSlider.getElement().getStyle().setHeight(300,Unit.PX);
		tabSlider.getElement().getStyle().setMargin(5, Unit.PX);       
		tabSlider.getElement().getStyle().setPadding(0, Unit.PX);
		tabSlider.getElement().getStyle().setBorderWidth(0, Unit.PX);
		
		// Add to panel
		sliderPanel.add(tabSlider);
	}

	@Override
	public HorizontalPanel getInfoBandField() {
		return infoBandField;
	}
	@Override 
	public HTMLPanel getNewsPanel1(){
		return new HTMLPanel("");
	}
	@Override 
	public HTMLPanel getNewsPanel2(){
		return new HTMLPanel("");
	}
}
