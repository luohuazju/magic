package com.sillycat.magicneptune.model;

public class BasicUser extends BasicObject{

	private static final long serialVersionUID = -5019720809497211122L;
	
	private String username;
	
	private String password;
	
	/**
	 * 1=online, 2=off
	 */
	private int online;
	
	private String email;

	private String mobile;
	
	/**
	 * 1=mobile user;2=merchant user
	 */
	private String type;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
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
	
}
