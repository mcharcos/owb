/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.StandardInfo;

@RemoteServiceRelativePath("standardService")
public interface StandardService extends RemoteService {
	StandardInfo updateStandard(StandardInfo standard);	
	ArrayList<StandardInfo> getNgoList();
	void removeStandard(StandardInfo standard);
}


