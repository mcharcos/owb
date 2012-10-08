package com.owb.playhelp.shared.ngo;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.Timer;
import com.owb.playhelp.client.helper.MapHelper;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class is used to share information in the front end about the organization such as the name, the
 * address, the phone number, a description of their goals, their email and their website. It also contains 
 * the information of the latitude and longitude in order to avoid converting the address each time
 * we want to visualize it in the map. This conversion is time consuming and only needs to be performed once
 * just before storing the object in the data base. 
 * 
 * The class also contains information about the users that are related to the organization in some way as
 * for example, members, followers and users that reported about the organization.
 *
 */
@SuppressWarnings("serial") public class NgoInfo implements Serializable {

	private String id;
	private String name;
	private String description;
	private String address;
	private double lat;
	private double lng;
	private String phone;
	private String email;
	private String website;
	private String uniqueId;
	private boolean valid;
	private boolean confirmed;
	private boolean member;
	private boolean follower;
	private List<String> memberList;
	private List<String> memberReqList;
	private List<String> followerList;
	private List<String> abuseReportList;
	private List<String> ngoReportList;
	private List<String> adminReportList;

	private boolean isApiLoaded;
	
	public NgoInfo() {

	}
	public NgoInfo(String name) {
		this();
		this.name = name;
	}
	public NgoInfo(String name, String description) {
		this(name);
		this.description = description;
	}
	public NgoInfo(String name, String description,String addr, double lat, double lng) {
		this(name,description);
		this.address = addr;
		/*
		MapHelper map = new MapHelper();
		this.lat = map.getPoint(this).getLatitude();
		this.lng = map.getPoint(this).getLongitude();
		 */
		
		isApiLoaded = false;

		this.lat = lat;
		this.lng = lng;
		/*
		Maps.loadMapsApi(MapHelper.MapKEY, "2", false, new Runnable() {
			   public void run() { 
				   Geocoder geocoder = new Geocoder();
				   //this.lat = map.getPoint(this).getLatitude();
				   //this.lng = map.getPoint(this).getLongitude();
			        //isApiLoaded = true;
			        geocoder.getLatLng(address, new LatLngCallback() {
			  	      public void onFailure() {
			  	    	  //alert(address + " not found"); 
			  	      }

			  	      public void onSuccess(LatLng point) {
			  	    	lat = point.getLatitude();
			  	    	lng = point.getLongitude();
			  	    	isApiLoaded = true;
			  	      }
			  	    });
			   }
		    });

		try{
			Timer apiLoadedTimer = new Timer() {
				@Override
				public void run() {
					if (isApiLoaded) {
						cancel();
					}
					
				}
			};
			apiLoadedTimer.scheduleRepeating(30);
			
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	        // Window.alert("Map cannot be loaded!!");
	      } */
	}
	public NgoInfo(String name, String description,String address, double lat, double lng, String phone, String email, String website) {
		this(name,description,address,lat,lng);
		this.phone = phone;
		this.email = email;
		this.website = website;
	}


  /**
   * Gets the database id. I am not sure what this id is used for. 
   * Recommend removable from the Info object if no specific use.
   * 
   * @param 
   * @return database Id
   */
	public String getId() {
		return this.id;
	}

	  /**
	   * Gets the Name of the organization. 
	   * 
	   * @param 
	   * @return Name of the organization
	   */
	public String getName() {
		return this.name;
	}

	  /**
	   * Gets the Description of the organization. 
	   * 
	   * @param 
	   * @return Description of the organization
	   */
	public String getDescription() {
		return this.description;
	}

	  /**
	   * Gets the Address of the organization. 
	   * 
	   * @param 
	   * @return Address of the organization
	   */
	public String getAddress() {
		return this.address;
	}

	  /**
	   * Gets the Latitude of the organization. 
	   * 
	   * @param 
	   * @return Latitude of the organization
	   */
	public double getLatitude() {
		return this.lat;
	}

	  /**
	   * Gets the Longitude of the organization. 
	   * 
	   * @param 
	   * @return Longitude of the organization
	   */
	public double getLongitude() {
		return this.lng;
	}

