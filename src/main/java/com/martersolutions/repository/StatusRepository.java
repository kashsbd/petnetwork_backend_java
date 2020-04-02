package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.Status;

public interface StatusRepository extends MongoRepository<Status, String> {

}
