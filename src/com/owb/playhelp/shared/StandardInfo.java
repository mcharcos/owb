package com.owb.playhelp.shared;

import java.io.Serializable;
 
@SuppressWarnings("serial")
public class StandardInfo implements Serializable  {

	public static Integer USER=0, ORGANIZATION=1, ORPHANAGE=2, PROJECT=3, VOLUNTEER=4;

	private Integer dbType;
	private boolean member;
	//private boolean follower;
	
	private String uniqueId;
	
	/*
	* Clean Water
	*/ 	
	private AreaStandardInfo water;
	
	/*
	* Food
	*/ 
	private AreaStandardInfo food;
	
	/*
	* Shelter
	*/ 
	private AreaStandardInfo shelter;
	
	/*
	* Clothing
	*/ 
	private AreaStandardInfo clothing;
	
	/*
	* Medicine
	*/ 
	private AreaStandardInfo medicine;
	
	/*
	* Hygiene
	*/ 
	private AreaStandardInfo hygiene;
	
	/*
	* Safety
	*/ 
	private AreaStandardInfo safety;
	
	/*
	* Physical Activities
	*/ 
	private AreaStandardInfo activity;
	
	/*
	* Schooling Education
	*/ 
	private AreaStandardInfo education;
	
	/*
	* Guidance
	*/ 
	private AreaStandardInfo guidance;
	
	/*
	* Responsabilities
	*/ 
	private AreaStandardInfo responsibility;
	
	/*
	* Discipline
	*/ 
	private AreaStandardInfo discipline;
	
	/*
	* Love
	*/ 
	private AreaStandardInfo love;
	
	/*
	* Compassionate Environment
	*/ 
	private AreaStandardInfo compassion;
	
	/*
	* Joy
	*/ 
	private AreaStandardInfo joy;
	
	/*
	* Hope of Future
	*/ 
	private AreaStandardInfo hope;

	public StandardInfo(){
		this.dbType = -1;
	}
	public StandardInfo(Integer dbType){
		this();
		this.dbType = dbType;
	}
	public StandardInfo(Integer dbType,
						Long water, Long food, Long shelter, Long clothing, 
			            Long medicine, Long hygiene, Long safety, Long activity, 
			            Long education, Long guidance, Long responsibility, Long discipline, 
			            Long love, Long compassion, Long joy, Long hope){
		this(dbType);
		setStatus(water, food, shelter, clothing, medicine, hygiene, safety, activity, 
	            education, guidance, responsibility, discipline, love, compassion, joy, hope);
		setDescription("","","","","","","","","","","","","","","","");
	}
	public StandardInfo(Integer dbType,
			Long water, String waterDesc, Long food, String foodDesc, Long shelter, String shelterDesc, Long clothing, String clothingDesc, 
            Long medicine, String medicineDesc, Long hygiene, String hygieneDesc, Long safety, String safetyDesc, Long activity, String activityDesc, 
            Long education, String educationDesc, Long guidance, String guidanceDesc, Long responsibility, String responsibilityDesc, Long discipline, String disciplineDesc,
            Long love, String loveDesc, Long compassion, String compassionDesc, Long joy, String joyDesc, Long hope, String hopeDesc){
		this(dbType,water, food, shelter, clothing, medicine, hygiene, safety, activity, 
	            education, guidance, responsibility, discipline, love, compassion, joy, hope);
		setDescription(waterDesc, foodDesc, shelterDesc, clothingDesc, medicineDesc, hygieneDesc, safetyDesc, activityDesc, 
                educationDesc, guidanceDesc, responsibilityDesc, disciplineDesc, loveDesc, compassionDesc, joyDesc, hopeDesc);
	}
	public StandardInfo(Integer dbType,
			AreaStandardInfo water, AreaStandardInfo food, AreaStandardInfo shelter, AreaStandardInfo clothing, 
            AreaStandardInfo medicine, AreaStandardInfo hygiene, AreaStandardInfo safety, AreaStandardInfo activity, 
            AreaStandardInfo education, AreaStandardInfo guidance, AreaStandardInfo responsibility, AreaStandardInfo discipline, 
            AreaStandardInfo love, AreaStandardInfo compassion, AreaStandardInfo joy, AreaStandardInfo hope){
		this(dbType);
		this.water = water;
		this.food = food;
		this.shelter = shelter;
		this.clothing = clothing;
		this.medicine = medicine;
		this.hygiene = hygiene;
		this.safety = safety;
		this.activity = activity;
		this.education = education;
		this.guidance = guidance;
		this.responsibility = responsibility;
		this.discipline = discipline;
		this.love = love;
		this.compassion = compassion;
		this.joy = joy;
		this.hope = hope;
	}
	
