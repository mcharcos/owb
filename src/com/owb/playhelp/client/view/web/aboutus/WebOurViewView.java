package com.owb.playhelp.client.view.web.aboutus;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.web.WebPagesPresenter;
import com.owb.playhelp.client.presenter.web.WebPictureBandPresenter;
import com.owb.playhelp.client.resources.Resources;
import com.owb.playhelp.client.view.web.home.WebPictureBandView;

public class WebOurViewView extends Composite implements WebPagesPresenter.Display {

	private static WebOurViewViewUiBinder uiBinder = GWT
			.create(WebOurViewViewUiBinder.class);

	interface WebOurViewViewUiBinder extends UiBinder<Widget, WebOurViewView> {
	}

	@UiField HTMLPanel view0, view1, view2, view3, view4, view5, view6, view7, view8;
	@UiField Anchor link0, link1, link2, link3, link4, link5, link6, link7, link8;
	@UiField HorizontalPanel sliderPanel;

	private ArrayList<HTMLPanel> viewList;
	private ArrayList<Anchor> linkList;

	// Image slider
	TabLayoutPanel tabSlider = new TabLayoutPanel(1.50, Unit.PX);

	// List of images that will be shown in the slider
	ArrayList<Image> images=new ArrayList<Image>();
	final private Image image1=new Image(Resources.INSTANCE.homeimg1());
	final private Image image2=new Image(Resources.INSTANCE.homeimg2());
	final private Image image3=new Image(Resources.INSTANCE.homeimg3());
	final private Image image4=new Image(Resources.INSTANCE.homeimg4());
	
	public WebOurViewView() {
		initWidget(uiBinder.createAndBindUi(this));

		// Define the properties of the slider
		tabSlider.setAnimationDuration(100);  // This is for tableLayout
		images.add(image1);
		images.add(image2);
		images.add(image3);
		images.add(image4);
		WebPictureBandView imageSlider = new WebPictureBandView(images, false, true);
		WebPictureBandPresenter imageSliderPresenter = new WebPictureBandPresenter(imageSlider);
		imageSliderPresenter.go(tabSlider);
		//tabSlider.add(imageSlider);

		// Set table properties
		tabSlider.getElement().getStyle().setWidth(820,Unit.PX);
		tabSlider.getElement().getStyle().setHeight(300,Unit.PX);
		tabSlider.getElement().getStyle().setMargin(0, Unit.PX);       
		tabSlider.getElement().getStyle().setPadding(0, Unit.PX);
		tabSlider.getElement().getStyle().setBorderWidth(0, Unit.PX);
		
		// Add to panel
		sliderPanel.add(tabSlider);
	}
	
	@Override
	  public Widget asWidget() {
		  viewList = new ArrayList<HTMLPanel>(
					Arrays.asList(view0,view1,view2,view3,view4, view5, view6, view7, view8));
		  linkList = new ArrayList<Anchor>(
					Arrays.asList(link0,link1, link2, link3, link4, link5, link6, link7, link8));
		  return this;
	  }

	@Override
	public ArrayList<HTMLPanel> getViewList(){
		return viewList;
	}
	@Override
	public ArrayList<Anchor> getLinkList(){
		return linkList;
	}

}
