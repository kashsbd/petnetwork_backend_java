package com.martersolutions.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prakash
 * 
 *         May 31, 2018
 * 
 *         Return type of comment
 */
public class RNComment {

	private String commentId;

	private String commentorId;

	private String commentorName;

	private String createdDate;

	private String editedDate;

	private String message;

	private List<String> likes = new ArrayList<String>();

	private List<String> replies = new ArrayList<String>();

	public RNComment() {

	}

	public RNComment(String commentId, String commentorId, String commentorName, String createdDate, String editedDate,
			String message, List<String> likes) {

		this.commentId = commentId;
		this.commentorId = commentorId;
		this.commentorName = commentorName;
		this.message = message;
		this.likes = likes;
		this.createdDate = createdDate;
		this.editedDate = editedDate;
	}

	public RNComment(String commentId, String commentorId, String commentorName, String createdDate, String editedDate,
			String message, List<String> likes, List<String> replies) {

		this.commentId = commentId;
		this.commentorId = commentorId;
		this.commentorName = commentorName;
		this.message = message;
		this.likes = likes;
		this.replies = replies;
		this.createdDate = createdDate;
		this.editedDate = editedDate;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentorId() {
		return commentorId;
	}

	public void setCommentorId(String commentorId) {
		this.commentorId = commentorId;
	}

	public String getCommentorName() {
		return commentorName;
	}

	public void setCommentorName(String commentorName) {
		this.commentorName = commentorName;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getLikes() {
		return likes;
	}

	public void setLikes(List<String> likes) {
		this.likes = likes;
	}

	public List<String> getReplies() {
		return replies;
	}

	public void setReplies(List<String> replies) {
		this.replies = replies;
	}

}
