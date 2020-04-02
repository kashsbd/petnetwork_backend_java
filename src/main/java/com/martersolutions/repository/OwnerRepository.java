package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.Owner;

public interface OwnerRepository extends MongoRepository<Owner,String>{

}
