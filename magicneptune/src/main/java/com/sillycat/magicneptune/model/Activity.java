package com.sillycat.magicneptune.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * activity model
 * @author SILLYCAT
 *
 */
public class Activity implements Serializable {

	private static final long serialVersionUID = 5070127685969943675L;

	private String id;

	private String title;

	private String content;

	private String desn;

	private List<String> images;

	private Date gmtCreate;
	
	private Date gmtUpdate;
	
	/**
	 * 1=activity, 2=delete, 4=block, 8=hot 
	 */
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDesn() {
		return desn;
	}

	public void setDesn(String desn) {
		this.desn = desn;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
