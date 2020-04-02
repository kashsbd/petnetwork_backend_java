package com.martersolutions.model;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
public class PetVaccination {
	
	@Id
	private String id;
	
	private String vaccineName;
	
	private String injectionDate;
	
	private String nextInjectionDate;
	
	private String vaccinationNote;
	
	private String petId;
	
	private String vaccinePicId;
	
	private ArrayList<String> allVaccineName=new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getVaccinePicId() {
		return vaccinePicId;
	}

	public void setVaccinePicId(String vaccinePicId) {
		this.vaccinePicId = vaccinePicId;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getInjectionDate() {
		return injectionDate;
	}

	public void setInjectionDate(String injectionDate) {
		this.injectionDate = injectionDate;
	}

	public String getNextInjectionDate() {
		return nextInjectionDate;
	}

	public void setNextInjectionDate(String nextInjectionDate) {
		this.nextInjectionDate = nextInjectionDate;
	}

	public String getVaccinationNote() {
		return vaccinationNote;
	}

	public void setVaccinationNote(String vaccinationNote) {
		this.vaccinationNote = vaccinationNote;
	}

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

	public ArrayList<String> getAllVaccineName() {
		return allVaccineName;
	}

	public void setAllVaccineName(ArrayList<String> allVaccineName) {
		this.allVaccineName = allVaccineName;
	}
	
	
}
