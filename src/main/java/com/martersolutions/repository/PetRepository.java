package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.Pet;

public interface PetRepository extends MongoRepository<Pet, String>{

}
