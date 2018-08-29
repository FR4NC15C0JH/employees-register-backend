package com.employees.profile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.employees.profile.model.Job;

@Repository
public interface JobRepository extends MongoRepository<Job, String>{

}
