/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class NgoUpdateEvent extends GwtEvent<NgoUpdateEventHandler>{
	public static Type<NgoUpdateEventHandler> TYPE = new Type<NgoUpdateEventHandler>();
	
	private final DBRecordInfo updatedNgo;
	
	public NgoUpdateEvent(DBRecordInfo updatedNgo){
		this.updatedNgo = updatedNgo;
	}
	
	public DBRecordInfo getUpdatedNgo(){
		return updatedNgo;
	}
	
	@Override public Type<NgoUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(NgoUpdateEventHandler handler){
		handler.onNgoUpdate(this);
	}
	
}

