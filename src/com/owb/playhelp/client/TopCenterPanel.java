package com.owb.playhelp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.TopCenterPanelPresenter;

/**
 * 
 * @author Miguel Charcos Llorens
 * 
 * The TopCenterPanel is the class that handles the view of the top panel in the central column of the window.
 * It will returns the various fields of the view which includes the three icons for the main views
 * of the app (news, map and friends) and the icon with the logo.
 *
 */
public class TopCenterPanel extends Composite implements TopCenterPanelPresenter.Display {

	private static TopCenterPanelUiBinder uiBinder = GWT
			.create(TopCenterPanelUiBinder.class);

	interface TopCenterPanelUiBinder extends UiBinder<Widget, TopCenterPanel> {
	}

	@UiField
	Image newsPanel;
	@UiField
	Image worldPanel;
	@UiField
	Image friendPanel;
	@UiField
	Image owbPanel;
	
	public TopCenterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}

	/**
	 * Get the field of the image containing the News icon
	 * 
	 * @return Image representing the news icon
	 */
	public Image getNewsPanel() {
	    return newsPanel;
	}

	/**
	 * Get the field of the image containing the map icon
	 * 
	 * @return Image representing the map icon
	 */
	public Image getWorldPanel() {
	    return worldPanel;
	}

	/**
	 * Get the field of the image containing the friend icon
	 * 
	 * @return Image representing the friend icon
	 */
	public Image getFriendPanel() {
	    return friendPanel;
	}

	/**
	 * Get the field of the image containing the owb icon
	 * 
	 * @return Image representing the owb icon
	 */
	public Image getOwbPanel() {
	    return friendPanel;
	}
}
