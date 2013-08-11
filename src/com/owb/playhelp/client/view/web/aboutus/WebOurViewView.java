package com.owb.playhelp.client.view.web.aboutus;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.web.WebPagesPresenter;

public class WebOurViewView extends Composite implements WebPagesPresenter.Display {

	private static WebOurViewViewUiBinder uiBinder = GWT
			.create(WebOurViewViewUiBinder.class);

	interface WebOurViewViewUiBinder extends UiBinder<Widget, WebOurViewView> {
	}

	@UiField HTMLPanel view0, view1, view2, view3, view4, view5, view6, view7, view8;
	@UiField Anchor link0, link1, link2, link3, link4, link5, link6, link7, link8;

	private ArrayList<HTMLPanel> viewList;
	private ArrayList<Anchor> linkList;
	
	public WebOurViewView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public WebOurViewView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
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
