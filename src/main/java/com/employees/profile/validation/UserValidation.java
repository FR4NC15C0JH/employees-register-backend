package com.employees.profile.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.employees.profile.model.User;
import com.employees.profile.service.UserService;

@Component
public class UserValidation {
	
	@Autowired
	private UserService userService;
	
	public void validateCreateUser(User user, BindingResult result) {
		if(user.getEmail() == null) {
			result.addError(new ObjectError("User","Email no information"));
			return;
		}
	}
	
	public void validadeUserEmail(User user,BindingResult result) {
		User userSearch = this.userService.findByEmail(user.getEmail());
		if(userSearch != null) {
			result.addError(new ObjectError("User", "User cadastred!"));
			return;
		}
	}
	
	public void validadeSave(User user,BindingResult result) {
		this.validateCreateUser(user, result);
		this.validadeUserEmail(user, result);
	}
}
