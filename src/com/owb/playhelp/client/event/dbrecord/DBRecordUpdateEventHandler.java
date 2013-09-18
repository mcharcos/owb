/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.dbrecord;

import com.google.gwt.event.shared.EventHandler;

public interface DBRecordUpdateEventHandler extends EventHandler {
	void onDBRecordUpdate(DBRecordUpdateEvent event);
}
