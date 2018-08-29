package com.employees.profile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.employees.profile.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	User findByEmail(String email);
	
}
