/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.dbrecord;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class DBRecordUpdateEvent extends GwtEvent<DBRecordUpdateEventHandler>{
	public static Type<DBRecordUpdateEventHandler> TYPE = new Type<DBRecordUpdateEventHandler>();
	
	private final DBRecordInfo updatedDBRecord;
	
	public DBRecordUpdateEvent(DBRecordInfo updatedDBRecord){
		this.updatedDBRecord = updatedDBRecord;
	}
	
	public DBRecordInfo getUpdatedDBRecord(){
		return updatedDBRecord;
	}
	
	@Override public Type<DBRecordUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(DBRecordUpdateEventHandler handler){
		handler.onDBRecordUpdate(this);
	}
	
}

