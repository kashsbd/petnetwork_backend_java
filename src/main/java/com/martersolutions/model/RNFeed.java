package com.martersolutions.model;

/**
 * @author prakash
 * 
 *         May 31, 2018
 * 
 *         Return type of Feed
 */

import java.util.ArrayList;
import java.util.List;

public class RNFeed {

	private String feedId;

	private String ownerId;

	private String ownerName;

	private String proPicId;

	private String activity;

	private String hashtags;

	private String petsName;

	private String status;

	private List<RNMedia> media = new ArrayList<RNMedia>();

	private List<String> likes = new ArrayList<String>();

	private List<String> comments = new ArrayList<String>();

	private String createdDate;

	private String editedDate;

	public RNFeed() {

	}

	public RNFeed(String feedId, String ownerId, String ownerName, String proPicId, String activity, String hashtags,
			String petName, String status, List<RNMedia> media, List<String> likes, List<String> comments,
			String createdDate, String editedDate) {
		this.feedId = feedId;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.proPicId = proPicId;
		this.activity = activity;
		this.hashtags = hashtags;
		this.petsName = petName;
		this.status = status;
		this.media = media;
		this.likes = likes;
		this.comments = comments;
		this.createdDate = createdDate;
		this.editedDate = editedDate;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getProPicId() {
		return proPicId;
	}

	public void setProPicId(String proPicId) {
		this.proPicId = proPicId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	public String getPetsName() {
		return petsName;
	}

	public void setPetsName(String petsName) {
		this.petsName = petsName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RNMedia> getMedia() {
		return media;
	}

	public void setMedia(List<RNMedia> media) {
		this.media = media;
	}

	public List<String> getLikes() {
		return likes;
	}

	public void setLikes(List<String> likes) {
		this.likes = likes;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getEditedDate() {
		return editedDate;
	}

	public void setEditedDate(String editedDate) {
		this.editedDate = editedDate;
	}
}
