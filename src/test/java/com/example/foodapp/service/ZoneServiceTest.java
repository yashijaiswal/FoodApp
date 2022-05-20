package com.example.foodapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.foodapp.model.Zones;
import com.example.foodapp.repository.ZoneRepository;

@SpringBootTest
public class ZoneServiceTest {
	
	@InjectMocks
	ZoneService zoneService;
	
	@Mock
	ZoneRepository zoneRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetZoneId() {
		
		List<Zones> zones = new ArrayList<Zones>();
		Zones myZone = new Zones();
		myZone.setZoneId(1);
		myZone.setLocality("Ek Murti Chowk");
		myZone.setZoneArea("A");
		zones.add(myZone);
		
		String locality = zones.get(0).getLocality();
		int expectedZoneId = zones.get(0).getZoneId();
				
		when(zoneRepository.findByLocality(locality)).thenReturn(zones);

		int actzoneId = zoneService.getZoneId(locality);
		
		assertEquals(expectedZoneId, actzoneId);
		verify(zoneRepository, times(1)).findByLocality(locality);
		
	}
	
	

}
