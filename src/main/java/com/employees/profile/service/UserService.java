package com.employees.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.employees.profile.model.Departament;
import com.employees.profile.model.Job;
import com.employees.profile.model.User;
import com.employees.profile.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User createOrUpdate(User user) {
		/*Departament departament = departamentService.findOne(idDepartament);
		Job job = jobService.findOne(idJob);		
		user.setDepartament(departament);
		user.setJob(job);*/
		return userRepository.save(user);
	}
	
	public User findById(String id) {
		return userRepository.findOne(id);
	}
	
	public Page<User> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.userRepository.findAll(pages);
	}
}
