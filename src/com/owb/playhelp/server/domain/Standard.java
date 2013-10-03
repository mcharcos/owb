/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.appengine.api.datastore.Text;
import com.owb.playhelp.shared.AreaStandardInfo;
import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;
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

	  //private static final long serialVersionUID = -2023204547641864687L;
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
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="waterDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="waterStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="waterDesc"))})*/
	private AreaStandard water; 
	
	/*
	* Food
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="foodDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="foodStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="foodDesc"))})*/
	private AreaStandard food;
	
	/*
	* Shelter
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="shelterDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="shelterStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="shelterDesc"))})*/
	private AreaStandard shelter;
	
	/*
	* Clothing
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="clothingDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="clothingStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="clothingDesc"))})*/
	private AreaStandard clothing;
	
	/*
	* Medicine
	*/ 
	/*@Persistent(serialized = "true")
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="medicineDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="medicineStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="medicineDesc"))})*/
	private AreaStandard medicine;
	
	/*
	* Hygiene
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="hygieneDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="hygieneStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="hygieneDesc"))})*/
	private AreaStandard hygiene;
	
	/*
	* Safety
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="safetyDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="safetyStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="safetyDesc"))})*/
	private AreaStandard safety;
	
	/*
	* Physical Activities
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="activityDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="activityStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="activityDesc"))})*/
	private AreaStandard activity;
	
	/*
	* Schooling Education
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="educationDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="educationStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="educationDesc"))})*/
	private AreaStandard education;
	
	/*
	* Guidance
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="guidanceDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="guidanceStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="guidanceDesc"))})*/
	private AreaStandard guidance;
	
	/*
	* Responsabilities
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="responsibilityDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="responsibilityStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="responsibilityDesc"))})*/
	private AreaStandard responsibility;
	
	/*
	* Discipline
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="disciplineDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="disciplineStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="disciplineDesc"))})*/
	private AreaStandard discipline;
	
	/*
	* Love
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="loveDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="loveStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="loveDesc"))})*/
	private AreaStandard love;
	
	/*
	* Compassionate Environment
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="compassionDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="compassionStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="compassionDesc"))})*/
	private AreaStandard compassion;
	
	/*
	* Joy
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="joyDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="joyStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="joyDesc"))})*/
	private AreaStandard joy;
	
	/*
	* Hope of Future
	*/ 
	/*@Persistent
	@Embedded(members = {
	        @Persistent(name="stdDate", columns=@Column(name="hopeDate")),
	        @Persistent(name="stdStatus", columns=@Column(name="hopeStatus")),
	        @Persistent(name="stdDesc", columns=@Column(name="hopeDesc"))})*/
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
		
		if (record.getStandard() == null){
			log.info("Standard(DBRecordInfo): Does not have StandardInfo");
			return;
		}
		this.setUniqueId(record.getStandard().getUniqueId());
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
		  StandardInfo stdInfo = new StandardInfo(-1, 
				  									std.getWater(date),
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
		if (water == null){
			water = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		water.add(stdStatus, stdDesc);
	}
	public void addFood(Long stdStatus, String stdDesc){
		if (food == null){
			food = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		food.add(stdStatus, stdDesc);
	}
	public void addShelter(Long stdStatus, String stdDesc){
		if (shelter == null){
			shelter = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		shelter.add(stdStatus, stdDesc);
	}
	public void addClothing(Long stdStatus, String stdDesc){
		if (clothing == null){
			clothing = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		clothing.add(stdStatus, stdDesc);
	}
	public void addMedicine(Long stdStatus, String stdDesc){
		if (medicine == null){
			medicine = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		medicine.add(stdStatus, stdDesc);
	}
	public void addHygiene(Long stdStatus, String stdDesc){
		if (hygiene == null){
			hygiene = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		hygiene.add(stdStatus, stdDesc);
	}
	public void addSafety(Long stdStatus, String stdDesc){
		if (safety == null){
			safety = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		safety.add(stdStatus, stdDesc);
	}
	public void addActivity(Long stdStatus, String stdDesc){
		if (activity == null){
			activity = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		activity.add(stdStatus, stdDesc);
	}
	public void addEducation(Long stdStatus, String stdDesc){
		if (education == null){
			education = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		education.add(stdStatus, stdDesc);
	}
	public void addGuidance(Long stdStatus, String stdDesc){
		if (guidance == null){
			guidance = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		guidance.add(stdStatus, stdDesc);
	}
	public void addResponsibility(Long stdStatus, String stdDesc){
		if (responsibility == null){
			responsibility = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		responsibility.add(stdStatus, stdDesc);
	}
	public void addDiscipline(Long stdStatus, String stdDesc){
		if (discipline == null){
			discipline = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		discipline.add(stdStatus, stdDesc);
	}
	public void addLove(Long stdStatus, String stdDesc){
		if (love == null){
			love = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		love.add(stdStatus, stdDesc);
	}
	public void addCompassion(Long stdStatus, String stdDesc){
		if (compassion == null){
			compassion = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		compassion.add(stdStatus, stdDesc);
	}
	public void addJoy(Long stdStatus, String stdDesc){
		if (joy == null){
			joy = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		joy.add(stdStatus, stdDesc);
	}
	public void addHope(Long stdStatus, String stdDesc){
		if (hope == null){
			hope = new AreaStandard(stdStatus,stdDesc);
			return;
		}
		hope.add(stdStatus, stdDesc);
	}
	
	public void add(StandardInfo standardInfo){
		if (standardInfo == null){
			log.info("Input standard information is null");
			return;
		}
		
		if (standardInfo.getUniqueId() != null){
			this.uniqueId = standardInfo.getUniqueId();
		}
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
		if (std == null){
			log.info("Standard area is empty");
			return new AreaStandardInfo(100L,"default");
		}
		AreaStandardInfo area = new AreaStandardInfo (std.getStatus(date),std.getDescription(date).toString(),std.getDate(date));
		return area;
	}
	public AreaStandardInfo getWater(Date date){
		log.info("Getting water standard");
		return this.getStdInfo(this.water, date);
	}
	public AreaStandardInfo getLastWater(Date date){
		log.info("Getting last water standard");
		return this.getStdInfo(this.water, new Date());
	}

	public AreaStandardInfo getFood(Date date){
		log.info("Getting food standard");
		return this.getStdInfo(this.food, date);
	}
	public AreaStandardInfo getLastFood(Date date){
		log.info("Getting last food standard");
		return this.getStdInfo(this.food, new Date());
	}

	public AreaStandardInfo getShelter(Date date){
		log.info("Getting shelter standard");
		return this.getStdInfo(this.shelter, date);
	}
	public AreaStandardInfo getLastShelter(Date date){
		log.info("Getting last shelter standard");
		return this.getStdInfo(this.shelter, new Date());
	}

	public AreaStandardInfo getClothing(Date date){
		log.info("Getting clothing standard");
		return this.getStdInfo(this.clothing, date);
	}
	public AreaStandardInfo getLastClothing(Date date){
		log.info("Getting last clothing standard");
		return this.getStdInfo(this.clothing, new Date());
	}

	public AreaStandardInfo getMedicine(Date date){
		log.info("Getting medicine standard");
		return this.getStdInfo(this.medicine, date);
	}
	public AreaStandardInfo getLastMedicine(Date date){
		log.info("Getting last medicine standard");
		return this.getStdInfo(this.medicine, new Date());
	}

	public AreaStandardInfo getHygiene(Date date){
		log.info("Getting hygiene standard");
		return this.getStdInfo(this.hygiene, date);
	}
	public AreaStandardInfo getLastHygiene(Date date){
		log.info("Getting last hygiene standard");
		return this.getStdInfo(this.hygiene, new Date());
	}

	public AreaStandardInfo getSafety(Date date){
		log.info("Getting safety standard");
		return this.getStdInfo(this.safety, date);
	}
	public AreaStandardInfo getLastSafety(Date date){
		log.info("Getting last safety standard");
		return this.getStdInfo(this.safety, new Date());
	}

	public AreaStandardInfo getActivity(Date date){
		log.info("Getting activity standard");
		return this.getStdInfo(this.activity, date);
	}
	public AreaStandardInfo getLastActivity(Date date){
		log.info("Getting last activty standard");
		return this.getStdInfo(this.activity, new Date());
	}

	public AreaStandardInfo getEducation(Date date){
		log.info("Getting education standard");
		return this.getStdInfo(this.education, date);
	}
	public AreaStandardInfo getLastEducation(Date date){
		log.info("Getting last education standard");
		return this.getStdInfo(this.education, new Date());
	}

	public AreaStandardInfo getGuidance(Date date){
		log.info("Getting guidance standard");
		return this.getStdInfo(this.guidance, date);
	}
	public AreaStandardInfo getLastGuidance(Date date){
		log.info("Getting last guidance standard");
		return this.getStdInfo(this.guidance, new Date());
	}

	public AreaStandardInfo getResponsibility(Date date){
		log.info("Getting responsibility standard");
		return this.getStdInfo(this.responsibility, date);
	}
	public AreaStandardInfo getLastResponsibility(Date date){
		log.info("Getting last responsibility standard");
		return this.getStdInfo(this.responsibility, new Date());
	}

	public AreaStandardInfo getDiscipline(Date date){
		log.info("Getting discipline standard");
		return this.getStdInfo(this.discipline, date);
	}
	public AreaStandardInfo getLastDiscipline(Date date){
		log.info("Getting last discipline standard");
		return this.getStdInfo(this.discipline, new Date());
	}

	public AreaStandardInfo getLove(Date date){
		log.info("Getting love standard");
		return this.getStdInfo(this.love, date);
	}
	public AreaStandardInfo getLastLove(Date date){
		log.info("Getting last love standard");
		return this.getStdInfo(this.love, new Date());
	}

	public AreaStandardInfo getCompassion(Date date){
		log.info("Getting compassion standard");
		return this.getStdInfo(this.compassion, date);
	}
	public AreaStandardInfo getLastCompassion(Date date){
		log.info("Getting last compassion standard");
		return this.getStdInfo(this.compassion, new Date());
	}

	public AreaStandardInfo getJoy(Date date){
		log.info("Getting joy standard");
		return this.getStdInfo(this.joy, date);
	}
	public AreaStandardInfo getLastJoy(Date date){
		log.info("Getting last joy standard");
		return this.getStdInfo(this.joy, new Date());
	}

	public AreaStandardInfo getHope(Date date){
		log.info("Getting hope standard");
		return this.getStdInfo(this.hope, date);
	}
	public AreaStandardInfo getLastHope(Date date){
		log.info("Getting last hope standard");
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