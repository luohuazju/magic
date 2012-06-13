package com.sillycat.magicneptune.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author SILLYCAT
 *
 */
public class BasicObject implements Serializable {

	private static final long serialVersionUID = 170246124791493329L;

	private String id;
	
	private String desn;
	
	private Date gmtCreate;
	
	private Date gmtUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesn() {
		return desn;
	}

	public void setDesn(String desn) {
		this.desn = desn;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}
	
}
