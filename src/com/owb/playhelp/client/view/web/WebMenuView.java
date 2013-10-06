package com.owb.playhelp.client.view.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.web.WebMenuPresenter;

public class WebMenuView extends Composite implements WebMenuPresenter.Display {

	private static WebMenuViewUiBinder uiBinder = GWT
			.create(WebMenuViewUiBinder.class);

	interface WebMenuViewUiBinder extends
			UiBinder<Widget, WebMenuView> {
	}

	public WebMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		internalMenu.setVisible(false);
	}
	
	@UiField
	MenuBar mainMenu;
	@UiField
	MenuItem internalMenu;
	@UiField 
	MenuItem volunteerItem;
	@UiField
	MenuItem homeItem, mapItem, aboutUsItem, ourMissionItem, theTeamItem, joinOWBItem;
	@UiField
	MenuItem resourcesItem, joinNetworkItem, searchResourcesItem;
	@UiField 
	MenuItem needsItem, shareProjectsItem, searchProjectsItem;
	//@UiField
	ImageElement aboutUsImg;
	MenuItem ourViewItem;
	/*
	@UiField contextItem;
	@UiField
	MenuItem whatDoWeDoItem, healthItem, excerciseItem, educationItem, cleanWaterItem, foodItem, shelterItem, clothingItem, hygieneItem, joyItem, hopeOfFutureItem;
	@UiField
	MenuItem loveItem, responsabilitiesItem, safetyItem, guidanceItem, compassionateEnvironementItem, disciplineItem;
	@UiField
	MenuItem howDoWeHelpItem, howChildrenItem, howOrganizationsItem, howProjectsItem, howIndividualsItem;
	*/

	
	@Override
	public MenuBar getmainMenu() {
		return mainMenu;
	}

	@Override 
	public MenuItem getVolunteerItem(){
		return volunteerItem;
	}
	
	@Override
	public MenuItem getInternalMenu() {
		return internalMenu;
	}

	@Override
	public MenuItem gethomeItem() {
		return homeItem;
	}

	@Override
	public MenuItem getaboutUsItem() {
		return aboutUsItem;
	}

	@Override
	public ImageElement getaboutUsImg() {
		return aboutUsImg;
	}

	@Override
	public MenuItem getourMissionItem() {
		return ourMissionItem;
	}

	@Override
	public MenuItem getourViewItem() {
		return ourViewItem;
	}

	@Override
	public MenuItem gettheTeamItem() {
		return theTeamItem;
	}

	@Override
	public MenuItem getjoinOWBItem() {
		return joinOWBItem;
	}

	@Override
	public MenuItem getresourcesItem() {
		return resourcesItem;
	}
	
	@Override
	public MenuItem getjoinNetworkItem() {
		return joinNetworkItem;
	}
	
	@Override
	public MenuItem getsearchResourcesItem() {
		return searchResourcesItem;
	}

	@Override
	public MenuItem getneedsItem() {
		return needsItem;
	}

	@Override
	public MenuItem getshareProjectItem() {
		return shareProjectsItem;
	}
	
	@Override
	public MenuItem getsearchProjectsItem() {
		return searchProjectsItem;
	}

	@Override
	public MenuItem getmapItem() {
		return mapItem;
	}

/*
	@Override
	public MenuItem getcontextItem() {
		return contextItem;
	}
	
	@Override
	public MenuItem getwhatDoWeDoItem() {
		return whatDoWeDoItem;
	}

	@Override
	public MenuItem gethealthItem() {
		return healthItem;
	}

	@Override
	public MenuItem getexcerciseItem() {
		return excerciseItem;
	}

	@Override
	public MenuItem geteducationItem() {
		return educationItem;
	}

	@Override
	public MenuItem getfoodItem() {
		return foodItem;
	}

	@Override
	public MenuItem getcleanWaterItem() {
		return cleanWaterItem;
	}

	@Override
	public MenuItem getshelterItem() {
		return shelterItem;
	}
	@Override
	public MenuItem getclothingItem() {
		return clothingItem;
	}

	@Override
	public MenuItem gethygieneItem() {
		return hygieneItem;
	}

	@Override
	public MenuItem getjoyItem() {
		return joyItem;
	}

	@Override
	public MenuItem gethopeOfFutureItem() {
		return hopeOfFutureItem;
	}

	@Override
	public MenuItem getloveItem() {
		return loveItem;
	}

	@Override
	public MenuItem getresponsabilitiesItem() {
		return responsabilitiesItem;
	}

	@Override
	public MenuItem getsafetyItem() {
		return safetyItem;
	}

	@Override
	public MenuItem getguidanceItem() {
		return guidanceItem;
	}

	@Override
	public MenuItem getcompassionateEnvironementItem() {
		return compassionateEnvironementItem;
	}

	@Override
	public MenuItem getdisciplineItem() {
		return disciplineItem;
	}

	@Override
	public MenuItem gethowDoWeHelpItem() {
		return howDoWeHelpItem;
	}

	@Override
	public MenuItem gethowChildrenItem() {
		return howChildrenItem;
	}

	@Override
	public MenuItem gethowOrganizationsItem() {
		return howOrganizationsItem;
	}

	@Override
	public MenuItem gethowProjectsItem() {
		return howProjectsItem;
	}
	
	@Override
	public MenuItem gethowIndividualsItem() {
		return howIndividualsItem;
	}

*/


}
