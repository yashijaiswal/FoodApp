package com.example.foodapp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.foodapp.model.City;

@SpringBootTest
public class CityServiceTest {
	
	@Autowired
	CityService cityService;
	
	@Test
	public void testAddCity() {
		
		City city = new City();
		city.setCity("Noida");
		city.setLocality("Ek Murti Chowk");
		city.setZoneId(1);
		
		int cityId = cityService.addCity(city);
		assertTrue(cityId != 0);
		
	}

}