	  /**
	   * Gets the Phone of the organization. 
	   * 
	   * @param 
	   * @return Phone of the organization
	   */
	public String getPhone() {
		return this.phone;
	}

	  /**
	   * Gets the email of the organization. 
	   * 
	   * @param 
	   * @return Email of the organization
	   */
	public String getEmail() {
		return this.email;
	}

	  /**
	   * Gets the website of the organization. 
	   * 
	   * @param 
	   * @return Website of the organization
	   */
	public String getWebsite() {
		return this.website;
	}

	  /**
	   * Gets the uniqueId of the organization. This is the id that will be used by other objects
	   * to refer to an object of this particular class.
	   * 
	   * @param 
	   * @return UniqueId of the organization
	   */
	public String getUniqueId() {
		return uniqueId;
	}

	  /**
	   * Gets the value member of the object. This value should be true if the current user is part
	   * of the organization and false otherwise. This value is set when creating the class in 
	   * the back end.
	   * 
	   * @param 
	   * @return Is the current user member?
	   */
	public boolean getMember(){
		return member;
	}

	  /**
	   * Gets the value follower of the object. This value should be true if the current user is a follower
	   * of the organization and false otherwise. This value is set when creating the class in 
	   * the back end.
	   * 
	   * @param 
	   * @return Is the current user follower?
	   */
	public boolean getFollower(){
		return follower;
	}

	  /**
	   * Gets the value cofirmed of the object. This value should be true if the organization was confirmed
	   * by an admin and false otherwise. This value is set when creating the class in 
	   * the back end.
	   * 
	   * @param 
	   * @return Is the organization confirmed?
	   */
	public boolean getConfirmed(){
		return confirmed;
	}

	  /**
	   * Gets the value valid of the object. This value should be true if the organization was confirmed
	   * by another organization and false otherwise. This value is set when creating the class in 
	   * the back end.
	   * 
	   * @param 
	   * @return Is the organization valid?
	   */
	public boolean getValid(){
		return valid;
	}
	
	  /**
	   * Gets the list of members of the organization. 
	   * 
	   * @param 
	   * @return List of members of the organization
	   */
	public List<String> getMemberList(){
		return memberList;
	}

	  /**
	   * Gets the list of members that requested being part of the organization. 
	   * 
	   * @param 
	   * @return List of member requests
	   */
	public List<String> getMemberReqList(){
		return memberReqList;
	}

	  /**
	   * Gets the list of followers of the organization. 
	   * 
	   * @param 
	   * @return List of followers of the organization
	   */
	public List<String> getFollowerList(){
		return followerList;
	}
	

	  /**
	   * Gets the list of reports of some kind of abuse that users has seen of this organization. 
	   * 
	   * @param 
	   * @return List of members of the organization
	   */
	public List<String> getAbuseReportList(){
		return abuseReportList;
	}

	  /**
	   * Gets the list of good or bad reports that users has seen of this organization in behalf of
	   * another organization. 
	   * 
	   * @param 
	   * @return List of members of the organization
	   */
	public List<String> getNgoReportList(){
		return ngoReportList;
	}

	  /**
	   * Gets the list of good or bad reports that administrators users has seen of this organization.
	   * Administrator users are part of Orphanage Without Borders
	   * 
	   * @param 
	   * @return List of members of the organization
	   */
	public List<String> getAdminReportList(){
		return adminReportList;
	}
	

	  /**
	   * Sets the Id of the object. Recommend deletion if id is not used for this class.
	   * 
	   * @param Id of the object
	   * @return 
	   */
	public void setId(String id) {
		this.id = id;
	}

	  /**
	   * Sets the Name of the organization. 
	   * 
	   * @param Name of organization
	   * @return 
	   */
	public void setName(String name) {
		this.name = name;
	}

	  /**
	   * Sets the Description of the organization. 
	   * 
	   * @param Description of organization
	   * @return 
	   */
	public void setDescription(String description) {
		this.description = description;
	}

