/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared.project;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import com.owb.playhelp.shared.ResourceInfo;
import com.owb.playhelp.shared.ChapterInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

@SuppressWarnings("serial") public class ProjectInfo implements Serializable {

	private String id;
	private String name;
	private String description;
	private String status;
	private String userId;
	private Date creationDate;
	private ChapterInfo chapter;
	private NgoInfo ngo;
	private OrphanageInfo orphanage;
	private Set<ResourceInfo> needs = new HashSet<ResourceInfo>();
	private Set<ResourceInfo> contributions = new HashSet<ResourceInfo>();

	public ProjectInfo() {
	}
	
	public ProjectInfo(String name) {
		this.name = name;
	}
	
	public ProjectInfo(String name, String description) {
		this(name);
		this.description = description;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getStatus() {
		return this.status;
	}

	public String getUserId() {
		return this.userId;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public ChapterInfo getChapter(){
		return this.chapter;
	}
	
	public OrphanageInfo getOrphanage(){
		return this.orphanage;
	}
	
	public NgoInfo getNgo(){
		return this.ngo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setNgo(NgoInfo ngo){
		this.ngo = ngo;
	}
	
	public void setChapter(ChapterInfo chapter){
		this.chapter = chapter;
	}
	
	public void setOrphanage(OrphanageInfo orphanage){
		this.orphanage = orphanage;
	}
	
	public void addNeed(ResourceInfo need){
		needs.add(need);
	}
	public Set<ResourceInfo> getNeeds(){
		return needs;
	}
	
	public void addContribution(ResourceInfo contribution){
		this.contributions.add(contribution);
	}
	
	public Set<ResourceInfo> getContributions(){
		return this.contributions;
	}
}
