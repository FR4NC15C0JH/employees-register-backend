package com.employees.profile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employees.profile.model.Departament;
import com.employees.profile.service.DepartamentService;
import com.employees.profile.validation.DepartamentValidation;
import com.employees.profile.web.support.Response;

@RestController
@RequestMapping("/api/departament")
@CrossOrigin(origins="*")
public class DepartamentController {

	@Autowired
	public DepartamentService departamentService;
	@Autowired
	public DepartamentValidation departamentValidation;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Departament>> create(HttpServletRequest request, @RequestBody Departament departament, 
			BindingResult result){
		Response<Departament> response = new Response<Departament>();
		try {
			departamentValidation.validateCreateDepartament(departament, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Departament departamentPersisted = (Departament) departamentService.createOrUpdate(departament);
			response.setData(departamentPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	public ResponseEntity<Response<Departament>> findById(@PathVariable("id") String id){
		Response<Departament> response = new Response<Departament>();
		Departament departament = departamentService.findOne(id);
		if(departament == null) {
			response.getErrors().add("Departament not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(departament);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Departament>>> findAll(@PathVariable int page, @PathVariable int count){
		Response<Page<Departament>> response = new Response<Page<Departament>>();
		Page<Departament> departaments = departamentService.findAll(page, count);
		response.setData(departaments);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping()
	@RequestMapping("/list")
	//@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	public ResponseEntity<Response<List<Departament>>> list(){
		Response<List<Departament>> response = new Response<List<Departament>>();
		List<Departament> departaments = departamentService.findAll();
		response.setData(departaments);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Departament>> delete(@PathVariable("id") String id){
		Response<Departament> response = new Response<Departament>();
		Departament departament = departamentService.findOne(id);
		if(departament == null) {
			response.getErrors().add("Departament not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		this.departamentService.delete(id);
		return ResponseEntity.ok(response);		
	}
	
}
