/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.appengine.api.datastore.Text;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;

/**
 * 
 * @author Miguel Charcos Llorens
 * This class represents a record entity in the data store. It includes the information
 * intrinsic to the record such as location and contact information. It is a parent class
 * for other records as ngo, orphanages, users,...
 *
 */
@SuppressWarnings("serial") 
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class DBRecord implements Serializable, Cacheable  {

	  protected static final Logger log = Logger.getLogger(Utils.class.getName());
	  protected static final int NUM_RETRIES = 5; 
  
	/*
	 * Index id of the data store
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	/*
	 * Name of the record
	 */
	@Persistent
	private String name;

	/*
	 * Description of the record
	 */
	@Persistent
	private Text description;
	
	/*
	 * Address of the record
	 */
	@Persistent
	private String address;

	/*
	 * Latitude and longitude of the location of the record
	 * It helps to manage the location in the map more efficiently
	 * instead of transalting the string address to location each time
	 * it is visualized in the map
	 */
	@Persistent
	private double lat, lng;
	
	/*
	 * Phone number of the record
	 */
	@Persistent
	private String phone;

	/*
	 * Contact email of the record. 
	 */
	@Persistent
	private String email;

	/*
	 * Website of the record
	 */
	@Persistent
	private String website;

	/*
	 * Id used to identify the record when it is passed to the front-end
	 */
	@Persistent
	private String uniqueId;

	/**
	 * Constructor of the class. When the object is created, it is assigned 
	 * with a unique identifier in addition to the data-store id. This is 
	 * used as a reference when passed to the front end.
	 */
	public DBRecord() {
		UUID uuid = UUID.randomUUID();
		this.uniqueId = uuid.toString();  
	}

	/**
	 * Constructor class from a shared class. It will initialize an object
	 * with the information from an OrphanageInfo object passed from the 
	 * front end. This constructor facilitates the creation of back end
	 * objects using information from the front end.
	 * @param DBRecordInfo
	 */
	public DBRecord(DBRecordInfo recordInfo) {
		// Construct the object
		this();
		
		// Copy the information from the input
		this.reEdit(recordInfo);
		this.setUniqueId(recordInfo.getUniqueId());
		
		// If the input object did not contain a uniqueId, that means 
		// that it should be created because probably is a new entry
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();
		} 
	}

	/**
	 * It fills the attributes of a data-store object with the
	 * information of the input share object coming from the front
	 * end. 
	 * @param DBRecordInfo
	 */
	public void reEdit(DBRecordInfo recordInfo) {
		
		/*
		if (!this.getUniqueId().equals(OrphanageInfo.getUniqueId())){
			return;
		}*/
		
		this.setName(recordInfo.getName());
		this.setDescription(recordInfo.getDescription());
		this.setAddress(recordInfo.getAddress());
		this.setLatitude(recordInfo.getLatitude());
		this.setLongitude(recordInfo.getLongitude());
		this.setPhone(recordInfo.getPhone());
		this.setEmail(recordInfo.getEmail());
		this.setWebsite(recordInfo.getWebsite());	
	}
	
	/**
	 * Creates a share object to be passed to the front end. The resulting
	 * object contains the information of the data-store object.
	 * @param o
	 * @return
	 */
	public static DBRecordInfo toInfo(Integer dbType, DBRecord o) {
		if (o == null)
			return null;

		// Create a new object with the information from the input
		DBRecordInfo oInfo = new DBRecordInfo(dbType, o.getName(), o.getDescription().getValue(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
		
		// Set the unique Id to the current Id from the input object
		oInfo.setUniqueId(o.getUniqueId());
		
		// Set the privacy according to the input objects
		// For now we deactivate everything but we should check if there 
		// are not data-store entries that link this object to the user
		// TODO The user should be read from the current login for security reasons.
		// We need to think if this may be handled by the service implementation instead
		oInfo.deactivateMember();
		oInfo.deactivateFollower();
		
		return oInfo;
	}

	/**
	 * Creates a share object to be passed to the front end. The resulting
	 * object contains the information of the data-store object. The returned
	 * object privacy is set according to the input userUniqueId accessibility 
	 * to the input record. 
	 * @param o
	 * @param userUniqueId
	 * @return
	 */
	public static DBRecordInfo toInfo(Integer dbType, DBRecord o, Long userId) {
		if (o == null)
			return null;

		// Create a new object with the information from the input
		DBRecordInfo oInfo = DBRecord.toInfo(dbType, o);
		
		// Check if the share object was created. If not, null should be 
		// returned instead of setting privacies. 
		if (oInfo == null)
			return null;

		// Set privacy depending on the user uniqueId
		// If the user with uniqueId id is a member or a follower
		// the appropriate attributes of the shared object
		// will be set.
		if (o.isMember(userId)) oInfo.activateMember();
		if (o.isFollower(userId)) oInfo.activateFollower();
		
		return oInfo;
	}
	

	/**
	 * Retrieve the user from the database if it already exist or
	 * create a new account if it is the first loggin
	 * @param DBRecord
	 * @return
	 */
	  public static DBRecord findOrCreateDBRecord(DBRecord record) {
		  return record;
	  }
	
	/**
	 * Returns true if the input unique Id is a member of the 
	 * record.
	 * @param userUniqueId
	 * @return
	 */
	public boolean isMember(Long userId){
		// TODO -- Should create a class member and a function to search in the 
		// data store giving a specific record id.
		// return Members.contains(userUniqueId)
		return true;  //members.contains(userUniqueId);
	}
	
	public boolean isFollower(Long userId){
		return true;  //followers.contains(userUniqueId);
	}
	
	public boolean isNgo(Long ngoId){
		return true;  //ngos.contains(ngoUniqueId);
	}
	  
	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	public String getName() {
		return this.name;
	}
	public Text getDescription() {
		return this.description;
	}
	public String getAddress() {
		return this.address;
	}
	public double getLatitude() {
		return this.lat;
	}
	public double getLongitude() {
		return this.lng;
	}
	public String getPhone() {
		return this.phone;
	}
	public String getEmail() {
		return this.email;
	}
	public String getWebsite() {
		return this.website;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = new Text(description);
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLatitude(double lat) {
		this.lat = lat;
	}
	public void setLongitude(double lng) {
		this.lng = lng;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}
	
}