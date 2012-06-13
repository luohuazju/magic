package com.sillycat.magicneptune.model;

import java.io.Serializable;
import java.util.Date;


/**
 * shop
 * @author SILLYCAT
 *
 */
public class MerchantUser implements Serializable {

	private static final long serialVersionUID = -7621607465147135984L;

	private String id;
	
	private String username;
	
	private String password;
	
	private String merchantname;
	
	private String mobile;
	
	private String website;
	
	private String desn;
	
	private String email;
	
	private Date gmtCreate;
	
	private Date gmtUpdate;
	
	/**
	 * 1=online, 2=off
	 */
	private int online;
	
	/**
	 * 1=active, 2=delete, 4=block, 8=VIP, 16=candidate
	 */
	private int status;
	
	
	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDesn() {
		return desn;
	}

	public void setDesn(String desn) {
		this.desn = desn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	} 
	
	
}
