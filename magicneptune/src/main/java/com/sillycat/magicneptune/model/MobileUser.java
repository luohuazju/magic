package com.sillycat.magicneptune.model;

import java.io.Serializable;
import java.util.Date;

/**
 * end user from the mobile phone
 * 
 * @author SILLYCAT
 * 
 */
public class MobileUser implements Serializable {

	private static final long serialVersionUID = -8848726666601787798L;

	private String id;

	private String username;
	
	private String nickname;
	
	private String password;

	private Integer age;

	private String email;

	private String mobile;

	private String desn;

	private Date gmtCreate;

	private Date gmtUpdate;
	
	/**
	 * 1=active, 2=delete, 4=block, 8=VIP
	 */
	private int status; 
	
	/**
	 * 1=online, 2=off
	 */
	private int online;
	
	
	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
