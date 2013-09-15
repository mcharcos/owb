/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.orphanage;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

@RemoteServiceRelativePath("ngoService")
public interface NgoService extends RemoteService {
	DBRecordInfo updateDBRecord(DBRecordInfo record);	
	ArrayList<DBRecordInfo> getDBRecordList();
	void removeDBRecord(DBRecordInfo record);
}


