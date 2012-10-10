/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.user;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
//import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.client.resources.Resources;


public class UserBadgePresenter implements Presenter{
	  public interface Display {
			Image getProfilePictureFrame();
		    Widget asWidget();
		  }

	  private final LoginServiceAsync loginService;
	  private final SimpleEventBus eventBus;
	  private final Display display;

	  private UserProfileInfo currentUser;

	  public UserBadgePresenter(LoginServiceAsync loginService, SimpleEventBus eventBus,
	      Display display) {
	    this.loginService = loginService;
	    this.eventBus = eventBus;
	    this.display = display;
	  }
	  
	  public void bind(){		  
		  createUI();
		  
		  // Listen to login events so we update the info of the user in this panel
	  }
	  
	  private void createUI(){
		  if (currentUser != null){
			  userLoggedin();
		  } else {
			  userLoggedout();
		  }
	  }
	  
	  private void userLoggedin(){		  
		  if (currentUser == null){
			  Window.alert("UserBadgePresenter:: Error in userLoggedin, null user");
			  userLoggedout();
			  return;
		  }
		  UserBadgePresenter.this.display.getProfilePictureFrame().setResource(Resources.INSTANCE.sampleProfilePicture());
		  // change text of loginout link to "logout"		  
	  }
	  private void userLoggedout(){		  
		  UserBadgePresenter.this.display.getProfilePictureFrame().setResource(Resources.INSTANCE.loggedoutPicture());
		  // change text of loginout link to "login"		  
	  }	  
	  
	  public void go(final HasWidgets container){
		  container.clear();
		  container.add(display.asWidget());
		  bind();
	  }
	  
}
