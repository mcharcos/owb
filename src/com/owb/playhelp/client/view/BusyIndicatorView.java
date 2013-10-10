package com.owb.playhelp.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.BusyIndicatorPresenter;
import com.owb.playhelp.client.resources.Resources;

/**
 * Displays a loading message, indicating the app is working while
 * the user is waiting.
 * 
 */
public class BusyIndicatorView extends PopupPanel implements BusyIndicatorPresenter.Display{
  private Image icon = new Image(Resources.INSTANCE.progressBar());
	
  public BusyIndicatorView() {
    //setAnimationEnabled(false);
	int winWidth = Window.getClientWidth();
	int winHeight = Window.getClientHeight();
	if (winWidth > 0 && winHeight > 0){
		setPopupPosition(winWidth/2-icon.getWidth()/2, winHeight/3-icon.getHeight()/2);
	} else {
		this.center();
	}
    add(icon);
  }

  @Override
  public Widget asWidget() {
    return this;
  }

}