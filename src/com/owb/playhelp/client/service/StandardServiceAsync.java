/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.StandardInfo;

public interface StandardServiceAsync {
	void updateStandard(StandardInfo stadnard, AsyncCallback<StandardInfo> callback);	
	void getNgoList(AsyncCallback<ArrayList<StandardInfo>> callback);
	void removeStandard(StandardInfo standard, AsyncCallback<Void> callback);	
}
