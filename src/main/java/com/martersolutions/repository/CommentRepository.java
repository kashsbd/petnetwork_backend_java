package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
