package com.owb.playhelp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.LeftPanelPresenter;

/**
 * 
 * @author Miguel Charcos Llorens
 * Represents the class that will contain the views of the left side of the window. This view contains the 
 * badge of the user with the picture profile, the main menu to navigate on the web and the character information 
 *
 */
public class LeftPanel extends Composite implements LeftPanelPresenter.Display {

	private static LeftPanelUiBinder uiBinder = GWT
			.create(LeftPanelUiBinder.class);

	interface LeftPanelUiBinder extends UiBinder<Widget, LeftPanel> {
	}

	public LeftPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Image owbIcon;
	
	//@UiField
	  VerticalPanel profilePanel=null;
	//@UiField
	  VerticalPanel testPanel=null;
	//@UiField
	  VerticalPanel menuPanel=null;

	/**
	 * Returns the panel containing the user profile information
	 * 
	 * @return Profile panel
	 */
	public VerticalPanel getProfilePanel() {
	  return profilePanel;
	}

	/**
	 * Returns the test panel. Depracated??
	 */
	public VerticalPanel getTestPanel() {
	  return testPanel;
	}
	
	/**
	 * Returns the panel containing the main menu of the web navigation.
	 * 
	 *@return main menu panel
	 */
	public VerticalPanel getMenuPanel() {
		  return menuPanel;
		}
	
	@Override
	public Image getOWBIcon(){
		return owbIcon;
	}

}
