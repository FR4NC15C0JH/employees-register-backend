package com.employees.profile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.employees.profile.model.Departament;

@Repository
public interface DepartamentRepository extends MongoRepository<Departament, String>{
	
}
