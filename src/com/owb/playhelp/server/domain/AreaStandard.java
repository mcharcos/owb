package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;
import com.owb.playhelp.server.utils.Utils;

public class AreaStandard implements Serializable {
	
	private static final long serialVersionUID = -2023204547641864687L;
	protected static final Logger log = Logger.getLogger(Utils.class.getName());
	
	public AreaStandard(){
		
	}

	public AreaStandard(Long stdStatus, String stdDesc){
		this.add(stdStatus, stdDesc);
	}
	

    @Persistent
    private List<Date> stdDate = new ArrayList<Date>();
	@Persistent
    private List<Long> stdStatus = new ArrayList<Long>();
	@Persistent
	private List<Text> stdDesc = new ArrayList<Text>();
	
	public void add(Long stdStatus, String stdDesc){
		this.stdDate.add(new Date());
		this.stdStatus.add(stdStatus);
		this.stdDesc.add(new Text(stdDesc));
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
}
