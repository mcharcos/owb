package com.owb.playhelp.shared.orphanage;


import com.owb.playhelp.shared.DBRecordInfo;
import com.owb.playhelp.shared.StandardInfo;

public class OrphanageInfo extends DBRecordInfo {

	/**
	 * Constructor
	 */
	public OrphanageInfo() {
		super(DBRecordInfo.ORPHANAGE);
	}
	
	/**
	 * Constructor
	 * @param name
	 */
	public OrphanageInfo(String name) {
		this();
		this.setName(name);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param description
	 */
	public OrphanageInfo(String name, String description) {
		this(name);
		this.setDescription(description);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param description
	 * @param addr
	 * @param lat
	 * @param lng
	 */
	public OrphanageInfo(String name, String description,String addr, double lat, double lng) {
		this(name,description);
		this.setAddress(addr, lat, lng);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param description
	 * @param address
	 * @param lat
	 * @param lng
	 * @param phone
	 * @param email
	 * @param website
	 */
	public OrphanageInfo(String name, String description,String address, double lat, double lng, String phone, String email, String website) {
		this(name,description,address,lat,lng);
		this.setPhone(phone);
		this.setEmail(email);
		this.setWebsite(website);
	}
}

