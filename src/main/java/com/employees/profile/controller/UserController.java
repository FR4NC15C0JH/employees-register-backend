package com.employees.profile.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employees.profile.enumerator.Profiles;
import com.employees.profile.model.User;
import com.employees.profile.service.UserService;
import com.employees.profile.validation.UserValidation;
import com.employees.profile.web.support.Response;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserValidation userValidation;
	
	@PostMapping
	@RequestMapping("/create")
	public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user, BindingResult result){
		Response<User> response = new Response<User>();
		List<String> errors = new ArrayList<String>();
		try {
			userValidation.validadeSave(user, result);
			if(result.hasErrors()) {
				//result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				for(ObjectError error : result.getAllErrors()) {
					errors.add(error.getDefaultMessage());
				}
				response.setErrors(errors);
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if(user.getProfiles() == null) {
				user.setProfiles(Profiles.ROLE_CUSTOMER);
			}
			User userPersisted = (User) userService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	public ResponseEntity<Response<User>> update(HttpServletRequest request, @RequestBody User user, BindingResult result){
		Response<User> response = new Response<User>();
		try {
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userUpdate = (User) userService.createOrUpdate(user);
			response.setData(userUpdate);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	public ResponseEntity<Response<User>> findById(@PathVariable("id") String id){
		Response<User> response = new Response<User>();
		User user = userService.findById(id);
		if(user == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(user);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<User>>> findAll(@PathVariable int page, @PathVariable int count){
		Response<Page<User>> response = new Response<Page<User>>();
		Page<User> users = userService.findAll(page, count);
		response.setData(users);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> delete(@PathVariable("id") String id){
		Response<User> response = new Response<User>();
		User user = userService.findById(id);
		if(user == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		userService.delete(id);
		return ResponseEntity.ok(response);
	}
	
}
