/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.volunteer;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

public interface VolunteerServiceAsync {
	void requestMemberVolunteer(VolunteerInfo volunteerInfo, AsyncCallback<VolunteerInfo> callback);
	void updateVolunteer(VolunteerInfo volunteer, AsyncCallback<VolunteerInfo> callback);	
	void confirmVolunteer(VolunteerInfo volunteerInfo, AsyncCallback<VolunteerInfo> callback);
	void reportAbuseVolunteer(VolunteerInfo volunteerInfo, String report, AsyncCallback<VolunteerInfo> callback);
	void getVolunteerList(AsyncCallback<ArrayList<VolunteerInfo>> callback);
	void removeVolunteer(VolunteerInfo volunteer, AsyncCallback<Void> callback);	
	/*
	void getUserNgoList(AsyncCallback<ArrayList<NgoItemInfo>> callback);
	void getNgo(String id, AsyncCallback<NgoInfo> callback);
	void deleteNgo(String id, AsyncCallback<String> callback) throws NoUserException;
	*/
}
