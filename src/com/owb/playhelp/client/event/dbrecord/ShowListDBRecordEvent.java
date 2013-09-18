/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.dbrecord;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;

public class ShowListDBRecordEvent extends GwtEvent<ShowListDBRecordEventHandler>{
	public static Type<ShowListDBRecordEventHandler> TYPE = new Type<ShowListDBRecordEventHandler>();
	
	private DBRecordInfo record;

	public ShowListDBRecordEvent(){
		this.record = null;
	};
	public ShowListDBRecordEvent(DBRecordInfo record){
		this.record = record;
	};

	public DBRecordInfo getDBRecord(){
		return record;
	}
	
	@Override public Type<ShowListDBRecordEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowListDBRecordEventHandler handler){
		handler.onShowListDBRecord(this);
	}
	
}

