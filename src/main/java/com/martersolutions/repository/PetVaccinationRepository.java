package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.PetVaccination;

public interface PetVaccinationRepository extends MongoRepository<PetVaccination,String> {
	
}
