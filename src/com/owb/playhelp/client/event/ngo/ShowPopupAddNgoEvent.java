/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class ShowPopupAddNgoEvent extends GwtEvent<ShowPopupAddNgoEventHandler>{
	public static Type<ShowPopupAddNgoEventHandler> TYPE = new Type<ShowPopupAddNgoEventHandler>();
	
	private final ClickPoint point;
	private DBRecordInfo ngo;
	
	public ShowPopupAddNgoEvent(ClickPoint location){
		this.point = location;
		this.ngo = null;
	};
	public ShowPopupAddNgoEvent(ClickPoint location, DBRecordInfo ngo){
		this.point = location;
		this.ngo = ngo;
	};
	
	public ClickPoint getClickPoint(){
		return point;
	}
	
	public DBRecordInfo getNgo(){
		return ngo;
	}
	@Override public Type<ShowPopupAddNgoEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupAddNgoEventHandler handler){
		handler.onShowPopupAddNgo(this);
	}
	
}
