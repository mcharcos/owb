/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.ngo;


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
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.ngo.NgoInfo;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class represents the Non-profits organizations. It contains all the information about the organization
 * that was entered by the user and additional information for keeping track of the validity of the NGO.
 * The additional information is stored in a ConfirmationBadge object that is referred here via the uniqueId identifier.
 * Information about users that are related to the organization are also stored here as lists of uniqueId identifiers.
 * The relation to the organization can be in three forms: members, followers and users that want to become member and
 * sent a request for that.  
 * 
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Ngo implements Serializable, Cacheable {

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
	
	@Persistent
	private String confirmationBadge;

	@Persistent
	private Set<String> memberRequests = new HashSet<String>();

	@Persistent
	private Set<String> members = new HashSet<String>();

	@Persistent
	private Set<String> followers = new HashSet<String>();

	//@Persistent(mappedBy = "userPrefs")
	//@Element(dependent = "true")
	//private Set<FriendItem> friends = new HashSet<FriendItem>();

	public Ngo() {
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();  //this.getEmail();
		} 
		
		//ConfirmationBadge confB = ConfirmationBadge.findOrCreateNgo(new ConfirmationBadge(this));
        
		// Don't need to update the uniqueId of the confirmationBadge because
		// the class ConfirmationBadge takes care of this
		//this.confirmationBadge = confB.getUniqueId();
	}

	public Ngo(NgoInfo ngoInfo) {
		this();
		this.setName(ngoInfo.getName());
		this.setDescription(ngoInfo.getDescription());
		this.setAddress(ngoInfo.getAddress());
		this.setLatitude(ngoInfo.getLatitude());
		this.setLongitude(ngoInfo.getLongitude());
		this.setPhone(ngoInfo.getPhone());
		this.setEmail(ngoInfo.getEmail());
		this.setWebsite(ngoInfo.getWebsite());
		if (ngoInfo.getUniqueId() != null) this.setUniqueId(ngoInfo.getUniqueId());
		
	}

	  /**
	   * Given a NgoInfo object it updates the information of the Ngo object 
	   * from the NgoInfo object.
	   * 
	   * @param NgoInfo object 
	   * @return 
	   */
	public void reEdit(NgoInfo ngoInfo) {
		this.setName(ngoInfo.getName());
		this.setDescription(ngoInfo.getDescription());
		this.setName(ngoInfo.getName());
		this.setAddress(ngoInfo.getAddress());
		this.setLatitude(ngoInfo.getLatitude());
		this.setLongitude(ngoInfo.getLongitude());
		this.setPhone(ngoInfo.getPhone());
		this.setEmail(ngoInfo.getEmail());
		this.setWebsite(ngoInfo.getWebsite());	
	}

	  /**
	   * It creates a NgoInfo object based on the information of the Ngo object. It considers that
	   * the user that requests this is not related to the organization. This is important
	   * to activate Member and Follower. 
	   * 
	   * @param 
	   * @return  NgoInfo object
	   */
	public static NgoInfo toInfo(Ngo o) {
		if (o == null)
			return null;

		NgoInfo oInfo = new NgoInfo(o.getName(), o.getDescription(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
		oInfo.setUniqueId(o.getUniqueId());
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		oInfo.deactivateMember();
		oInfo.deactivateFollower();
		oInfo.setValid(o.isValid());
		oInfo.setConfirm(false);
		
		// Fill the information about members, followers,...
		List<String> nameList = new ArrayList<String>();
		if (o.getMembers() != null){
			for(String m:o.getMembers()){
				nameList.add(UserProfile.findUserProfile(new UserProfile(m)).getName());
			}
		} 
		oInfo.setMemberList(nameList);

		nameList = new ArrayList<String>();
		if (o.getMemberRequests() != null){
			for(String m:o.getMemberRequests()){
				nameList.add(UserProfile.findUserProfile(new UserProfile(m)).getName());
			}
		} 
		oInfo.setMemberReqList(nameList);

		nameList = new ArrayList<String>();
		if (o.getFollowers() != null){
			for(String m:o.getFollowers()){
				nameList.add(UserProfile.findUserProfile(new UserProfile(m)).getName());
			}
		} 
		oInfo.setFollowerList(nameList);
		
		return oInfo;
	}

	  /**
	   * It creates a NgoInfo object based on the information of the Ngo object. It considers the
	   * input user to activate Member and Follower. 
	   * 
	   * @param 
	   * @return  NgoInfo object
	   */
	public static NgoInfo toInfo(Ngo o, String userUniqueId) {
		if (o == null)
			return null;

		NgoInfo oInfo = Ngo.toInfo(o);
		
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		if (o.isMember(userUniqueId)) oInfo.activateMember();
		if (o.isFollower(userUniqueId)) oInfo.activateFollower();

		//oInfo.setValid(o.isValid());
		oInfo.setConfirm(o.isConfirmed(userUniqueId,o));
		
		return oInfo;
	}

	  /**
	   * Retrieve the user from the database if it exists an object with the same uniqueId as the Ngo or create a new object
	   * otherwise with the information of the input Ngo.
	   *  
	   * @param  Requested Ngo
	   * @return  Ngo object that was persisted
	   */
	  public static Ngo findOrCreateNgo(Ngo ngo) {
	
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Ngo oneResult = null, detached = null;
	
	    String uniqueId = ngo.getUniqueId();
	
	    Query q = pm.newQuery(Ngo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    boolean persistConfirmationBadge = false;
	    ConfirmationBadge confB = null;
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        if (uniqueId != null) oneResult = (Ngo) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("Ngo " + uniqueId + " does not exist, creating...");
	          confB = new ConfirmationBadge(ngo);
	          persistConfirmationBadge = true;
	          pm.makePersistent(ngo);
	          detached = pm.detachCopy(ngo);
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
	          pm.makePersistent(ngo);
	          detached = pm.detachCopy(ngo);	    	
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
	    
	    if (persistConfirmationBadge) {
	    	//ConfirmationBadge newConfB = ConfirmationBadge.findOrCreateNgo(confB);
	    	ConfirmationBadge.findOrCreateNgo(confB);
	    }
	    return detached;
	  }
	

	  /**
	   * Confirms the ConfirmationBadge of the Ngo (this) by a user representing the input Ngo (input)
	   *  
	   * @param  User profile and Ngo
	   * @return  
	   */
	public void addConfirmation(UserProfile user, Ngo ngo){
		if (!user.isMember(ngo)){
			log.info("User is not a member of the NGO");
			return;
		}
		this.getConfirmationBadge().addConfirmation(user, ngo);
	}

	  /**
	   * Confirms the ConfirmationBadge of the Ngo by the current user. In principle, 
	   * ConfirmationBadge.addConfirmation will check the user preferences to validate the Ngo badge.
	   *  
	   * @param  User profile
	   * @return  
	   */
	public void addConfirmation(UserProfile user){
		this.getConfirmationBadge().addConfirmation(user);
	}

	  /**
	   * Check if the input user is a member of the Ngo
	   *  
	   * @param  User profile uniqueId
	   * @return  true if member or false if not.
	   */
	public boolean isMember(String userUniqueId){
		return members.contains(userUniqueId);
	}

	  /**
	   * Check if the input user is a follower of the Ngo
	   *  
	   * @param  User profile uniqueId
	   * @return  true if follower or false if not.
	   */
	public boolean isFollower(String userUniqueId){
		return followers.contains(userUniqueId);
	}

	  /**
	   * Check if the organization is confirmed by the input user representing a specific NGO
	   *  
	   * @param  User profile and Ngo that represents
	   * @return  true if confirmed or false if not.
	   */
	public boolean isConfirmed(String userId, Ngo ngo){
		return this.getConfirmationBadge().isConfirmed(userId, ngo);
	}

	  /**
	   * Check if the organization is valid, meaning that it has been confirmed by an admin
	   *  
	   * @param  
	   * @return  true if validated or false if not.
	   */
	public boolean isValid(){
		return this.getConfirmationBadge().isValid();
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
	   * Get the Id of the NGO stored in this object. This id correspond to the index used by the database
	   *  
	   * @param  
	   * @return  Id of NGO (different from uniqueId)
	   */
	public Long getId() {
		return this.id;
	}

	  /**
	   * Get the uniqueId of the NGO stored in this object. This id is a unique identifier of the object
	   * and will be used in other classes to refer to a specific Ngo.
	   *  
	   * @param  
	   * @return  Unique Id of NGO 
	   */
	public String getUniqueId() {
		return uniqueId;
	}
	
	  /**
	   * Get the Name of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Name of NGO
	   */
	public String getName() {
		return this.name;
	}

	  /**
	   * Get the Description of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Description of NGO
	   */
	public String getDescription() {
		return this.description;
	}

	  /**
	   * Get the Address of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Address of NGO
	   */
	public String getAddress() {
		return this.address;
	}

	  /**
	   * Get the Latitude of the location of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Latitude of NGO
	   */
	public double getLatitude() {
		return this.lat;
	}
	
	  /**
	   * Get the Longitude of the location of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Longitude of NGO
	   */
	public double getLongitude() {
		return this.lng;
	}

	  /**
	   * Get the Phone of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Phone of NGO
	   */
	public String getPhone() {
		return this.phone;
	}

	  /**
	   * Get the email of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Email of NGO
	   */
	public String getEmail() {
		return this.email;
	}

	  /**
	   * Get the website of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Website of NGO
	   */
	public String getWebsite() {
		return this.website;
	}

	  /**
	   * Get the confirmation badge of the NGO stored in this object. Since the object stores
	   * the uniqueId it will call findNgo to retrieve the confirmation badge itself. It is assumed
	   * that all Ngo has a confirmation badge that was assigned when it was made persistent.
	   *  
	   * @param  
	   * @return  ConfirmationBadge of NGO
	   */	
	public ConfirmationBadge getConfirmationBadge(){
		return ConfirmationBadge.findNgo(this);
	}

	  /**
	   * Get the id of the confirmation badge of the NGO stored in this object
	   *  
	   * @param  
	   * @return  Id of confirmation badge of NGO
	   */
	public String getConfirmationBadgeId(){
		return confirmationBadge;
	}

	  /**
	   * Get the list of members that are in the list request.
	   *  
	   * @param  
	   * @return  List of unique Ids of member request list
	   */
	public Set<String> getMemberRequests(){
		return memberRequests;
	}

	  /**
	   * Get the list of members that are in the list request.
	   *  
	   * @param  
	   * @return  List of unique Ids of member request list
	   */
	public Set<String> getMembers(){
		return members;
	}
	
	  /**
	   * Get the list of followers.
	   *  
	   * @param  
	   * @return  List of unique Ids of followers
	   */
	public Set<String> getFollowers(){
		return followers;
	}
	
	  /**
	   * Set the Name of the NGO.
	   *  
	   * @param  Name of NGO
	   * @return  
	   */
	public void setName(String name) {
		this.name = name;
	}

	  /**
	   * Set the description of the NGO.
	   *  
	   * @param  Description of NGO
	   * @return  
	   */
	public void setDescription(String description) {
		this.description = description;
	}

	  /**
	   * Set the address of the NGO.
	   *  
	   * @param  Address of NGO
	   * @return  
	   */
	public void setAddress(String address) {
		this.address = address;
	}

	  /**
	   * Set the latitude of the NGO.
	   *  
	   * @param  Latitude of NGO
	   * @return  
	   */
	public void setLatitude(double lat) {
		this.lat = lat;
	}

	  /**
	   * Set the longitude of the NGO.
	   *  
	   * @param  Longitude of NGO
	   * @return  
	   */
	public void setLongitude(double lng) {
		this.lng = lng;
	}

	  /**
	   * Set the phone of the NGO.
	   *  
	   * @param  Phone of NGO
	   * @return  
	   */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	  /**
	   * Set the email of the NGO.
	   *  
	   * @param  Email of NGO
	   * @return  
	   */
	public void setEmail(String email) {
		this.email = email;
	}

	  /**
	   * Set the website of the NGO.
	   *  
	   * @param  Website of NGO
	   * @return  
	   */
	public void setWebsite(String website) {
		this.website = website;
	}

	  /**
	   * Set the id of the NGO. This id is the index in the database. Do we want to be able to set it?
	   *  
	   * @param  Id of NGO
	   * @return  
	   */
	public void setId(Long id) {
		this.id = id;
	}

	  /**
	   * Set the confirmation badge id of the NGO. Do we really want to be able to modify the id of the confirmation badge?
	   *  
	   * @param  UniqueId of NGO confirmation badge
	   * @return  
	   */
	public void setConfirmationBadge(String uniqueId){
		this.confirmationBadge = uniqueId;
	}

	  /**
	   * Set unique id of the NGO. 
	   *  
	   * @param  UniqueId of NGO 
	   * @return  
	   */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	  /**
	   * Used when a user want to be part of the members of the NGO. The user will be added to the 
	   * member request list.
	   *  
	   * @param  UniqueId of the user requesting membership 
	   * @return  
	   */
	public void requestMember(String member){
		// Check if the member exist
		if (memberRequests.contains(member)) return;
		
		// Add it if it does not
		memberRequests.add(member);
	}

	  /**
	   * Used to add a member to the NGO. The user will be added to the member list of the object.
	   * We should probably check if the logged in user is a member that has permissions to do so.
	   * Also, we should remove the added member from the request member list if existing.
	   *  
	   * @param  UniqueId of the user added as a member.
	   * @return  
	   */
	public void addMember(String member){
		// Check if the member exist
		if (members.contains(member)) return;
		
		// Add it if it does not
		members.add(member);
	}

	  /**
	   * Used to add as follower of the NGO. 
	   *  
	   * @param  UniqueId of the user added as a follower.
	   * @return  
	   */
	public void addFollowers(String follower){
		// Check if the member exist
		if (followers.contains(follower)) return;
		
		// Add it if it does not
		followers.add(follower);
	}
}