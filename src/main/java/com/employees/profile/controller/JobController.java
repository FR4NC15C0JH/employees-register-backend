package com.employees.profile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employees.profile.model.Job;
import com.employees.profile.service.JobService;
import com.employees.profile.validation.JobValidation;
import com.employees.profile.web.support.Response;

@RestController
@RequestMapping("/api/job")
@CrossOrigin(origins="*")
public class JobController {

	@Autowired
	public JobService jobService;
	@Autowired
	public JobValidation jobValidation;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Job>> create(HttpServletRequest request, @RequestBody Job job, 
			BindingResult result){
		Response<Job> response = new Response<Job>();
		try {
			jobValidation.validateCreateJob(job, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Job jobPersisted = (Job) jobService.createOrUpdate(job);
			response.setData(jobPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	public ResponseEntity<Response<Job>> findById(@PathVariable("id") String id){
		Response<Job> response = new Response<Job>();
		Job job = jobService.findOne(id);
		if(job == null) {
			response.getErrors().add("Job not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(job);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Job>>> findAll(@PathVariable int page, @PathVariable int count){
		Response<Page<Job>> response = new Response<Page<Job>>();
		Page<Job> jobs = jobService.findAll(page, count);
		response.setData(jobs);
		return ResponseEntity.ok(response);
	}
}
