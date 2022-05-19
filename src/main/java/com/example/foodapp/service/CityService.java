package com.example.foodapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodapp.model.City;
import com.example.foodapp.repository.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public int addCity(City city) {
		City cityEntity = cityRepository.save(city);
		return cityEntity.getCityId();
	}

}
