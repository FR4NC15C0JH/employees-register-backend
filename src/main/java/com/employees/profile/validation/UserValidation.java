package com.employees.profile.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.employees.profile.model.User;

@Component
public class UserValidation {
	
	public void validateCreateUser(User user, BindingResult result) {
		if(user.getEmail() == null) {
			result.addError(new ObjectError("User","Email no information"));
			return;
		}
	}
}
