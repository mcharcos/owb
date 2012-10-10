package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.project.ProjectNeedVolunteeringPresenter;

public class ProjectNeedVolunteeringView extends Composite implements ProjectNeedVolunteeringPresenter.Display {

	private static ProjectNeedVolunteeringViewUiBinder uiBinder = GWT
			.create(ProjectNeedVolunteeringViewUiBinder.class);

	interface ProjectNeedVolunteeringViewUiBinder extends
			UiBinder<Widget, ProjectNeedVolunteeringView> {
	}

	public ProjectNeedVolunteeringView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	

}
