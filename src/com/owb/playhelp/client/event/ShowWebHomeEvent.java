/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowWebHomeEvent extends GwtEvent<ShowWebHomeEventHandler>{
	public static Type<ShowWebHomeEventHandler> TYPE = new Type<ShowWebHomeEventHandler>();
	public ShowWebHomeEvent(){};
	@Override public Type<ShowWebHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowWebHomeEventHandler handler){
		handler.onShowWebHomeRequest(this);
	}
	
}

