package com.martersolutions.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Status")
public class Status {

	@Id
	private String id;

	private String textStatus;

	private String stickerStatus;

	private String gifStatus;

	public Status() {
	}

	public String getTextStatus() {
		return textStatus;
	}

	public void setTextStatus(String textStatus) {
		this.textStatus = textStatus;
	}

	public String getStickerStatus() {
		return stickerStatus;
	}

	public void setStickerStatus(String stickerStatus) {
		this.stickerStatus = stickerStatus;
	}

	public String getGifStatus() {
		return gifStatus;
	}

	public void setGifStatus(String gifStatus) {
		this.gifStatus = gifStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
