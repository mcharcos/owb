package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.StandardInfoPresenter;

public class StandardInfoView extends Composite implements StandardInfoPresenter.Display {

	private static DBRecordInfoViewUiBinder uiBinder = GWT
			.create(DBRecordInfoViewUiBinder.class);

	interface DBRecordInfoViewUiBinder extends
			UiBinder<Widget, StandardInfoView> {
	}

	public StandardInfoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label recordName;
	@UiField
	HTML recordDescription;
	@UiField
	HTMLPanel mainPanel;
	@UiField
	Anchor editBut, removeBut, reportBut, followBut, fulldescBut, addstdBut, addprojBut;


	  @Override
	public Widget asWidget(){
		return this;
	}

	  @Override
	public HTMLPanel getMainPanel(){
		return mainPanel;
	}
	  @Override
	public Label getRecordName(){
		return recordName;
	}
	  @Override
	public HTML getRecordDescription(){
		return recordDescription;
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
		public Anchor getAddStdBut(){
			return addstdBut;
		}
		@Override
		public Anchor getAddprojBut(){
		  return addprojBut;
		}


}