	  /**
	   * Sets the Address of the organization. 
	   * 
	   * @param Address of organization
	   * @return 
	   */
	public void setAddress(String address,double lat, double lng) {
		this.address = address;
		this.lat = lat;
		this.lng = lng;
	}

	  /**
	   * Sets the Geographical coordinates of the organization. Latitude and Longitude are entered following the
	   * standards used by Google Map (degrees?).
	   * 
	   * @param Latitude and Longitude of the organization.
	   * @return 
	   */
	public void setPoint(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	} 

	  /**
	   * Sets the phone of the organization. 
	   * 
	   * @param Phone of organization
	   * @return 
	   */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	  /**
	   * Sets the email of the organization. 
	   * 
	   * @param Email of organization
	   * @return 
	   */
	public void setEmail(String email) {
		this.email = email;
	}

	  /**
	   * Sets the website of the organization. 
	   * 
	   * @param Website of organization
	   * @return 
	   */
	public void setWebsite(String website) {
		this.website = website;
	}

	  /**
	   * Sets the unique Id of the organization. This id is the one used by other objects to refer to 
	   * this organization.
	   * 
	   * @param Name of organization
	   * @return 
	   */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	

	  /**
	   * Sets the confirm value of the object. This value indicates if the organization was confirmed 
	   * by an administrator
	   * 
	   * @param Is the organization confirmed?
	   * @return 
	   */
	public void setConfirm(boolean confirmed){
		this.confirmed = confirmed;
	}

	  /**
	   * Sets the valid value of the object. This value indicates if the organization was confirmed 
	   * by a user on behalf another organizations
	   * 
	   * @param Is the organization validated?
	   * @return 
	   */
	public void setValid(boolean valid){
		this.valid = valid;
	}

	  /**
	   * Sets the list of members of the organization. 
	   * 
	   * @param Members of organization
	   * @return 
	   */
	public void setMemberList(List<String> memberList){
		this.memberList = memberList;
	}

	  /**
	   * Sets the list of requested memberships of the organization. 
	   * 
	   * @param List of requests of organization
	   * @return 
	   */
	public void setMemberReqList(List<String> memberReqList){
		this.memberReqList = memberReqList;
	}

	  /**
	   * Sets the list of followers of the organization. 
	   * 
	   * @param Followers of organization
	   * @return 
	   */
	public void setFollowerList(List<String> followerList){
		this.followerList = followerList;
	}

	  /**
	   * Sets the list of reports of abuses that users have seen about the organization. 
	   * 
	   * @param Reports of abuses of organization
	   * @return 
	   */
	public void setAbuseReportList(List<String> abuseReportList){
		this.abuseReportList = abuseReportList;
	}

	  /**
	   * Sets the list of reports that users do about the organization on behalf of another organization. 
	   * 
	   * @param Report list of organization
	   * @return 
	   */
	public void setNgoReportList(List<String> ngoReportList){
		this.ngoReportList = ngoReportList;
	}

	  /**
	   * Sets the list of reports of the organization from administrator users. 
	   * 
	   * @param Members of organization
	   * @return 
	   */
	public void setAdminReportList(List<String> adminReportList){
		this.adminReportList = adminReportList;
	}
	

	  /**
	   * Set the member value to true. It is used when the current user is a member of the organization.
	   * 
	   * @param 
	   * @return 
	   */
	public void activateMember(){
		this.member = true;
	}

	  /**
	   * Set the member value to false. It is used when the current user is not a member of the organization.
	   * 
	   * @param 
	   * @return 
	   */
	public void deactivateMember(){
		this.member = false;
	}

	  /**
	   * Set the follower value to true. It is used when the current user is a follower of the organization.
	   * 
	   * @param 
	   * @return 
	   */
	public void activateFollower(){
		this.follower = true;
	}
	  /**
	   * Set the follower value to false. It is used when the current user is not a follower of the organization.
	   * 
	   * @param 
	   * @return 
	   */
	public void deactivateFollower(){
		this.follower = false;
	}
	
	
}

