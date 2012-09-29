/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.web;

import com.google.gwt.event.shared.GwtEvent;

public class ShowWebEvent extends GwtEvent<ShowWebEventHandler>{
	public static Type<ShowWebEventHandler> TYPE = new Type<ShowWebEventHandler>();
	
	private String page = "mission";
	
	public ShowWebEvent(String page){
		this.page = page;
	};
	@Override public Type<ShowWebEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowWebEventHandler handler){
		handler.onShowWeb(this);
	}
	
	public String getPage(){
		return page;
	}
	
}


