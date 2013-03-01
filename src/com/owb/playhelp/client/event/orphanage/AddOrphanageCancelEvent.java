/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;

public class AddOrphanageCancelEvent extends GwtEvent<AddOrphanageCancelEventHandler>{
	public static Type<AddOrphanageCancelEventHandler> TYPE = new Type<AddOrphanageCancelEventHandler>();
	public AddOrphanageCancelEvent(){};
	@Override public Type<AddOrphanageCancelEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(AddOrphanageCancelEventHandler handler){
		handler.onAddOrphanageCancel(this);
	}
	
}

