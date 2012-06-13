package com.sillycat.magicneptune.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * conversation
 * @author SILLYCAT
 *
 */
public class Conversation implements Serializable {

	private static final long serialVersionUID = -7159046904962605198L;

	private String id;
	
	private String topic;
	
	private Date gmtCreate;
	
	private Date gmtUpdate;
	
	/**
	 * 1=active, 2=delete
	 */
	private int status;
	
	private List<MobileUser> mobileusers;
	
	private List<MerchantUser> merchants;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
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

	public List<MobileUser> getMobileusers() {
		return mobileusers;
	}

	public void setMobileusers(List<MobileUser> mobileusers) {
		this.mobileusers = mobileusers;
	}

	public List<MerchantUser> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<MerchantUser> merchants) {
		this.merchants = merchants;
	}
	
}
