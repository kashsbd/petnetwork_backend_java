/**
 * 
 */
package com.martersolutions.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prakash Jun 18, 2018
 */
public class RNArticle {

	private String articleId;

	private String ownerId;

	private String proPicId;

	private String title;

	private String description;

	private String createdDate;

	private String editedDate;

	private RNMedia media;

	private List<String> likes = new ArrayList<String>();

	private List<String> comments = new ArrayList<String>();

	public RNArticle() {

	}

	public RNArticle(String articleId, String ownerId, String proPicId, String title, String description,
			String createdDate, String editedDate, RNMedia media, List<String> likes, List<String> comments) {

		this.articleId = articleId;
		this.ownerId = ownerId;
		this.proPicId = proPicId;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.editedDate = editedDate;
		this.media = media;
		this.likes = likes;
		this.comments = comments;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getProPicId() {
		return proPicId;
	}

	public void setProPicId(String proPicId) {
		this.proPicId = proPicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public RNMedia getMedia() {
		return media;
	}

	public void setMedia(RNMedia media) {
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

}
