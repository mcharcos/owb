/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.volunteer;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.UUID;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.ConfirmationBadge;
import com.owb.playhelp.server.utils.EmailHelper;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.volunteer.VolunteerInfo;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class represents the Non-profits organizations. It contains all the information about the organization
 * that was entered by the user and additional information for keeping track of the validity of the Volunteer.
 * The additional information is stored in a ConfirmationBadge object that is referred here via the uniqueId identifier.
 * Information about users that are related to the organization are also stored here as lists of uniqueId identifiers.
 * The relation to the organization can be in three forms: members, followers and users that want to become member and
 * sent a request for that.  
 * 
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Volunteer implements Serializable, Cacheable {

	  private static final Logger log = Logger.getLogger(Utils.class.getName());
	  private static final int NUM_RETRIES = 5; 
	  
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String description;
	
	@Persistent
	private String address;

	@Persistent
	private double lat, lng;
	
	@Persistent
	private String phone;

	@Persistent
	private String email;

	@Persistent
	private String website;

	/*
	@Persistent(dependent = "true")
	private Standard status;
	*/
	
	@Persistent
	private String uniqueId;
	
	/*
	@Persistent
	private String confirmationBadge;

	@Persistent
	private Set<String> memberRequests = new HashSet<String>();

	@Persistent
	private Set<String> members = new HashSet<String>();

	@Persistent
	private Set<String> followers = new HashSet<String>();
	*/

	//@Persistent(mappedBy = "userPrefs")
	//@Element(dependent = "true")
	//private Set<FriendItem> friends = new HashSet<FriendItem>();

	public Volunteer() {
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();  //this.getEmail();
		} 
		
		//ConfirmationBadge confB = ConfirmationBadge.findOrCreateVolunteer(new ConfirmationBadge(this));
        
		// Don't need to update the uniqueId of the confirmationBadge because
		// the class ConfirmationBadge takes care of this
		//this.confirmationBadge = confB.getUniqueId();
	}

	public Volunteer(VolunteerInfo volunteerInfo) {
		this();
		this.setName(volunteerInfo.getName());
		this.setDescription(volunteerInfo.getDescription());
		this.setAddress(volunteerInfo.getAddress());
		this.setLatitude(volunteerInfo.getLatitude());
		this.setLongitude(volunteerInfo.getLongitude());
		this.setPhone(volunteerInfo.getPhone());
		this.setEmail(volunteerInfo.getEmail());
		this.setWebsite(volunteerInfo.getWebsite());
		//if (volunteerInfo.getUniqueId() != null) this.setUniqueId(volunteerInfo.getUniqueId());
		
	}

	  /**
	   * Given a VolunteerInfo object it updates the information of the Volunteer object 
	   * from the VolunteerInfo object.
	   * 
	   * @param VolunteerInfo object 
	   * @return 
	   */
	public void reEdit(VolunteerInfo volunteerInfo) {
		this.setName(volunteerInfo.getName());
		this.setDescription(volunteerInfo.getDescription());
		this.setName(volunteerInfo.getName());
		this.setAddress(volunteerInfo.getAddress());
		this.setLatitude(volunteerInfo.getLatitude());
		this.setLongitude(volunteerInfo.getLongitude());
		this.setPhone(volunteerInfo.getPhone());
		this.setEmail(volunteerInfo.getEmail());
		this.setWebsite(volunteerInfo.getWebsite());	
	}

	  /**
	   * It creates a VolunteerInfo object based on the information of the Volunteer object. It considers that
	   * the user that requests this is not related to the organization. This is important
	   * to activate Member and Follower. 
	   * 
	   * @param 
	   * @return  VolunteerInfo object
	   */
	public static VolunteerInfo toInfo(Volunteer o) {
		if (o == null)
			return null;

		VolunteerInfo oInfo = new VolunteerInfo(o.getName(), o.getDescription(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
		oInfo.setUniqueId(o.getUniqueId());
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		oInfo.deactivateMember();
		oInfo.deactivateFollower();
		oInfo.setValid(o.isValid());
		oInfo.setConfirm(false);
		
		
		return oInfo;
	}

	  /**
	   * It creates a VolunteerInfo object based on the information of the Volunteer object. It considers the
	   * input user to activate Member and Follower. 
	   * 
	   * @param 
	   * @return  VolunteerInfo object
	   */
	public static VolunteerInfo toInfo(Volunteer o, String userUniqueId) {
		if (o == null)
			return null;

		VolunteerInfo oInfo = Volunteer.toInfo(o);
		
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		if (o.isMember(userUniqueId)) oInfo.activateMember();
		if (o.isFollower(userUniqueId)) oInfo.activateFollower();

		//oInfo.setValid(o.isValid());
		oInfo.setConfirm(o.isConfirmed(userUniqueId,o));
		
		return oInfo;
	}

	  /**
	   * Retrieve the user from the database if it exists an object with the same uniqueId as the Volunteer or create a new object
	   * otherwise with the information of the input Volunteer.
	   *  
	   * @param  Requested Volunteer
	   * @return  Volunteer object that was persisted
	   */
	  public static Volunteer findOrCreateVolunteer(Volunteer volunteer) {
	
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Volunteer oneResult = null, detached = null;
	
	    String uniqueId = volunteer.getUniqueId();
	
	    Query q = pm.newQuery(Volunteer.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    //boolean persistConfirmationBadge = false;
	    //ConfirmationBadge confB = null;
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        if (uniqueId != null) oneResult = (Volunteer) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("Volunteer " + uniqueId + " does not exist, creating...");
	          /*
	          confB = new ConfirmationBadge(volunteer);
	          persistConfirmationBadge = true;
	          */
	          pm.makePersistent(volunteer);
	          log.info("Sending email...");
	          EmailHelper.sendMail(volunteer);
	          detached = pm.detachCopy(volunteer);
	        }
	        try {
	          tx.commit();
	          break;
	        }
	        catch (JDOCanRetryException e1) {
	          if (i == (NUM_RETRIES - 1)) { 
	            throw e1;
	          }
	        }
	      } // end for
	    } catch (JDOUserException e){
	          log.info("JDOUserException: UserProfile table is empty");
	          // Create friends from Google+
	          pm.makePersistent(volunteer);
	          detached = pm.detachCopy(volunteer);	    	
		        try {
			          tx.commit();
			        }
			        catch (JDOCanRetryException e1) {
			        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    finally {
	      if (tx.isActive()) {
	        tx.rollback();
	      }
	      pm.close();
	      q.closeAll();
	    }
	    
	    /*
	    if (persistConfirmationBadge) {
	    	//ConfirmationBadge newConfB = ConfirmationBadge.findOrCreateVolunteer(confB);
	    	ConfirmationBadge.findOrCreateVolunteer(confB);
	    }*/
	    return detached;
	  }
	


	  /**
	   * Check if the input user is a member of the Volunteer
	   *  
	   * @param  User profile uniqueId
	   * @return  true if member or false if not.
	   */
	public boolean isMember(String userUniqueId){
		return true; //members.contains(userUniqueId);
	}

	  /**
	   * Check if the input user is a follower of the Volunteer
	   *  
	   * @param  User profile uniqueId
	   * @return  true if follower or false if not.
	   */
	public boolean isFollower(String userUniqueId){
		return true; //followers.contains(userUniqueId);
	}

	  /**
	   * Check if the organization is confirmed by the input user representing a specific Volunteer
	   *  
	   * @param  User profile and Volunteer that represents
	   * @return  true if confirmed or false if not.
	   */
	public boolean isConfirmed(String userId, Volunteer volunteer){
		return true;  //this.getConfirmationBadge().isConfirmed(userId, volunteer);
	}

	  /**
	   * Check if the organization is valid, meaning that it has been confirmed by an admin
	   *  
	   * @param  
	   * @return  true if validated or false if not.
	   */
	public boolean isValid(){
		return true;  //this.getConfirmationBadge().isValid();
	}

	  /**
	   * Add the object to the Cache
	   *  
	   * @param  
	   * @return  
	   */
	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	  /**
	   * Remove the object from the Cache
	   *  
	   * @param  
	   * @return  
	   */
	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	  /**
	   * Get the Id of the Volunteer stored in this object. This id correspond to the index used by the database
	   *  
	   * @param  
	   * @return  Id of Volunteer (different from uniqueId)
	   */
	public Long getId() {
		return this.id;
	}

	  /**
	   * Get the uniqueId of the Volunteer stored in this object. This id is a unique identifier of the object
	   * and will be used in other classes to refer to a specific Volunteer.
	   *  
	   * @param  
	   * @return  Unique Id of Volunteer 
	   */
	public String getUniqueId() {
		return uniqueId;
	}
	
	  /**
	   * Get the Name of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Name of Volunteer
	   */
	public String getName() {
		return this.name;
	}

	  /**
	   * Get the Description of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Description of Volunteer
	   */
	public String getDescription() {
		return this.description;
	}

	  /**
	   * Get the Address of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Address of Volunteer
	   */
	public String getAddress() {
		return this.address;
	}

	  /**
	   * Get the Latitude of the location of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Latitude of Volunteer
	   */
	public double getLatitude() {
		return this.lat;
	}
	
	  /**
	   * Get the Longitude of the location of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Longitude of Volunteer
	   */
	public double getLongitude() {
		return this.lng;
	}

	  /**
	   * Get the Phone of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Phone of Volunteer
	   */
	public String getPhone() {
		return this.phone;
	}

	  /**
	   * Get the email of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Email of Volunteer
	   */
	public String getEmail() {
		return this.email;
	}

	  /**
	   * Get the website of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Website of Volunteer
	   */
	public String getWebsite() {
		return this.website;
	}


	  /**
	   * Get the id of the confirmation badge of the Volunteer stored in this object
	   *  
	   * @param  
	   * @return  Id of confirmation badge of Volunteer
	   */
	/*
	public String getConfirmationBadgeId(){
		return confirmationBadge;
	}*/

	  /**
	   * Get the list of members that are in the list request.
	   *  
	   * @param  
	   * @return  List of unique Ids of member request list
	   */
	/*
	public Set<String> getMemberRequests(){
		return memberRequests;
	}*/

	  /**
	   * Get the list of members that are in the list request.
	   *  
	   * @param  
	   * @return  List of unique Ids of member request list
	   */
	/*
	public Set<String> getMembers(){
		return members;
	}*/
	
	  /**
	   * Get the list of followers.
	   *  
	   * @param  
	   * @return  List of unique Ids of followers
	   */
	/*
	public Set<String> getFollowers(){
		return followers;
	}*/
	
	  /**
	   * Set the Name of the Volunteer.
	   *  
	   * @param  Name of Volunteer
	   * @return  
	   */
	public void setName(String name) {
		this.name = name;
	}

	  /**
	   * Set the description of the Volunteer.
	   *  
	   * @param  Description of Volunteer
	   * @return  
	   */
	public void setDescription(String description) {
		this.description = description;
	}

	  /**
	   * Set the address of the Volunteer.
	   *  
	   * @param  Address of Volunteer
	   * @return  
	   */
	public void setAddress(String address) {
		this.address = address;
	}

	  /**
	   * Set the latitude of the Volunteer.
	   *  
	   * @param  Latitude of Volunteer
	   * @return  
	   */
	public void setLatitude(double lat) {
		this.lat = lat;
	}

	  /**
	   * Set the longitude of the Volunteer.
	   *  
	   * @param  Longitude of Volunteer
	   * @return  
	   */
	public void setLongitude(double lng) {
		this.lng = lng;
	}

	  /**
	   * Set the phone of the Volunteer.
	   *  
	   * @param  Phone of Volunteer
	   * @return  
	   */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	  /**
	   * Set the email of the Volunteer.
	   *  
	   * @param  Email of Volunteer
	   * @return  
	   */
	public void setEmail(String email) {
		this.email = email;
	}

	  /**
	   * Set the website of the Volunteer.
	   *  
	   * @param  Website of Volunteer
	   * @return  
	   */
	public void setWebsite(String website) {
		this.website = website;
	}

	  /**
	   * Set the id of the Volunteer. This id is the index in the database. Do we want to be able to set it?
	   *  
	   * @param  Id of Volunteer
	   * @return  
	   */
	public void setId(Long id) {
		this.id = id;
	}

	  /**
	   * Set the confirmation badge id of the Volunteer. Do we really want to be able to modify the id of the confirmation badge?
	   *  
	   * @param  UniqueId of Volunteer confirmation badge
	   * @return  
	   */
	/*
	public void setConfirmationBadge(String uniqueId){
		this.confirmationBadge = uniqueId;
	}*/

	  /**
	   * Set unique id of the Volunteer. 
	   *  
	   * @param  UniqueId of Volunteer 
	   * @return  
	   */
	/*
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}*/

	  /**
	   * Used when a user want to be part of the members of the Volunteer. The user will be added to the 
	   * member request list.
	   *  
	   * @param  UniqueId of the user requesting membership 
	   * @return  
	   */
	/*
	public void requestMember(String member){
		// Check if the member exist
		if (memberRequests.contains(member)) return;
		
		// Add it if it does not
		memberRequests.add(member);
	}*/

	  /**
	   * Used to add a member to the Volunteer. The user will be added to the member list of the object.
	   * We should probably check if the logged in user is a member that has permissions to do so.
	   * Also, we should remove the added member from the request member list if existing.
	   *  
	   * @param  UniqueId of the user added as a member.
	   * @return  
	   */
	/*
	public void addMember(String member){
		// Check if the member exist
		if (members.contains(member)) return;
		
		// Add it if it does not
		members.add(member);
	}*/

	  /**
	   * Used to add as follower of the Volunteer. 
	   *  
	   * @param  UniqueId of the user added as a follower.
	   * @return  
	   */
	/*
	public void addFollowers(String follower){
		// Check if the member exist
		if (followers.contains(follower)) return;
		
		// Add it if it does not
		followers.add(follower);
	}*/
}