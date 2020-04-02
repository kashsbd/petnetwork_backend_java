package com.martersolutions.model;

/**
 * @author prakash Jun 2, 2018
 */
public class RNMedia {
	private String contentType;
	private String mediaId;

	public RNMedia() {

	}

	public RNMedia(String contentType, String mediaId) {
		this.contentType = contentType;
		this.mediaId = mediaId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
