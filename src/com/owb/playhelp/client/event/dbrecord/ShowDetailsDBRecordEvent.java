/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.dbrecord;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;

public class ShowDetailsDBRecordEvent extends GwtEvent<ShowDetailsDBRecordEventHandler>{
	public static Type<ShowDetailsDBRecordEventHandler> TYPE = new Type<ShowDetailsDBRecordEventHandler>();
	
	private DBRecordInfo record;
	
	public ShowDetailsDBRecordEvent(){
		this.record = null;
	};
	public ShowDetailsDBRecordEvent(DBRecordInfo record){
		this.record = record;
	};
	
	public DBRecordInfo getDBRecord(){
		return record;
	}
	@Override public Type<ShowDetailsDBRecordEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowDetailsDBRecordEventHandler handler){
		handler.onShowDetailsDBRecord(this);
	}
	
}
