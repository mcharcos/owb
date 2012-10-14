/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * 
 * @author Miguel Charcos Llorens
 * Generic presenter interface to Presnter class.
 *
 */
public abstract interface Presenter {
	public abstract void go(final HasWidgets container);
}
