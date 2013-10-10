package com.owb.playhelp.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.core.client.GWT;
//import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {
	// I added this line below
    public static final Resources INSTANCE =  GWT.create(Resources.class);

    /*
    @Source("mysettings.css")
    @CssResource.NotStrict
    CssResource css();*/
    
    // Menu bar
    @ImageOptions(preventInlining=true)
    @Source("webmenu/Phome.jpg")
    ImageResource menuHomeIcon();
    @ImageOptions(preventInlining=true)
    @Source("webmenu/PaboutB.jpg")
    ImageResource menuAboutOnIcon();
    @ImageOptions(preventInlining=true)
    @Source("webmenu/Pabout.jpg")
    ImageResource menuAboutIcon();
		@ImageOptions(preventInlining=true)
		@Source("webmenu/mission.jpg")
		ImageResource menuMissionIcon();
		@ImageOptions(preventInlining=true)
		@Source("webmenu/what's different?.jpg")
		ImageResource menuOurViewIcon();
		@ImageOptions(preventInlining=true)
		@Source("webmenu/the team.jpg")
		ImageResource menuTheTeamIcon();
		@ImageOptions(preventInlining=true)
		@Source("webmenu/volunteering.jpg")
		ImageResource menuJoinOWBIcon();
    @ImageOptions(preventInlining=true)
    @Source("webmenu/Presources.jpg")
    ImageResource menuResourcesIcon();
	    @ImageOptions(preventInlining=true)
	    @Source("webmenu/join the network.jpg")
	    ImageResource menuJoinNetworkIcon();
	    @ImageOptions(preventInlining=true)
	    @Source("webmenu/search resources.jpg")
	    ImageResource menuSearchResourcesIcon();
    @ImageOptions(preventInlining=true)
    @Source("webmenu/Pneeds.jpg")
    ImageResource menuNeedsIcon();
	    @ImageOptions(preventInlining=true)
	    @Source("webmenu/share a project.jpg")
	    ImageResource menuShareProjectsIcon();
	    @ImageOptions(preventInlining=true)
	    @Source("webmenu/search projects.jpg")
	    ImageResource menuSearchProjectsIcon();
	    @ImageOptions(preventInlining=true)
	    @Source("webmenu/choose a project.jpg")
	    ImageResource menuChooseProjectsIcon();
    @ImageOptions(preventInlining=true)
    @Source("webmenu/Pmap.jpg")
    ImageResource menuMapIcon();
    
    @Source("ProfilePanel.gif")
    ImageResource profilePanel();
    @Source("SocialIcons.gif")
    ImageResource socialIcons();
    @Source("edit_profile.png")
    ImageResource sampleProfilePicture();
    @Source("loggedout.gif")
    ImageResource loggedoutPicture();
    @Source("AvatarPanel.gif")
    ImageResource avatarPanel();

    @Source("NewsHomePanel.jpg")
    ImageResource NewsHomePanel();
    @Source("FriendsHomePanel.jpg")
    ImageResource FriendsHomePanel();
    @Source("RadioHomePanel.jpg")
    ImageResource RadioHomePanel();
    
    @Source("owb_logo_verysmall.gif")
    ImageResource owbLogo();
    @Source("Name2Logo.gif")
    ImageResource name2Logo();
    
    @Source("emailTop.gif")
    ImageResource emailLogo();
    @Source("newsTop.gif")
    ImageResource newsLogo();
    @Source("worldTop.gif")
    ImageResource worldLogo();
    @Source("friendTop.gif")
    ImageResource friendLogo();
    @Source("blogTop.gif")
    ImageResource blogLogo();
    @Source("radioTop.gif")
    ImageResource radioLogo();

    @Source("emailSelTop.gif")
    ImageResource emailSelLogo();
    @Source("newsSelTop.gif")
    ImageResource newsSelLogo();
    @Source("worldSelTop.gif")
    ImageResource worldSelLogo();
    @Source("friendSelTop.gif")
    ImageResource friendSelLogo();
    @Source("blogSelTop.gif")
    ImageResource blogSelLogo();
    @Source("radioSelTop.gif")
    ImageResource radioSelLogo();
    
    // The new design September 27th 2012
    @Source("TopePG.jpg")
    ImageResource name3Logo();
    @Source("fillerLarge.jpg")
    ImageResource bar3Logo();
    @Source("NewsIconPanel.jpg")
    ImageResource newsLogo2();
    @Source("MapIconPanel.jpg")
    ImageResource worldLogo2();
    @Source("FriendsIconPanel.jpg")
    ImageResource friendLogo2();
    @Source("login.jpg")
    ImageResource loginIcon();
    @Source("logout.jpg")
    ImageResource logoutIcon();
    @Source("settings.jpg")
    ImageResource settingIcon();
    @Source("settingsblank.jpg")
    ImageResource settingBlankIcon();
    
    @Source("NewsIconPanelSel.jpg")
    ImageResource newsSelLogo2();
    @Source("MapIconPanelSel.jpg")
    ImageResource worldSelLogo2();
    @Source("FriendsIconPanelSel.jpg")
    ImageResource friendSelLogo2();

    @Source("TopLeftPanel.jpg")
    ImageResource nameLeftLogo();
    
    
	@Source("propertyButton.png")
	ImageResource propertyButton();

	// Progress bar
	@Source("progressbar1.gif")
	ImageResource progressBar();
	
    // Map images
    @Source("NgoMapIcon2.gif")
    ImageResource ngoMapIcon();
    @Source("NgoMapIcon.gif")
    ImageResource orphanageMapIcon();
    
    // Webpage resources "what do we do"
    @Source("webpics/clothing.jpg")
    ImageResource clothingWebPic();
    @Source("webpics/compassionate.jpg")
    ImageResource compassionateWebPic();
    @Source("webpics/discipline.jpg")
    ImageResource disciplineWebPic();
    @Source("webpics/education.jpg")
    ImageResource educationWebPic();
    @Source("webpics/food.jpg")
    ImageResource foodWebPic();
    @Source("webpics/water.jpg")
    ImageResource waterWebPic();
    @Source("webpics/guidance.jpg")
    ImageResource guidanceWebPic();
    @Source("webpics/health.jpg")
    ImageResource healthWebPic();
    @Source("webpics/excercise.jpg")
    ImageResource excerciseWebPic();
    @Source("webpics/hopeoffuture.jpg")
    ImageResource hopeoffutureWebPic();
    @Source("webpics/hygiene.jpg")
    ImageResource hygieneWebPic();
    @Source("webpics/joy.jpg")
    ImageResource joyWebPic();
    @Source("webpics/love.jpg")
    ImageResource loveWebPic();
    @Source("webpics/responsabilities.jpg")
    ImageResource responsabilitiesWebPic();
    @Source("webpics/safety.jpg")
    ImageResource safetyWebPic();
    @Source("webpics/shelter.jpg")
    ImageResource shelterWebPic();
    @Source("webpics/whatdowedo.jpg")
    ImageResource whatdowedoWebPic();
    @Source("Migue.jpg")
    ImageResource Miguelpic();
    @Source("Kathy.jpg")
    ImageResource Kathypic();
    @Source("Erikaphoto.jpg")
    ImageResource Erikapic();
    @Source("Kariphoto.jpg")
    ImageResource Karipic();
    @Source("nopicphoto.jpg")
    ImageResource nopicpic();
    
    // Webpage resources "about us"
    @Source("webpics/aboutus.jpg")
    ImageResource aboutusWebPic();
    @Source("webpics/context.jpg")
    ImageResource contextWebPic();
    @Source("webpics/mission.jpg")
    ImageResource missionWebPic();
    @Source("webpics/view.jpg")
    ImageResource viewWebPic();
    @Source("webpics/volunteer.jpg")
    ImageResource volunteerWebPic();
    
    // Picture slider
    @Source("go-next.gif")
    ImageResource getNext();
    @Source("go-previous.gif")
    ImageResource getBack();
    @Source("slide1.jpg")
    ImageResource homeimg1();
    @Source("slide2.jpg")
    ImageResource homeimg2();
    @Source("slide3.jpg")
    ImageResource homeimg3();
    @Source("slide4b.jpg")
    ImageResource homeimg4();
    @Source("slideshow.gif")
    ImageResource slideshowgif();
    
    
    
    
}
