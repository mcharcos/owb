/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.ngo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.UUID;

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

import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.ngo.NgoInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Ngo implements Serializable, Cacheable {

	  private static final Logger log = Logger.getLogger(Utils.class.getName());
	  private static final int NUM_RETRIES = 5; 
	  
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String description;
	
	@Persistent
	private String address;

	@Persistent
	private double lat, lng;
	
	@Persistent
	private String phone;

	@Persistent
	private String email;

	@Persistent
	private String website;

	@Persistent
	private String uniqueId;

	@Persistent
	private Set<String> members = new HashSet<String>();

	@Persistent
	private Set<String> followers = new HashSet<String>();

	//@Persistent(mappedBy = "userPrefs")
	//@Element(dependent = "true")
	//private Set<FriendItem> friends = new HashSet<FriendItem>();

	public Ngo() {
	}

	public Ngo(NgoInfo ngoInfo) {
		this();
		this.setName(ngoInfo.getName());
		this.setDescription(ngoInfo.getDescription());
		this.setAddress(ngoInfo.getAddress());
		this.setLatitude(ngoInfo.getLatitude());
		this.setLongitude(ngoInfo.getLongitude());
		this.setPhone(ngoInfo.getPhone());
		this.setEmail(ngoInfo.getEmail());
		this.setWebsite(ngoInfo.getWebsite());
		this.setUniqueId(ngoInfo.getUniqueId());
		
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();  //this.getEmail();
		} 
	}

	public void reEdit(NgoInfo ngoInfo) {
		
		/*String ngoId = uniqueId;  //this.getUniqueId();
		String ngoInfoId = ngoInfo.getUniqueId();
		boolean tst = ngoId.toString().equals(ngoInfoId.toString());
		if (!tst){
			return;
		}*/
		
		this.setName(ngoInfo.getName());
		this.setDescription(ngoInfo.getDescription());
		this.setName(ngoInfo.getName());
		this.setAddress(ngoInfo.getAddress());
		this.setLatitude(ngoInfo.getLatitude());
		this.setLongitude(ngoInfo.getLongitude());
		this.setPhone(ngoInfo.getPhone());
		this.setEmail(ngoInfo.getEmail());
		this.setWebsite(ngoInfo.getWebsite());	
	}
	
	public static NgoInfo toInfo(Ngo o) {
		if (o == null)
			return null;

		NgoInfo oInfo = new NgoInfo(o.getName(), o.getDescription(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
		oInfo.setUniqueId(o.getUniqueId());
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		oInfo.deactivateMember();
		oInfo.deactivateFollower();
		
		return oInfo;
	}

	public static NgoInfo toInfo(Ngo o, String userUniqueId) {
		if (o == null)
			return null;

		NgoInfo oInfo = Ngo.toInfo(o);
		
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		if (o.isMember(userUniqueId)) oInfo.activateMember();
		if (o.isFollower(userUniqueId)) oInfo.activateFollower();
		
		return oInfo;
	}
	
	  // Retrieve the user from the database if it already exist or
	  // create a new account if it is the first loggin
	  public static Ngo findOrCreateNgo(Ngo ngo) {
	
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Ngo oneResult = null, detached = null;
	
	    String uniqueId = ngo.getUniqueId();
	
	    Query q = pm.newQuery(Ngo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Ngo) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("UserProfile " + uniqueId + " does not exist, creating...");
	          // Create friends from Google+
	          //user.setKarma(new Karma());
	          pm.makePersistent(ngo);
	          detached = pm.detachCopy(ngo);
	        }
	        try {
	          tx.commit();
	          break;
	        }
	        catch (JDOCanRetryException e1) {
	          if (i == (NUM_RETRIES - 1)) { 
	            throw e1;
	          }
	        }
	      } // end for
	    } catch (JDOUserException e){
	          log.info("JDOUserException: UserProfile table is empty");
	          // Create friends from Google+
	          pm.makePersistent(ngo);
	          detached = pm.detachCopy(ngo);	    	
		        try {
			          tx.commit();
			        }
			        catch (JDOCanRetryException e1) {
			        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    finally {
	      if (tx.isActive()) {
	        tx.rollback();
	      }
	      pm.close();
	      q.closeAll();
	    }
	    
	    return detached;
	  }
	
	public boolean isMember(String userUniqueId){
		return members.contains(userUniqueId);
	}
	
	public boolean isFollower(String userUniqueId){
		return followers.contains(userUniqueId);
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
	public String getDescription() {
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
	public String getPhone() {
		return this.phone;
	}
	public String getEmail() {
		return this.email;
	}
	public String getWebsite() {
		return this.website;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
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
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setWebsite(String website) {
		this.website = website;
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
	
	public void addMember(String member){
		// Check if the member exist
		if (members.contains(member)) return;
		
		// Add it if it does not
		members.add(member);
	}
	
	public Set<String> getMembers(){
		return members;
	}
	
	public void addFollowers(String follower){
		// Check if the member exist
		if (followers.contains(follower)) return;
		
		// Add it if it does not
		followers.add(follower);
	}
	
	public Set<String> getFollowers(){
		return followers;
	}
}