	public void setStatus(Long water, Long food, Long shelter, Long clothing, 
            Long medicine, Long hygiene, Long safety, Long activity, 
            Long education, Long guidance, Long responsibility, Long discipline, 
            Long love, Long compassion, Long joy, Long hope){
		this.water.setStatus(water);
		this.food.setStatus(food);
		this.shelter.setStatus(shelter);
		this.clothing.setStatus(clothing);
		this.medicine.setStatus(medicine);
		this.hygiene.setStatus(hygiene);
		this.safety.setStatus(safety);
		this.activity.setStatus(activity);
		this.education.setStatus(education);
		this.guidance.setStatus(guidance);
		this.responsibility.setStatus(responsibility);
		this.discipline.setStatus(discipline);
		this.love.setStatus(love);
		this.compassion.setStatus(compassion);
		this.joy.setStatus(joy);
		this.hope.setStatus(hope);
	}
	
	public void setDescription(String waterDesc, String foodDesc, String shelterDesc, String clothingDesc, 
                               String medicineDesc, String hygieneDesc, String safetyDesc, String activityDesc, 
                               String educationDesc, String guidanceDesc, String responsibilityDesc, String disciplineDesc,
                               String loveDesc, String compassionDesc, String joyDesc, String hopeDesc){
		this.water.setDescription(waterDesc);
		this.food.setDescription(foodDesc);
		this.shelter.setDescription(shelterDesc);
		this.clothing.setDescription(clothingDesc);
		this.medicine.setDescription(medicineDesc);
		this.hygiene.setDescription(hygieneDesc);
		this.safety.setDescription(safetyDesc);
		this.activity.setDescription(activityDesc);
		this.education.setDescription(educationDesc);
		this.guidance.setDescription(guidanceDesc);
		this.responsibility.setDescription(responsibilityDesc);
		this.discipline.setDescription(disciplineDesc);
		this.love.setDescription(loveDesc);
		this.compassion.setDescription(compassionDesc);
		this.joy.setDescription(joyDesc);
		this.hope.setDescription(hopeDesc);
	}
	
	/**
	 * Set the values of the water standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setWater(Long stdStatus, String stdDesc){
		this.water.setStatus(stdStatus);
		this.water.setDescription(stdDesc);
	}

	/**
	 * Set the values of the food standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setFood(Long stdStatus, String stdDesc){
		this.food.setStatus(stdStatus);
		this.food.setDescription(stdDesc);
	}

	/**
	 * Set the values of the shelter standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setShelter(Long stdStatus, String stdDesc){
		this.shelter.setStatus(stdStatus);
		this.shelter.setDescription(stdDesc);
	}

	/**
	 * Set the values of the clothing standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setClothing(Long stdStatus, String stdDesc){
		this.clothing.setStatus(stdStatus);
		this.clothing.setDescription(stdDesc);
	}

	/**
	 * Set the values of the medicine standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setMedicine(Long stdStatus, String stdDesc){
		this.medicine.setStatus(stdStatus);
		this.medicine.setDescription(stdDesc);
	}

	/**
	 * Set the values of the hygiene standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setHygiene(Long stdStatus, String stdDesc){
		this.hygiene.setStatus(stdStatus);
		this.hygiene.setDescription(stdDesc);
	}

	/**
	 * Set the values of the safety standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setSafety(Long stdStatus, String stdDesc){
		this.safety.setStatus(stdStatus);
		this.safety.setDescription(stdDesc);
	}

	/**
	 * Set the values of the activity standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setActivity(Long stdStatus, String stdDesc){
		this.activity.setStatus(stdStatus);
		this.activity.setDescription(stdDesc);
	}

	/**
	 * Set the values of the education standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setEducation(Long stdStatus, String stdDesc){
		this.education.setStatus(stdStatus);
		this.education.setDescription(stdDesc);
	}

	/**
	 * Set the values of the guidance standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setGuidance(Long stdStatus, String stdDesc){
		this.guidance.setStatus(stdStatus);
		this.guidance.setDescription(stdDesc);
	}

	/**
	 * Set the values of the responsibility standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setResponsibility(Long stdStatus, String stdDesc){
		this.responsibility.setStatus(stdStatus);
		this.responsibility.setDescription(stdDesc);
	}

	/**
	 * Set the values of the discipline standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setDiscipline(Long stdStatus, String stdDesc){
		this.discipline.setStatus(stdStatus);
		this.discipline.setDescription(stdDesc);
	}

	/**
	 * Set the values of the love standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setLove(Long stdStatus, String stdDesc){
		this.love.setStatus(stdStatus);
		this.love.setDescription(stdDesc);
	}

	/**
	 * Set the values of the compassion standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setCompassion(Long stdStatus, String stdDesc){
		this.compassion.setStatus(stdStatus);
		this.compassion.setDescription(stdDesc);
	}

	/**
	 * Set the values of the joy standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setJoy(Long stdStatus, String stdDesc){
		this.joy.setStatus(stdStatus);
		this.joy.setDescription(stdDesc);
	}

	/**
	 * Set the values of the hope standard
	 * @param stdStatus
	 * @param stdDesc
	 */
	public void setHope(Long stdStatus, String stdDesc){
		this.hope.setStatus(stdStatus);
		this.hope.setDescription(stdDesc);
	}
	
