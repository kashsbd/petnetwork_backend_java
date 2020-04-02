package com.martersolutions.model;

public class RNVaccination {
	
	private String vaccinePicId;
	
	private String vaccineName;
	
	private String currentInjectionDate;
	
	private String nextInjectionDate;
	
	private String vaccinationNotes;
	
	public RNVaccination() {
		
	}

	public RNVaccination(String vaccinePicId, String vaccineName, String currentInjectionDate, String nextInjectionDate,
			String vaccinationNotes) {
		super();
		this.vaccinePicId = vaccinePicId;
		this.vaccineName = vaccineName;
		this.currentInjectionDate = currentInjectionDate;
		this.nextInjectionDate = nextInjectionDate;
		this.vaccinationNotes = vaccinationNotes;
	}

	@Override
	public String toString() {
		return "RNVaccination [vaccinePicId=" + vaccinePicId + ", vaccineName=" + vaccineName
				+ ", currentInjectionDate=" + currentInjectionDate + ", nextInjectionDate=" + nextInjectionDate
				+ ", vaccinationNotes=" + vaccinationNotes + "]";
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

	public String getCurrentInjectionDate() {
		return currentInjectionDate;
	}

	public void setCurrentInjectionDate(String currentInjectionDate) {
		this.currentInjectionDate = currentInjectionDate;
	}

	public String getNextInjectionDate() {
		return nextInjectionDate;
	}

	public void setNextInjectionDate(String nextInjectionDate) {
		this.nextInjectionDate = nextInjectionDate;
	}

	public String getVaccinationNotes() {
		return vaccinationNotes;
	}

	public void setVaccinationNotes(String vaccinationNotes) {
		this.vaccinationNotes = vaccinationNotes;
	}
	
	

}
