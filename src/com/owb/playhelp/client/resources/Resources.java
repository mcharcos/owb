package com.owb.playhelp.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.core.client.GWT;

public interface Resources extends ClientBundle {
	// I added this line below
    public static final Resources INSTANCE =  GWT.create(Resources.class);

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
    
    // The new desigh September 27th 2012
    @Source("TopPanelMain.jpg")
    ImageResource name3Logo();
    @Source("NewsIconPanel.jpg")
    ImageResource newsLogo2();
    @Source("MapIconPanel.jpg")
    ImageResource worldLogo2();
    @Source("FriendsIconPanel.jpg")
    ImageResource friendLogo2();
    
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
    
    // Webpage resources
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
    @Source("webpics/guidance.jpg")
    ImageResource guidanceWebPic();
    @Source("webpics/health.jpg")
    ImageResource healthWebPic();
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
    
}
