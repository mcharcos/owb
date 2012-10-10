/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.ContributionInfo;

@RemoteServiceRelativePath("contributionService")
public interface ContributionService extends RemoteService {
			
	ContributionInfo addContribution(ContributionInfo contributionInfo);
	public ArrayList<ContributionInfo> getUserContribution();
}






