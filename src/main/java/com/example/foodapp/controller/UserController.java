package com.example.foodapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodapp.model.Account;
import com.example.foodapp.model.City;
import com.example.foodapp.model.PasswordCrypt;
import com.example.foodapp.model.UserAddress;
import com.example.foodapp.model.Users;
import com.example.foodapp.service.CityService;
import com.example.foodapp.service.UserAddressService;
import com.example.foodapp.service.UsersService;
import com.example.foodapp.service.ZoneService;
import com.fasterxml.jackson.databind.JsonNode;
import com.health.healthplus.model.Hospital;

@RestController
@Component
public class UserController {

	@Autowired
	private UsersService userService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private CityService cityService;

	@Autowired
	private UserAddressService userAddressService;

	@GetMapping(path = "/")
	public String welcome() {
		return "Application is Up and Running";
	}

	@PostMapping(path = "/addUserAccount")
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public String addUserAccount(@RequestBody JsonNode userJson) throws Exception {

		PasswordCrypt pwdCrypt = new PasswordCrypt();

		String username = userJson.get("username").asText();
		String password = userJson.get("password").asText();
		String email = userJson.get("email").asText();
		DateTimeFormatter createDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime created_on = LocalDateTime.parse(userJson.get("created_on").asText(), createDateFormatter);
		LocalDateTime last_login = LocalDateTime.parse(userJson.get("last_login").asText(), createDateFormatter);		
		String cities = userJson.get("city").asText();
		String locality = userJson.get("locality").asText();		
		String name = userJson.get("name").asText();
		String gender = userJson.get("gender").asText();
		long contactNum = userJson.get("contact").asLong();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dob = LocalDate.parse(userJson.get("dob").asText(), formatter);

		String encodedPassword = pwdCrypt.converttHash(password);
		Account acc = new Account();
		acc.setCreatedOn(created_on);
		acc.setEmailId(email);
		acc.setLastLogin(last_login);
		acc.setPassword(encodedPassword);
		acc.setUserName(username);

		int id = userService.addAccount(acc);
		int zoneId = zoneService.getZoneId(locality);

		City city = new City();
		city.setCity(cities);
		city.setLocality(locality);
		city.setZoneId(zoneId);

		int cityId = cityService.addCity(city);

		UserAddress userAddr = new UserAddress();
		userAddr.setCityId(cityId);
		int userAddrId = userAddressService.addUserAddress(userAddr);		

		Users user = new Users();
		user.setAccId(id);
		user.setAddressId(userAddrId);
		user.setContact(contactNum);
		user.setDob(dob);
		user.setGender(gender);
		user.setUserName(name);

		int userId = userService.addUser(user);
		return "User with Id " + userId + " created Successfully";

	}
	
	@PostMapping(path = "/validateUser")
	public String validateUserAccount(@RequestBody JsonNode userJson) {		
		return null;
	}
	

	
	


}
