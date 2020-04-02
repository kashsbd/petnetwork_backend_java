package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.Feed;

public interface FeedRepository extends MongoRepository<Feed, String> {

}
