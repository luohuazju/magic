package com.sillycat.magicneptune.model;


public class ChatMessage extends BasicObject {

	private static final long serialVersionUID = 1437240642374550072L;

	private String content;

	/**
	 * 1=text, 2=picture, 4=audio, 8=video
	 */
	private String type;
	
	/**
	 * message send from this user, maybe a merchant or mobile
	 */
	private BasicUser fromuser;
	
	/**
	 * message send to this user, maybe a merchant or mobile
	 * if this is null, means to all the users in conversation(multiple user in the future)
	 */
	private BasicUser touser;
	
	public BasicUser getFromuser() {
		return fromuser;
	}

	public void setFromuser(BasicUser fromuser) {
		this.fromuser = fromuser;
	}

	public BasicUser getTouser() {
		return touser;
	}

	public void setTouser(BasicUser touser) {
		this.touser = touser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
