package com.owb.playhelp.client.presenter.web;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.view.web.home.WebInfoBandView;

public class WebHomePresenter  implements Presenter {
	public interface Display {
		Widget asWidget();
		HorizontalPanel getInfoBandField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	public WebHomePresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		
	}


	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		
		String messageVolunteer = "<b>DO YOU LOVE HELPING CHILDREN? </b><br></br> " +
				"Do you love being involved in ground breaking non-profit organizations that help children? " +
				"We are developing an organization that help orphanages around the world. "+
				"<b>We are looking for self-motivating, compassionate individuals that can volunteer their time to assist us " +
				"in building our organization to help children in orphanages worldwide. </b> Our organization started two years " + 
				"ago and looking for help from YOU in making our organization stronger and grow on a national and international level. " + 
				"An hour of your time can change a life of a child! Volunteer TODAY!!";
		messageVolunteer = "<font color='#ffffff'>Whether you're a techie, a social butterfly, or an artist, we can use your help! We are seeking committed, compassionate, creative, and awesome individuals who are willing to give their time to continue building this network to help children in need. There are plenty of volunteering opportunities available for you - from research, to computer programming, to brainstorming new ideas, and many more - join us!</font>";
		// Add the Info band widgets on the bottom of the page
		WebInfoBandView volunteerBandView = new WebInfoBandView("Volunteer With Us",messageVolunteer,null,"270px","30px");
		WebInfoBandVolunteerPresenter volunteerBandPresenter = new WebInfoBandVolunteerPresenter(eventBus, volunteerBandView);
		volunteerBandPresenter.go(this.display.getInfoBandField());
		
		String messageResource = "Organizations that work in specific areas have the experience to support projects with their expertise. <b>Creating a network "+
				"that can provide knowledge and resources</b> is critical to achieve our goal. We believe that this network will help not only "+
				"the projects that directly help children but also organizations in different areas to learn from different experiences " +
				"and a framework to find useful information. In addition, the organizations in our network can take advantage of "+
				"<b>feedback from our community of individuals</b> to find resources and support for their own ongoing projects. We also " +
				"support their projects if they are within our baseline in the areas they work with. "+
				"If you as an organization also thinks that collaboration is the key for a global change, join our Network "+
				"and collaborate with other organizations to the cause";
		messageResource="<font color='#ffffff'>Companies, non-profits, agencies, and individuals who want to help: this is a call to you. You represent a resource, and this network connects resources to needs. If you agree that collaboration is the key to global change, join our network and start collaborating with other organizations by supporting projects, sharing your expertise, and providing feedback to other organizations.</font>";
		WebInfoBandView resourceBandView = new WebInfoBandView("Join Our Network",messageResource,null,"270px","30px");
		WebInfoBandResourcesPresenter resourceBandPresenter = new WebInfoBandResourcesPresenter(eventBus, resourceBandView);
		resourceBandPresenter.go(this.display.getInfoBandField());
  
		String messageProjects = "Projects that were started by individuals, small groups and large organizations in order to helping children "+
				"are the main bone of our cause. Efforts devoted to help in specific areas are at the forefront of our work. "+
				"<b>We represent projects to finding resources, connecting to other organizations or "+
				"find other people in different parts of the world that can support them with ideas, resources or raising awareness.</b> "+
				"We promote creation of small projects helping children and find resources for them or stimulate help in various countries. "+
		        "Through our network of organizations or within our community we find advice on how to proceed with projects, find resources "+
		        "or be more efficient. If you think you have a project helping children in need, you can share it with organizations "+
		        "and individuals of our network by applying as an orphanage. ";
		messageProjects = "<font color='#ffffff'>This is where you ask for help. If you are in an orphanage and want to fulfill a need of the children, you can share it with the organizations and individuals in our network by creating a project. You can also create a project to help the children in an orphanage you know. We promote the creation of small tangible projects that can easily be connected to existing resources that can help meet those needs.</font>";
		WebInfoBandView needBandView = new WebInfoBandView("Share Your Needs",messageProjects,null,"270px","30px");
		WebInfoBandProjectsPresenter needBandPresenter = new WebInfoBandProjectsPresenter(eventBus, needBandView);
		needBandPresenter.go(this.display.getInfoBandField());
		
		bind();
	}
	

}
