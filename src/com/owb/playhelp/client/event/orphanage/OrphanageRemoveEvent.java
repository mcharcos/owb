/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class OrphanageRemoveEvent extends GwtEvent<OrphanageRemoveEventHandler>{
	public static Type<OrphanageRemoveEventHandler> TYPE = new Type<OrphanageRemoveEventHandler>();
	
	private final DBRecordInfo Orphanage;
	
	public OrphanageRemoveEvent(DBRecordInfo Orphanage){
		this.Orphanage = Orphanage;
	};
	
	public DBRecordInfo getOrphanage(){
		return Orphanage;
	}
	
	@Override public Type<OrphanageRemoveEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(OrphanageRemoveEventHandler handler){
		handler.onOrphanageRemove(this);
	}
	
}
