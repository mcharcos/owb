package com.owb.playhelp.client.presenter.web;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.Owb;
import com.owb.playhelp.client.event.WinSizeUpdateEvent;
import com.owb.playhelp.client.event.WinSizeUpdateEventHandler;
import com.owb.playhelp.client.event.user.LoginEvent;
import com.owb.playhelp.client.event.user.LoginEventHandler;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.view.web.home.WebInfoBandView;

public class WebHomePresenter  implements Presenter {
	public interface Display {
		Widget asWidget();
		HorizontalPanel getInfoBandField();
		HTMLPanel getNewsPanel1();
		HTMLPanel getNewsPanel2();
		Image getImageField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	public WebHomePresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		eventBus.addHandler(WinSizeUpdateEvent.TYPE, new WinSizeUpdateEventHandler(){
			  @Override public void onWinSizeUpdate(WinSizeUpdateEvent event){
				  updateSizes();
			  }
		  });
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
		messageVolunteer = "<font style='font-family:Century Gothic; font-size: 15px; color: #999; align: justify;'>Are you a techie? Social butterfly? Artist? We can use your help! We're seeking committed, creative, compassionate, and awesome individuals who are willing to give their time to continue building this network to help children in need. You can volunteer with computer programming, brainstorming, research, and much more - join us!</font>";
		// Add the Info band widgets on the bottom of the page
		WebInfoBandView volunteerBandView = new WebInfoBandView("Volunteer With Us","",null,"270px","30px");
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
		messageResource="<font style='font-family:Century Gothic; font-size: 15px; color: #999; align: justify;'>Companies, non-profits, agencies, and individuals who want to help: this is a call to you! You represent a resource, and this network connects resources to needs. If you agree that collaboration is key to global change, join our network now and work with others to support projects, share expertise, and provide feedback.</font>";
		WebInfoBandView resourceBandView = new WebInfoBandView("Join Our Network","",null,"270px","30px");
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
		messageProjects = "<font style='font-family:Century Gothic; font-size: 15px; color: #999; align: justify;'>This is where you ask for help. If you are in an orphanage, or you know one personally, you can create a project to help fulfill a specific need of the children there. Share it with the members of our network. We promote the creation of small tangible projects that can easily be connected to existing resources that can help meet those needs.</font>";
		WebInfoBandView needBandView = new WebInfoBandView("Submit Your Request","",null,"270px","30px");
		WebInfoBandProjectsPresenter needBandPresenter = new WebInfoBandProjectsPresenter(eventBus, needBandView);
		needBandPresenter.go(this.display.getInfoBandField());
		
		updateSizes();
		bind();
	}
	
	private void updateSizes(){
		  int width0img = display.getImageField().getWidth();
		  int height0img = display.getImageField().getHeight();
		  if (Owb.get().getWidthCenter() < width0img){
			  display.getImageField().setWidth(Owb.get().getWidthCenter()+"px");
			  display.getImageField().setHeight(height0img/width0img*Owb.get().getWidthCenter()+"px");
		  }
		  //Window.alert(Integer.toString(Owb.get().getWidthCenter())+" "+Integer.toString(width0img));
		  
		  int widthNews = (Owb.get().getWidthCenter() - 40)/2;
		  if (widthNews > 0){
			  this.display.getNewsPanel1().setWidth(widthNews+"px");
			  this.display.getNewsPanel2().setWidth(widthNews+"px");
		  }
		  //Window.alert(Integer.toString(Owb.get().getWidthCenter())+" "+Integer.toString(widthNews));
	}
	

}
