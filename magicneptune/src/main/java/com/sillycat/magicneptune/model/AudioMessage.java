package com.sillycat.magicneptune.model;

public class AudioMessage extends ChatMessage {

	private static final long serialVersionUID = 3590206062600569992L;

	private String audiourl;

	private int filesize;

	/**
	 * MP3, WMW
	 */
	private String filetype;

	public String getAudiourl() {
		return audiourl;
	}

	public void setAudiourl(String audiourl) {
		this.audiourl = audiourl;
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
