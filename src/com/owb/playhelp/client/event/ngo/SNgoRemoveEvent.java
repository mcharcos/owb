/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.StandardInfo;

public class SNgoRemoveEvent extends GwtEvent<SNgoRemoveEventHandler>{
	public static Type<SNgoRemoveEventHandler> TYPE = new Type<SNgoRemoveEventHandler>();
	
	private final StandardInfo standard;
	
	public SNgoRemoveEvent(StandardInfo standard){
		this.standard = standard;
	};
	
	public StandardInfo getSNgo(){
		return standard;
	}
	
	@Override public Type<SNgoRemoveEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(SNgoRemoveEventHandler handler){
		handler.onSNgoRemove(this);
	}
	
}
