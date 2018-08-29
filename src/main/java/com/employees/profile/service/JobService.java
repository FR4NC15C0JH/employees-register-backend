package com.employees.profile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.employees.profile.model.Job;
import com.employees.profile.repository.JobRepository;

@Repository
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;
	
	List<Job> findAll(){
		return this.jobRepository.findAll();
	}
	
	public Job createOrUpdate(Job job) {
		return jobRepository.save(job);
	}
	
	public Job findOne(String id) {
		return jobRepository.findOne(id);
	}
	
	public Page<Job> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.jobRepository.findAll(pages);
	}
}
