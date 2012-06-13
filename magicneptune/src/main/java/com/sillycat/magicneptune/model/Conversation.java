package com.sillycat.magicneptune.model;

import java.util.List;


/**
 * conversation
 * @author SILLYCAT
 *
 */
public class Conversation extends BasicObject {

	private static final long serialVersionUID = -7159046904962605198L;

	private String topic;
	
	/**
	 * 1=active, 2=delete
	 */
	private int status;
	
	/**
	 * users in this conversation
	 */
	List<BasicUser> users;
	
	/**
	 * all the messages in this conversation
	 */
	List<ChatMessage> messages;
	
	public List<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}

	public List<BasicUser> getUsers() {
		return users;
	}

	public void setUsers(List<BasicUser> users) {
		this.users = users;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
