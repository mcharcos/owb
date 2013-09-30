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
import com.owb.playhelp.shared.AreaStandardInfo;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;
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
	private AreaStandard responsibility;
	
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
	
	public Standard(DBRecordInfo record){
		this();
		this.setName(record.getName());
		this.description = new Text(record.getDescription());
		this.setAddress(record.getAddress());
		this.setLatitude(record.getLatitude());
		this.setLongitude(record.getLongitude());
		
		this.add(record.getStandard());
	}

	public Standard(DBRecord record){
		this();
		this.setName(record.getName());
		this.description = record.getDescription();
		this.setAddress(record.getAddress());
		this.setLatitude(record.getLatitude());
		this.setLongitude(record.getLongitude());
	}
	
	public Standard(StandardInfo stdInfo){
		this();
		this.add(stdInfo);
	}

	/**
	 * Retrieve the user from the database if it already exist or
	 * create a new account if it is the first loggin
	 * @param Standard
	 * @return
	 */
	  public static Standard findOrCreate(Standard record, Long userId) {
	    return record;
	  }
	
	  public static StandardInfo toInfo(Standard std, Date date){
		  StandardInfo stdInfo = new StandardInfo(std.getWater(date),
													std.getFood(date),
													std.getShelter(date),
													std.getClothing(date),
													std.getMedicine(date),
													std.getHygiene(date),
													std.getSafety(date),
													std.getActivity(date),
													std.getEducation(date),
													std.getGuidance(date),
													std.getResponsibility(date),
													std.getDiscipline(date),
													std.getLove(date),
													std.getCompassion(date),
													std.getJoy(date),
													std.getHope(date));
		  
		  stdInfo.setUniqueId(std.getUniqueId());
		  return stdInfo;
	  }

	  public static StandardInfo toInfo(Standard std){
		  return toInfo(std, new Date());
	  }
	
	  // For the add functions we may want to check if the values changed before
	  // adding. If not the list may get very long as the users edit information
	  // We should find a way to add only when the standard is updated.
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
	public void addResponsibility(Long stdStatus, String stdDesc){
		responsibility.add(stdStatus, stdDesc);
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
	
	public void add(StandardInfo standardInfo){
		this.addWater(standardInfo.getWaterStatus(),standardInfo.getWaterDescription());
		this.addFood(standardInfo.getFoodStatus(),standardInfo.getFoodDescription());
		this.addShelter(standardInfo.getShelterStatus(),standardInfo.getSafetyDescription());
		this.addClothing(standardInfo.getClothingStatus(),standardInfo.getClothingDescription());
		this.addMedicine(standardInfo.getMedicineStatus(),standardInfo.getMedicineDescription());
		this.addHygiene(standardInfo.getHygieneStatus(),standardInfo.getHopeDescription());
		this.addSafety(standardInfo.getSafetyStatus(),standardInfo.getSafetyDescription());
		this.addActivity(standardInfo.getActivityStatus(),standardInfo.getActivityDescription());
		this.addEducation(standardInfo.getEducationStatus(),standardInfo.getEducationDescription());
		this.addGuidance(standardInfo.getGuidanceStatus(),standardInfo.getGuidanceDescription());
		this.addResponsibility(standardInfo.getResponsibilityStatus(),standardInfo.getResponsibilityDescription());
		this.addDiscipline(standardInfo.getDisciplineStatus(),standardInfo.getDisciplineDescription());
		this.addLove(standardInfo.getLoveStatus(),standardInfo.getLoveDescription());
		this.addCompassion(standardInfo.getCompassionStatus(),standardInfo.getCompassionDescription());
		this.addJoy(standardInfo.getJoyStatus(),standardInfo.getJoyDescription());
		this.addHope(standardInfo.getHopeStatus(),standardInfo.getHopeDescription());
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
	public void setDescription(Text description) {
		this.description = description;
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
	
	/*
	 * Methods to retrieve the standards at a specific date 
	 * or the latest one
	 */
	private AreaStandardInfo getStdInfo(AreaStandard std, Date date){
		AreaStandardInfo area = new AreaStandardInfo (std.getStatus(date),std.getDescription(date).toString(),std.getDate(date));
		return area;
	}
	public AreaStandardInfo getWater(Date date){
		return this.getStdInfo(this.water, date);
	}
	public AreaStandardInfo getLastWater(Date date){
		return this.getStdInfo(this.water, new Date());
	}

	public AreaStandardInfo getFood(Date date){
		return this.getStdInfo(this.food, date);
	}
	public AreaStandardInfo getLastFood(Date date){
		return this.getStdInfo(this.food, new Date());
	}

	public AreaStandardInfo getShelter(Date date){
		return this.getStdInfo(this.shelter, date);
	}
	public AreaStandardInfo getLastShelter(Date date){
		return this.getStdInfo(this.shelter, new Date());
	}

	public AreaStandardInfo getClothing(Date date){
		return this.getStdInfo(this.clothing, date);
	}
	public AreaStandardInfo getLastClothing(Date date){
		return this.getStdInfo(this.clothing, new Date());
	}

	public AreaStandardInfo getMedicine(Date date){
		return this.getStdInfo(this.medicine, date);
	}
	public AreaStandardInfo getLastMedicine(Date date){
		return this.getStdInfo(this.medicine, new Date());
	}

	public AreaStandardInfo getHygiene(Date date){
		return this.getStdInfo(this.hygiene, date);
	}
	public AreaStandardInfo getLastHygiene(Date date){
		return this.getStdInfo(this.hygiene, new Date());
	}

	public AreaStandardInfo getSafety(Date date){
		return this.getStdInfo(this.safety, date);
	}
	public AreaStandardInfo getLastSafety(Date date){
		return this.getStdInfo(this.safety, new Date());
	}

	public AreaStandardInfo getActivity(Date date){
		return this.getStdInfo(this.activity, date);
	}
	public AreaStandardInfo getLastActivity(Date date){
		return this.getStdInfo(this.activity, new Date());
	}

	public AreaStandardInfo getEducation(Date date){
		return this.getStdInfo(this.education, date);
	}
	public AreaStandardInfo getLastEducation(Date date){
		return this.getStdInfo(this.education, new Date());
	}

	public AreaStandardInfo getGuidance(Date date){
		return this.getStdInfo(this.guidance, date);
	}
	public AreaStandardInfo getLastGuidance(Date date){
		return this.getStdInfo(this.guidance, new Date());
	}

	public AreaStandardInfo getResponsibility(Date date){
		return this.getStdInfo(this.responsibility, date);
	}
	public AreaStandardInfo getLastResponsibility(Date date){
		return this.getStdInfo(this.responsibility, new Date());
	}

	public AreaStandardInfo getDiscipline(Date date){
		return this.getStdInfo(this.discipline, date);
	}
	public AreaStandardInfo getLastDiscipline(Date date){
		return this.getStdInfo(this.discipline, new Date());
	}

	public AreaStandardInfo getLove(Date date){
		return this.getStdInfo(this.love, date);
	}
	public AreaStandardInfo getLastLove(Date date){
		return this.getStdInfo(this.love, new Date());
	}

	public AreaStandardInfo getCompassion(Date date){
		return this.getStdInfo(this.compassion, date);
	}
	public AreaStandardInfo getLastCompassion(Date date){
		return this.getStdInfo(this.compassion, new Date());
	}

	public AreaStandardInfo getJoy(Date date){
		return this.getStdInfo(this.joy, date);
	}
	public AreaStandardInfo getLastJoy(Date date){
		return this.getStdInfo(this.joy, new Date());
	}

	public AreaStandardInfo getHope(Date date){
		return this.getStdInfo(this.hope, date);
	}
	public AreaStandardInfo getLastHope(Date date){
		return this.getStdInfo(this.hope, new Date());
	}

	/**
	 * Returns true if the input unique Id is a member of the 
	 * record.
	 * @param userId
	 * @return
	 */
	public boolean isMember(Long userId){
		return true;
	}
}