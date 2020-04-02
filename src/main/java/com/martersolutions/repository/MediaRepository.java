package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.Media;

public interface MediaRepository extends MongoRepository<Media, String> {

}
