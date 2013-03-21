/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.volunteer;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

public class ShowAddVolunteerEvent extends GwtEvent<ShowAddVolunteerEventHandler>{
	public static Type<ShowAddVolunteerEventHandler> TYPE = new Type<ShowAddVolunteerEventHandler>();
	
	private VolunteerInfo volunteer;
	
	public ShowAddVolunteerEvent(){
		this.volunteer = null;
	};
	public ShowAddVolunteerEvent(VolunteerInfo volunteer){
		this.volunteer = volunteer;
	};
	
	
	public VolunteerInfo getVolunteer(){
		return volunteer;
	}
	@Override public Type<ShowAddVolunteerEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowAddVolunteerEventHandler handler){
		handler.onShowPopupAddVolunteer(this);
	}
	
}
