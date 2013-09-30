package com.owb.playhelp.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Miguel Charcos Llorens
 * 
**/

@SuppressWarnings("serial") 
public class AreaStandardInfo implements Serializable {
	private static final long serialVersionUID = -2023204547641864687L;
	
	public AreaStandardInfo(){
		
	}
	public AreaStandardInfo(Long stdStatus, String stdDesc){
		this.stdDate = null;
		this.stdStatus = stdStatus;
		this.stdDesc = stdDesc;
		checkRange();
	}
	public AreaStandardInfo(Long stdStatus, String stdDesc,Date date){
		this(stdStatus,stdDesc);
		stdDate = null;
	}
	
    private Date stdDate;
    private Long stdStatus;
	private String stdDesc;

	private void checkRange(){
		if (this.stdStatus < 0L) this.stdStatus=0L;
		if (this.stdStatus > 100L) this.stdStatus=100L;
	}
	
	/**
	 * Return the description of the standard
	 * @return Description
	 */
	public String getDescription(){
		return this.stdDesc;
	}
	
	/**
	 * Set the description of the standard
	 * @param stdDesc
	 */
	public void setDescription(String stdDesc){
		this.stdDesc = stdDesc;
	}
	
	/**
	 * Get the status number of the standard
	 * @return Status
	 */
	public Long getStatus(){
		return this.stdStatus;
	}
	
	/**
	 * Set the status of the standard
	 * @param status
	 */
	public void setStatus(Long status){
		this.stdStatus = status;
		checkRange();
	}
	
	/**
	 * Get the Date of the standard
	 * @return date
	 */
	public Date getDate(){
		return this.stdDate;
	}
	
	/**
	 * Set the Date of the standard
	 * @param stdDate
	 */
	public void setDate(Date stdDate){
		this.stdDate = stdDate;
	}
	
	/**
	 * Set the date of the standard to the current date.
	 */
	public void setCurrent(){
		this.stdDate = new Date();
	}
	
}
