/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;

public class AddOrphanageUpdateEvent extends GwtEvent<AddOrphanageUpdateEventHandler>{
	public static Type<AddOrphanageUpdateEventHandler> TYPE = new Type<AddOrphanageUpdateEventHandler>();
	public AddOrphanageUpdateEvent(){};
	@Override public Type<AddOrphanageUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(AddOrphanageUpdateEventHandler handler){
		handler.onAddOrphanageUpdate(this);
	}
	
}

