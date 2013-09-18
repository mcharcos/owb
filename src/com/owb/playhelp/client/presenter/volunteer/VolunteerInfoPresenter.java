package com.owb.playhelp.client.presenter.volunteer;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.owb.playhelp.client.presenter.DBRecordInfoPresenter;
import com.owb.playhelp.shared.DBRecordInfo;

public class VolunteerInfoPresenter extends DBRecordInfoPresenter{


	public VolunteerInfoPresenter(SimpleEventBus eventBus, DBRecordInfo record, Display display) {
		super(eventBus,record,display);
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.add(display.asWidget());
		display.getRecordName().setText(record.getName());
		
		// Separate the Description to make it more human readable
		String interests = record.getDescription();
		String eol = "<br></br>"; 
		String message = "";
		if (interests.contains("||")) {
			  String[] parts = interests.split("[|]+");
			  for (String p: parts){
				  if (p.contains("Yes")){
					  message = message + p + eol; 
				  }
			  }
			} else {
			  message = message + interests;
			}
		display.getRecordDescription().setHTML(message);
		
		display.getFulldescBut().setVisible(false);
		display.getEditBut().setVisible(false);
		display.getRemoveBut().setVisible(false);
		display.getReportBut().setVisible(false);
		display.getFollowBut().setVisible(false);
		display.getFulldescBut().setVisible(false);
		display.getAddprojBut().setVisible(false);
		
		
		bind();
	};
}
