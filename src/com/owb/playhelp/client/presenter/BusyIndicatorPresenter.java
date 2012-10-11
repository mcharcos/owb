/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.RPCInEvent;
import com.owb.playhelp.client.event.RPCInEventHandler;
import com.owb.playhelp.client.event.RPCOutEvent;
import com.owb.playhelp.client.event.RPCOutEventHandler;

public class BusyIndicatorPresenter implements Presenter {
	public interface Display {
	    void show();
	    void hide();
	    Widget asWidget();
	  }

	int outCount = 0; 	// # of RPC calls sent by the app. If > 0 we'll show a
						// 'busy' indicator.
	private final SimpleEventBus eventBus;
	private final Display display;

	public BusyIndicatorPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
	    eventBus.addHandler(RPCInEvent.TYPE, new RPCInEventHandler() {
	      @Override
	      public void onRPCIn(RPCInEvent event) {
	        outCount = outCount > 0 ? --outCount : 0;
	        if (outCount <= 0) {
	          display.hide();
	        }
	      }
	    });
	    eventBus.addHandler(RPCOutEvent.TYPE, new RPCOutEventHandler() {
	      @Override
	      public void onRPCOut(RPCOutEvent event) {
	        outCount++;
	        display.show();
	      }
	    });
	  }

	public void go(final HasWidgets container) {
	}

}
