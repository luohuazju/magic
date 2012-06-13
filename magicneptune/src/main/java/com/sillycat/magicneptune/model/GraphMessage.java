package com.sillycat.magicneptune.model;

public class GraphMessage extends ChatMessage {

	private static final long serialVersionUID = -6608185124392140172L;

	private String imageurl;

	private int filesize;

	/**
	 * JPG, GIF, PNG
	 */
	private String filetype;

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

}
