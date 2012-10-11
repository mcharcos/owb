package com.owb.playhelp.client.view.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.web.WebMenuPresenter;


public class WebMenuView extends Composite implements WebMenuPresenter.Display {

	private static CenterPanelUiBinder uiBinder = GWT
			.create(CenterPanelUiBinder.class);

	interface CenterPanelUiBinder extends UiBinder<Widget, WebMenuView> {
	}

	public WebMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		//mainMenu.setStyleName(".gwt-MenuBar");
		// insert a separator
		mainMenu.insertSeparator(5);

	}
	
	public Widget asWidget(){
		//mainMenu.setStyleName(".gwt-MenuBar");
		return this;
	}

	/*
	@UiField
	  HorizontalPanel barPanel;*/
	
	@UiField
	MenuBar mainMenu;
	@UiField
	MenuItem addNgoItem;
	@UiField
	MenuItem addOrphanageItem;
	@UiField
	MenuItem missionItem, goalsItem, differentItem, probsolItem, teamItem;
	@UiField
	MenuItem joinusItem;

	/*
	@Override
	public HorizontalPanel getBarPanel() {
		  return barPanel;
		}*/
	@Override
	public MenuItem getAddNgoItem() {
		  return addNgoItem;
		}
	@Override
	public MenuItem getAddOrphanageItem() {
		  return addOrphanageItem;
		}
	public MenuItem getMissionItem() {
		  return missionItem;
		}
	public MenuItem getGoalsItem() {
		  return goalsItem;
		}
	public MenuItem getDifferentItem() {
		  return differentItem;
		}
	public MenuItem getProblemSolutionItem() {
		  return probsolItem;
		}
	public MenuItem getTeamItem() {
		  return teamItem;
		}
	public MenuItem getJoinusItem() {
		  return joinusItem;
		}
}
