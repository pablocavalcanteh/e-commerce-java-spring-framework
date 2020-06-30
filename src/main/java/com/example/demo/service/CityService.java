package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.City;
import com.example.demo.repositories.CityRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository repo;
	
	public List<City> findByState(Integer stateId) {
		
		return repo.findCities(stateId);
	}

}
