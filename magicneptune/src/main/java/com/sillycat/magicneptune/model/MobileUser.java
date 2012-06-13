package com.sillycat.magicneptune.model;


/**
 * end user from the mobile phone
 * 
 * @author SILLYCAT
 * 
 */
public class MobileUser extends BasicUser {

	private static final long serialVersionUID = -8848726666601787798L;

	private String nickname;
	
	private Integer age;

	/**
	 * 1=active, 2=delete, 4=block, 8=VIP
	 */
	private int status; 
	
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
