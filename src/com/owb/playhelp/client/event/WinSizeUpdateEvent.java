/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class WinSizeUpdateEvent extends GwtEvent<WinSizeUpdateEventHandler>{
	public static Type<WinSizeUpdateEventHandler> TYPE = new Type<WinSizeUpdateEventHandler>();
	
	private final int widthLeft, widthCenter, widthRight;
	
	public WinSizeUpdateEvent(int widthLeft, int widthCenter, int widthRight){
		this.widthLeft = widthLeft;
		this.widthCenter = widthCenter;
		this.widthRight = widthRight;
	}

	public int getWidthLeft(){
		return widthLeft;
	}
	public int getWidthCenter(){
		return widthCenter;
	}
	public int getWidthRight(){
		return widthRight;
	}
	
	@Override public Type<WinSizeUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(WinSizeUpdateEventHandler handler){
		handler.onWinSizeUpdate(this);
	}
	
}

