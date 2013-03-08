package com.owb.playhelp.client.view.web.whatdowedo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WebWhatDoWeDoMainView extends Composite implements HasText {

	private static WebWhatDoWeDoMainViewUiBinder uiBinder = GWT
			.create(WebWhatDoWeDoMainViewUiBinder.class);

	interface WebWhatDoWeDoMainViewUiBinder extends
			UiBinder<Widget, WebWhatDoWeDoMainView> {
	}

	@UiField VerticalPanel areaField, indexField;
	
	public WebWhatDoWeDoMainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}
	
	public VerticalPanel getAreaField(){
		return areaField;
	}
	
	public VerticalPanel getIndexField(){
		return indexField;
	}

}
