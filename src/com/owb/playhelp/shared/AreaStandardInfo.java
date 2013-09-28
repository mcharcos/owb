package com.owb.playhelp.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

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
		this.add(stdStatus, stdDesc);
	}
	

    @Persistent
    private Set<Date> stdDate = new HashSet<Date>();
	@Persistent
    private Set<Long> stdStatus = new HashSet<Long>();
	@Persistent
	private Set<Text> stdDesc = new HashSet<Text>();
	
	public void add(Long stdStatus, String stdDesc){
		this.stdDate.add(new Date());
		this.stdStatus.add(stdStatus);
		this.stdDesc.add(new Text(stdDesc));
	}
}
