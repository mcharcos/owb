/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.project;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.project.ProjectInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Project implements Serializable, Cacheable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String description;

	@Persistent
	private Date creationDate;
	
	@Persistent
	private String userId; 

	//@Persistent(mappedBy = "pNews")
	//@Element(dependent = "true")
	//private Set<ProjectNews> news = new HashSet<ProjectNews>();

	//@Persistent(mappedBy = "pNeeds")
	//@Element(dependent = "true")
	//private Set<ProjectNeed> needs = new HashSet<ProjectNeed>();


	public Project(String userId) {
		Date currentDate = new Date();
		
		this.userId = userId;
		this.creationDate = currentDate;
	}
	public Project(String userId, ProjectInfo projectInfo) {
		this(userId);
		this.setName(projectInfo.getName());
		this.setDescription(projectInfo.getDescription());
	}
	public Project(String userId, ProjectItem projectItem) {
		this(userId);
		this.setName(projectItem.getTitle());
		this.setDescription(projectItem.getDescription());		
	}


	public static ProjectInfo toInfo(Project o) {
		if (o == null)
			return null;

		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setName(o.getName());
		projectInfo.setDescription(o.getDescription());
		projectInfo.setUserId(o.getUserId());
		projectInfo.setCreationDate(o.getCreationDate());
		
		return projectInfo;
	}

	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}	
	
	
	
}