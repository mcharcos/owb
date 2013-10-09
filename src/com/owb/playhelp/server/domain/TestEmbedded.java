package com.owb.playhelp.server.domain;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.shared.AreaStandardInfo;

@PersistenceCapable(detachable = "true")
@EmbeddedOnly
public class TestEmbedded {
	
	//private static final long serialVersionUID = -2023204547641864687L;
	protected static final Logger log = Logger.getLogger(Utils.class.getName());
	
	public TestEmbedded(){
		this.test = 1L;
	}
	

    @Persistent  
    private Long test;

    public void set(Long test){
    	this.test = test;
    }
    public Long get(){
    	return this.test;
    }
	
}
