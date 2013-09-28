/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

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
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.appengine.api.datastore.Text;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.server.domain.associations.NgoUser;
import com.owb.playhelp.server.utils.EmailHelper;
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
public class Standard implements Serializable, Cacheable {

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
     * Date of creation of the standard
     * This is to keep track of how the standard evolve
     * with time. For each standard, the date, status and 
     * description is entered at the same time.
    */
	/*
	* Clean Water
	*/ 	
	@Persistent(dependent = "true")
	private AreaStandard water;
	
	/*
	* Food
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard food;
	
	/*
	* Shelter
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard shelter;
	
	/*
	* Clothing
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard clothing;
	
	/*
	* Medicine
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard medicine;
	
	/*
	* Hygiene
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard hygiene;
	
	/*
	* Safety
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard safety;
	
	/*
	* Physical Activities
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard activity;
	
	/*
	* Schooling Education
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard education;
	
	/*
	* Guidance
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard guidance;
	
	/*
	* Responsabilities
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard responsability;
	
	/*
	* Discipline
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard discipline;
	
	/*
	* Love
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard love;
	
	/*
	* Compassionate Environment
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard compassion;
	
	/*
	* Joy
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard joy;
	
	/*
	* Hope of Future
	*/ 
	@Persistent(dependent = "true")
	private AreaStandard hope;
		
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
	public Standard() {
		UUID uuid = UUID.randomUUID();
		this.uniqueId = uuid.toString();  
	}
	
	public Standard(DBRecord record){
		this();
		this.setName(record.getName());
		this.description = record.getDescription();
		this.setAddress(record.getAddress());
		this.setLatitude(record.getLatitude());
		this.setLongitude(record.getLongitude());
	}


	/**
	 * Retrieve the user from the database if it already exist or
	 * create a new account if it is the first loggin
	 * @param Standard
	 * @return
	 */
	  public static Standard findOrCreate(Standard record, String userId) {
	    return record;
	  }
	
	
	public void addWater(Long stdStatus, String stdDesc){
		water.add(stdStatus, stdDesc);
	}
	public void addFood(Long stdStatus, String stdDesc){
		food.add(stdStatus, stdDesc);
	}
	public void addShelter(Long stdStatus, String stdDesc){
		shelter.add(stdStatus, stdDesc);
	}
	public void addClothing(Long stdStatus, String stdDesc){
		clothing.add(stdStatus, stdDesc);
	}
	public void addMedicine(Long stdStatus, String stdDesc){
		medicine.add(stdStatus, stdDesc);
	}
	public void addHygiene(Long stdStatus, String stdDesc){
		hygiene.add(stdStatus, stdDesc);
	}
	public void addSafety(Long stdStatus, String stdDesc){
		safety.add(stdStatus, stdDesc);
	}
	public void addActivity(Long stdStatus, String stdDesc){
		activity.add(stdStatus, stdDesc);
	}
	public void addEducation(Long stdStatus, String stdDesc){
		education.add(stdStatus, stdDesc);
	}
	public void addGuidance(Long stdStatus, String stdDesc){
		guidance.add(stdStatus, stdDesc);
	}
	public void addResponsability(Long stdStatus, String stdDesc){
		responsability.add(stdStatus, stdDesc);
	}
	public void addDiscipline(Long stdStatus, String stdDesc){
		discipline.add(stdStatus, stdDesc);
	}
	public void addLove(Long stdStatus, String stdDesc){
		love.add(stdStatus, stdDesc);
	}
	public void addCompassion(Long stdStatus, String stdDesc){
		compassion.add(stdStatus, stdDesc);
	}
	public void addJoy(Long stdStatus, String stdDesc){
		joy.add(stdStatus, stdDesc);
	}
	public void addHope(Long stdStatus, String stdDesc){
		hope.add(stdStatus, stdDesc);
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