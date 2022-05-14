package com.example.foodapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodapp.model.Zones;
import com.example.foodapp.repository.ZoneRepository;

@Service
public class ZoneService {
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	public int getZoneId(String locality) {
		List<Zones> zonesList = zoneRepository.findByLocality(locality);
		int zoneId = zonesList.get(0).getZoneId();
		return zoneId;
	}
	

}
