package com.martersolutions.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Owner")
public class Owner {

	@Id
	private String id;

	private String firstName;

	private String lastName;

	private String country;

	private String city;

	private String job;

	private String dob;

	private String phNo;

	private String gender;

	private String description;

	private String createdDate;

	private String editedDate;

	private String credentialId;

	private String profilePicId;

	private List<String> pets = new ArrayList<String>();

	private List<String> feeds = new ArrayList<String>();

	private List<String> articles = new ArrayList<String>();

	private ArrayList<String> followerLists = new ArrayList<String>();

	private ArrayList<String> followingLists = new ArrayList<String>();

	public Owner() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getProfilePicId() {
		return profilePicId;
	}

	public void setProfilePicId(String profilePicId) {
		this.profilePicId = profilePicId;
	}

	public List<String> getPets() {
		return pets;
	}

	public void setPets(List<String> pets) {
		this.pets = pets;
	}

	public List<String> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}

	public List<String> getArticles() {
		return articles;
	}

	public void setArticles(List<String> articles) {
		this.articles = articles;
	}

	public ArrayList<String> getFollowerLists() {
		return followerLists;
	}

	public void setFollowerLists(ArrayList<String> followerLists) {
		this.followerLists = followerLists;
	}

	public ArrayList<String> getFollowingLists() {
		return followingLists;
	}

	public void setFollowingLists(ArrayList<String> followingLists) {
		this.followingLists = followingLists;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
