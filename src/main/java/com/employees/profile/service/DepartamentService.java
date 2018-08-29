package com.employees.profile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.employees.profile.model.Departament;
import com.employees.profile.repository.DepartamentRepository;

@Service
public class DepartamentService {
	
	@Autowired
	private DepartamentRepository departamentRepository;
	
	public List<Departament> listTicket() {		
		return this.departamentRepository.findAll();
	}
	
	public Departament createOrUpdate(Departament departament) {
		return departamentRepository.save(departament);
	}
	
	public Departament findOne(String id) {
		return departamentRepository.findOne(id);
	}
	
	public Page<Departament> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.departamentRepository.findAll(pages);
	}
	
	public void delete(String id) {
		this.departamentRepository.delete(id);
	}
}
