/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.standard;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;

public class ShowAddStandardEvent extends GwtEvent<ShowAddStandardEventHandler>{
	public static Type<ShowAddStandardEventHandler> TYPE = new Type<ShowAddStandardEventHandler>();
	
	private DBRecordInfo record;
	
	public ShowAddStandardEvent(){
		this.record = null;
	};
	public ShowAddStandardEvent(DBRecordInfo record){
		this.record = record;
	};
	
	public DBRecordInfo getDBRecord(){
		return record;
	}
	@Override public Type<ShowAddStandardEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowAddStandardEventHandler handler){
		handler.onShowAddStandard(this);
	}
	
}
