package com.sillycat.magicneptune.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * activity model
 * @author SILLYCAT
 *
 */
public class Activity extends BasicObject {

	private static final long serialVersionUID = 5070127685969943675L;

	private String title;

	private String content;

	private List<String> images;

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

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
