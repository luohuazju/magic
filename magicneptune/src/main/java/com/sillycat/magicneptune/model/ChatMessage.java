package com.sillycat.magicneptune.model;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {

	private static final long serialVersionUID = 1437240642374550072L;

	private String id;

	private String content;

	/**
	 * 1=text, 2=picture, 4=audio, 8=video
	 */
	private String type;

	private String desn;

	private Date gmtCreate;

	private Date gmtUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
