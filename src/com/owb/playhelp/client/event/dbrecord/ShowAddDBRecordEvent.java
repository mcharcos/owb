/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.dbrecord;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;

public class ShowAddDBRecordEvent extends GwtEvent<ShowAddDBRecordEventHandler>{
	public static Type<ShowAddDBRecordEventHandler> TYPE = new Type<ShowAddDBRecordEventHandler>();
	
	private DBRecordInfo record;
	
	public ShowAddDBRecordEvent(){
		this.record = null;
	};
	public ShowAddDBRecordEvent(DBRecordInfo record){
		this.record = record;
	};
	
	public DBRecordInfo getDBRecord(){
		return record;
	}
	@Override public Type<ShowAddDBRecordEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowAddDBRecordEventHandler handler){
		handler.onShowAddDBRecord(this);
	}
	
}
