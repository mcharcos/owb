/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.orphanage;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public interface NgoServiceAsync {
	void updateDBRecord(DBRecordInfo ngo, AsyncCallback<DBRecordInfo> callback);	
	void getDBRecordList(AsyncCallback<ArrayList<DBRecordInfo>> callback);
	void removeDBRecord(DBRecordInfo Orphanage, AsyncCallback<Void> callback);	
}