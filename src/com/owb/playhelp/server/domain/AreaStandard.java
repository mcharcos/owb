package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.shared.AreaStandardInfo;

/*@PersistenceCapable
@EmbeddedOnly*/
public class AreaStandard {
	
	//private static final long serialVersionUID = -2023204547641864687L;
	protected static final Logger log = Logger.getLogger(Utils.class.getName());
	
	/*
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key AnotherClassId;*/
	
	public AreaStandard(){
		
	}

	public AreaStandard(Long stdStatus, String stdDesc){
		this.add(stdStatus, stdDesc);
	}
	
	public AreaStandard(AreaStandardInfo std){
		this.add(std);
	}
	

    @Persistent(defaultFetchGroup = "true")
    private List<Date> stdDate = new ArrayList<Date>();
    @Persistent(defaultFetchGroup = "true")
    private List<Long> stdStatus = new ArrayList<Long>();
    @Persistent(defaultFetchGroup = "true")
	private List<Text> stdDesc = new ArrayList<Text>();

	public void add(Long stdStatus, String stdDesc){
		this.stdDate.add(new Date());
		this.stdStatus.add(stdStatus);
		this.stdDesc.add(new Text(stdDesc));
	}
	
	public void add(Long stdStatus, String stdDesc, Date date){
		this.stdDate.add(date);
		this.stdStatus.add(stdStatus);
		this.stdDesc.add(new Text(stdDesc));
	}
	
	public void add(AreaStandardInfo std){
		if (std.getDate() == null) this.add(std.getStatus(),std.getDescription()); 
		else this.add(std.getStatus(),std.getDescription(),std.getDate());
	}

	/**
	 * Get all the dates in the object
	 * @return List<Date>
	 */
	public List<Date> getDateList(){
		return stdDate;
	}

	/**
	 * Get all the Status in the object
	 * @return List<Long>
	 */
	public List<Long> getStatusList(){
		return stdStatus;
	}

	/**
	 * Get all the descriptions in the object
	 * @return List<String>
	 */
	public List<Text> getDescriptionList(){
		return stdDesc;
	}
	
	/**
	 * 
	 * @param idx
	 * @return Date for index i
	 */
	public Date getDate(int idx){
		if (idx < 0){
			log.info("Index should be positive.");
			return null;
		}
		if (idx >= stdDate.size()){
			log.info("Index is too high ("+Integer.toString(idx)+").");
			return null;
		}
		return stdDate.get(idx);
	}

	/**
	 * 
	 * @param idx
	 * @return Date for index i
	 */
	public Long getStatus(int idx){
		if (idx < 0){
			log.info("Index should be positive.");
			return null;
		}
		if (idx >= stdDate.size()){
			log.info("Index is too high ("+Integer.toString(idx)+").");
			return null;
		}
		return stdStatus.get(idx);
	}

	/**
	 * 
	 * @param idx
	 * @return Date for index i
	 */
	public Text getDescription(int idx){
		if (idx < 0){
			log.info("Index should be positive.");
			return null;
		}
		if (idx >= stdDate.size()){
			log.info("Index is too high ("+Integer.toString(idx)+").");
			return null;
		}
		return stdDesc.get(idx);
	}
	

	/*
	 * Get values of standard at a specific date or latest one
	 */
	/**
	 * Get the index of the list that correspond to the values of the standard
	 * at a given date. This is the closest date before the input date.
	 * @param date
	 * @return
	 */
	public int getIndex(Date date){
		if (stdDate.size() == 0) return -1;
		if (date == null) return stdDate.size()-1;
		return stdDate.size()-1;
	}
	/**
	 * 
	 * @param 
	 * @return Date for last index
	 */
	public Date getLastDate(){
		if (stdDate.size() == 0) return null;
		return stdDate.get(stdDate.size()-1);
	}
	/**
	 * 
	 * @param 
	 * @return Date of last entry before the input date
	 */
	public Date getDate(Date date){
		if (this.getIndex(date) == -1) return null;
		return stdDate.get(this.getIndex(date));
	}

	/**
	 * 
	 * @param 
	 * @return Status for last index
	 */
	public Long getLastStatus(){
		if (stdStatus.size() == 0) return null;
		return stdStatus.get(stdStatus.size());
	}
	/**
	 * 
	 * @param 
	 * @return Status of last entry before the input date
	 */
	public Long getStatus(Date date){
		if (this.getIndex(date) == -1) return null;
		return stdStatus.get(this.getIndex(date));
	}

	/**
	 * 
	 * @param 
	 * @return Description for last index
	 */
	public Text getLastDescription(){
		if (stdDesc.size() == 0) return null;
		return stdDesc.get(stdDesc.size());
	}
	/**
	 * 
	 * @param 
	 * @return Description of last entry before the input date
	 */
	public Text getDescription(Date date){
		if (this.getIndex(date) == -1) return null;
		return stdDesc.get(this.getIndex(date));
	}
	
	
}
