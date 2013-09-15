/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;

public class DBRecordRemoveEvent extends GwtEvent<DBRecordRemoveEventHandler>{
	public static Type<DBRecordRemoveEventHandler> TYPE = new Type<DBRecordRemoveEventHandler>();
	
	private final DBRecordInfo record;
	
	public DBRecordRemoveEvent(DBRecordInfo record){
		this.record = record;
	};
	
	public DBRecordInfo getRecord(){
		return record;
	}
	
	@Override public Type<DBRecordRemoveEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(DBRecordRemoveEventHandler handler){
		handler.onDBRecordRemove(this);
	}
	
}
