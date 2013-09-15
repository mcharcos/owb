/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;

public class ShowListOrphanageEvent extends GwtEvent<ShowListOrphanageEventHandler>{
	public static Type<ShowListOrphanageEventHandler> TYPE = new Type<ShowListOrphanageEventHandler>();
	public ShowListOrphanageEvent(){};
	@Override public Type<ShowListOrphanageEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowListOrphanageEventHandler handler){
		handler.onShowListOrphanage(this);
	}
	
}

