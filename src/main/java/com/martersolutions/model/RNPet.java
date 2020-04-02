package com.martersolutions.model;

public class RNPet {
	
	private String petId;
	
	private String petType;
	
	private String petName;
	
	private String petAge;
	
	private String petDob;
	
	private String petGender;
	
	private String  petWeight;
	
	private String petDescription;
	
	private String petProPic;
	
	public RNPet() {
		
	}
	
	@Override
	public String toString() {
		return "RNPet [petId=" + petId + ", petType=" + petType + ", petName=" + petName + ", petAge=" + petAge
				+ ", petDob=" + petDob + ", petGender=" + petGender + ", petWeight=" + petWeight + ", petDescription="
				+ petDescription + ", petProPic=" + petProPic + "]";
	}

	public RNPet(String id, String petType, String petName,String petAge, String petDob, String petGender, String petWeight,
			String petDescription, String petProPic) {
		super();
		this.petId = id;
		this.petType = petType;
		this.petName = petName;
		this.petAge=petAge;
		this.petDob = petDob;
		this.petGender = petGender;
		this.petWeight = petWeight;
		this.petDescription = petDescription;
		this.petProPic = petProPic;
	}

	public String getId() {
		return petId;
	}

	public void setId(String id) {
		this.petId = id;
	}

	public String getPetProPic() {
		return petProPic;
	}

	public void setPetProPic(String petProPic) {
		this.petProPic = petProPic;
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

	public String getPetDob() {
		return petDob;
	}

	public void setPetDob(String petDob) {
		this.petDob = petDob;
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

	public String getPetAge() {
		return petAge;
	}

	public void setPetAge(String petAge) {
		this.petAge = petAge;
	}
	
}
