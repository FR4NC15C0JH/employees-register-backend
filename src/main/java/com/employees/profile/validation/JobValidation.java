package com.employees.profile.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.employees.profile.model.Job;

@Component
public class JobValidation {
	
	public void validateCreateJob(Job job, BindingResult result) {
		if(job.getName() == null) {
			result.addError(new ObjectError("Job","Name no information"));
			return;
		}
	}
}
