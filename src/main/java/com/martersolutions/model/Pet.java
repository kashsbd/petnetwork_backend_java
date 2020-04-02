package com.martersolutions.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Pet")
public class Pet {

	@Id
	private String id;

	private String ownerId;
	
	private String petType;
	
	private String petName;
	
	private String petAge;

	private String petBirthDate;

	private String petGender;

	private String petWeight;

	private String petDescription;
	
	private String petProPicId;
	
	private ArrayList<String> vaccinationsId=new ArrayList<String>();
	
	private ArrayList<String> media = new ArrayList<String>();
	
	
	public Pet() {

	}	

	public String getProPicId() {
		return petProPicId;
	}

	public void setProPicId(String petProPicId) {
		this.petProPicId = petProPicId;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetBirthDate() {
		return petBirthDate;
	}

	public void setPetBirthDate(String petBirthDate) {
		this.petBirthDate = petBirthDate;
	}

	public String getPetGender() {
		return petGender;
	}

	public void setPetGender(String petGender) {
		this.petGender = petGender;
	}

	public String getPetWeight() {
		return petWeight;
	}

	public void setPetWeight(String petWeight) {
		this.petWeight = petWeight;
	}

	public String getPetDescription() {
		return petDescription;
	}

	public void setPetDescription(String petDescription) {
		this.petDescription = petDescription;
	}

	public ArrayList<String> getMedia() {
		return media;
	}

	public ArrayList<String> getVaccinationsId() {
		return vaccinationsId;
	}

	public void setVaccinationsId(ArrayList<String> vaccinationsId) {
		this.vaccinationsId = vaccinationsId;
	}

	public void setMedia(ArrayList<String> media) {
		this.media = media;
	}

	public String getPetAge() {
		return petAge;
	}

	public void setPetAge(String petAge) {
		this.petAge = petAge;
	}



}
