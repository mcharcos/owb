package com.owb.playhelp.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginDialog extends DialogBox {

	    public LoginDialog(String title, String msg) {
	      // Set the dialog box's caption.
	      setText(title);

	      // Enable animation.
	      setAnimationEnabled(true);

	      // Enable glass background.
	      setGlassEnabled(true);

	      // DialogBox is a SimplePanel, so you have to set its widget property to
	      // whatever you want its contents to be.
	      Button ok = new Button("Log In or Sign Up");
	      ok.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          LoginDialog.this.hide();
	          Window.Location.assign("/logingoogle");
	        }
	      });

	      Button cancel = new Button("Cancel");
	      cancel.addClickHandler(new ClickHandler() {
		        public void onClick(ClickEvent event) {
		          LoginDialog.this.hide();
		        }
		      });
	      
	      VerticalPanel dialogVPanel = new VerticalPanel();
	      dialogVPanel.addStyleName("dialogVPanel");
	      dialogVPanel.add(new HTML(msg));
	      dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
	      
	      HorizontalPanel buttonPanel = new HorizontalPanel();
	      buttonPanel.add(ok);
	      buttonPanel.add(cancel);
	      dialogVPanel.add(buttonPanel);
	      this.setWidget(dialogVPanel);
	      this.center();
	    }
}
