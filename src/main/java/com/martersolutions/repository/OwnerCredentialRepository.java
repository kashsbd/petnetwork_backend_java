package com.martersolutions.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martersolutions.model.OwnerCredential;

public interface OwnerCredentialRepository extends MongoRepository<OwnerCredential, String> {

	List<OwnerCredential> findByEmail(String email);

	List<OwnerCredential> findByEmailAndPassword(String email, String password);
}
