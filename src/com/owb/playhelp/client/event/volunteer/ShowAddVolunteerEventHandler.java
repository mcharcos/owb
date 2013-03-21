

/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.volunteer;

import com.google.gwt.event.shared.EventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEvent;

public interface ShowAddVolunteerEventHandler extends EventHandler {
	void onShowPopupAddVolunteer(ShowAddVolunteerEvent event);
}
