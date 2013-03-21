/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.volunteer;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.event.volunteer.VolunteerUpdateEventHandler;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

public class VolunteerUpdateEvent extends GwtEvent<VolunteerUpdateEventHandler>{
	public static Type<VolunteerUpdateEventHandler> TYPE = new Type<VolunteerUpdateEventHandler>();
	
	private final VolunteerInfo updatedVolunteer;
	
	public VolunteerUpdateEvent(VolunteerInfo updatedVolunteer){
		this.updatedVolunteer = updatedVolunteer;
	}
	
	public VolunteerInfo getUpdatedVolunteer(){
		return updatedVolunteer;
	}
	
	@Override public Type<VolunteerUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(VolunteerUpdateEventHandler handler){
		handler.onVolunteerUpdate(this);
	}
	
}

