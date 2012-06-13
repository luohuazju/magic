package com.sillycat.magicneptune.model;

public class TextMessage extends ChatMessage {

	private static final long serialVersionUID = -7277130411206544533L;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
