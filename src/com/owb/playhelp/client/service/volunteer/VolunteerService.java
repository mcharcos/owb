/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.volunteer;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

@RemoteServiceRelativePath("volunteerService")
public interface VolunteerService extends RemoteService {
	VolunteerInfo requestMemberVolunteer(VolunteerInfo volunteerInfo);
	VolunteerInfo updateVolunteer(VolunteerInfo volunteer);	
	VolunteerInfo confirmVolunteer(VolunteerInfo volunteerInfo);
	VolunteerInfo reportAbuseVolunteer(VolunteerInfo volunteerInfo, String report);
	ArrayList<VolunteerInfo> getVolunteerList();
	void removeVolunteer(VolunteerInfo volunteerInfo);
}


