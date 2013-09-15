package com.owb.playhelp.client.view.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.orphanage.OrphanageMapMarkerInfoPresenter;

public class OrphanageMapMarkerInfoView extends Composite implements OrphanageMapMarkerInfoPresenter.Display {

	private static OrphanageMapMarkerInfoViewUiBinder uiBinder = GWT
			.create(OrphanageMapMarkerInfoViewUiBinder.class);

	interface OrphanageMapMarkerInfoViewUiBinder extends
			UiBinder<Widget, OrphanageMapMarkerInfoView> {
	}

	public OrphanageMapMarkerInfoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label orphanageName, orphanageDescription;
	@UiField
	HTMLPanel mainPanel;
	@UiField
	Anchor editBut, removeBut, reportBut, followBut, fulldescBut,addprojBut;

	  @Override
	public Widget asWidget(){
		return this;
	}

	  @Override
	public HTMLPanel getMainPanel(){
		return mainPanel;
	}
	  @Override
	public Label getOrphanageName(){
		return orphanageName;
	}
	  @Override
	public Label getOrphanageDescription(){
		return orphanageDescription;
	}

		@Override
		public Anchor getEditBut(){
		  return editBut;
		}
		@Override
		public Anchor getRemoveBut(){
		  return removeBut;
		}
		@Override
		public Anchor getReportBut(){
		  return reportBut;
		}
		@Override
		public Anchor getFollowBut(){
		  return followBut;
		}
		@Override
		public Anchor getFulldescBut(){
		  return fulldescBut;
		}
		@Override
		public Anchor getAddprojBut(){
		  return addprojBut;
		}


}