	/*
	 * Methods to get the information of the standards: status and description
	 */
	public Long getWaterStatus(){
		return this.water.getStatus();
	}
	public String getWaterDescription(){
		return this.water.getDescription();
	}
	public Long getFoodStatus(){
		return this.food.getStatus();
	}
	public String getFoodDescription(){
		return this.food.getDescription();
	}
	public Long getShelterStatus(){
		return this.shelter.getStatus();
	}
	public String getShelterDescription(){
		return this.shelter.getDescription();
	}
	public Long getClothingStatus(){
		return this.clothing.getStatus();
	}
	public String getClothingDescription(){
		return this.clothing.getDescription();
	}
	public Long getMedicineStatus(){
		return this.medicine.getStatus();
	}
	public String getMedicineDescription(){
		return this.medicine.getDescription();
	}
	public Long getHygieneStatus(){
		return this.hygiene.getStatus();
	}
	public String getHygieneDescription(){
		return this.hygiene.getDescription();
	}
	public Long getSafetyStatus(){
		return this.safety.getStatus();
	}
	public String getSafetyDescription(){
		return this.safety.getDescription();
	}
	public Long getActivityStatus(){
		return this.activity.getStatus();
	}
	public String getActivityDescription(){
		return this.activity.getDescription();
	}
	public Long getEducationStatus(){
		return this.education.getStatus();
	}
	public String getEducationDescription(){
		return this.education.getDescription();
	}
	public Long getGuidanceStatus(){
		return this.guidance.getStatus();
	}
	public String getGuidanceDescription(){
		return this.guidance.getDescription();
	}
	public Long getResponsibilityStatus(){
		return this.responsibility.getStatus();
	}
	public String getResponsibilityDescription(){
		return this.responsibility.getDescription();
	}
	public Long getDisciplineStatus(){
		return this.discipline.getStatus();
	}
	public String getDisciplineDescription(){
		return this.discipline.getDescription();
	}
	public Long getLoveStatus(){
		return this.love.getStatus();
	}
	public String getLoveDescription(){
		return this.love.getDescription();
	}
	public Long getCompassionStatus(){
		return this.compassion.getStatus();
	}
	public String getCompassionDescription(){
		return this.compassion.getDescription();
	}
	public Long getJoyStatus(){
		return this.joy.getStatus();
	}
	public String getJoyDescription(){
		return this.joy.getDescription();
	}
	public Long getHopeStatus(){
		return this.hope.getStatus();
	}
	public String getHopeDescription(){
		return this.hope.getDescription();
	}
	
	
	/*
	 * Getting and Setting basic attributes
	 */
	  /**
	   * Gets the uniqueId of the standard. This is the id that will be used by other objects
	   * to refer to an object of this particular class.
	   * 
	   * @param 
	   * @return UniqueId of the organization
	   */
	public String getUniqueId() {
		return uniqueId;
	}

	  /**
	   * Gets the Type of the DB record. 
	   * 
	   * @param 
	   * @return Type of the DB record
	   */
	public Integer getDBType() {
		return this.dbType;
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
	   * Sets the unique Id of the standard. This id is the one used by other objects to refer to 
	   * this organization.
	   * 
	   * @param Unique Id of standard
	   * @return 
	   */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	  /**
	   * Sets the type of the standard. 
	   * 
	   * @param Type of Standard
	   * @return 
	   */
	public void setDBType(Integer dbType) {
		this.dbType = dbType;
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
}
