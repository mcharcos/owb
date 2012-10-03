package com.owb.playhelp.client.view;

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
    //this.center();
    //this.center();
    setPopupPosition(550, 350);
    add(icon);
  }

  @Override
  public Widget asWidget() {
    return this;
  }

}