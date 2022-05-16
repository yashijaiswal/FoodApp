package com.example.foodapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.foodapp.exception.FieldNotFoundException;
import com.example.foodapp.model.Account;
import com.example.foodapp.model.City;
import com.example.foodapp.model.PasswordCrypt;
import com.example.foodapp.model.UserAddress;
import com.example.foodapp.model.Users;
import com.example.foodapp.service.CityService;
import com.example.foodapp.service.UserAddressService;
import com.example.foodapp.service.UsersService;
import com.example.foodapp.service.ZoneService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@Component
public class UserController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UsersService userService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private CityService cityService;

	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/")
	public String welcome() {
		return "Application is Up and Running";
	}

	@PostMapping(path = "/addUserAccount")
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class })
	public String addUserAccount(@RequestBody JsonNode userJson) {

		try {

			PasswordCrypt pwdCrypt = new PasswordCrypt();
			if (!userJson.isNull() && !userJson.get("username").isNull() && !userJson.get("password").isNull()
					&& !userJson.get("city").isNull() && !userJson.get("locality").isNull()
					&& !userJson.get("name").isNull() && !userJson.get("contact").isNull()
					&& userJson.get("contact").asText().length() == 10) {
				LOGGER.info("ALL VALIDATION SUCCESSFUL FOR {}",  userJson.get("username").asText());
				String username = userJson.get("username").asText();
				String password = userJson.get("password").asText();
				String email = userJson.get("email").asText();
				DateTimeFormatter createDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				LocalDateTime created_on = LocalDateTime.parse(userJson.get("created_on").asText(),
						createDateFormatter);
				LocalDateTime last_login = LocalDateTime.parse(userJson.get("last_login").asText(),
						createDateFormatter);
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
			} else {
				LOGGER.info("VALIDATION NOT SUCCESSFUL");
				throw new FieldNotFoundException("Field is Null value");
			}

		} catch (Exception e) {
			LOGGER.error("ERROR MESSAGE {}", e.getMessage());
			e.printStackTrace();
		}finally {
			System.out.println("AddUser Controller");
		}
		return "Field is Null value";
	}

	@PostMapping(path = "/validateUser")
	public String validateUserAccount(@RequestBody JsonNode userJson) {
		return null;
	}

	@GetMapping("/getRestaurantsForUserLocality")
	public JsonNode getRestaurantsByLocality(@RequestParam String city, @RequestParam String locality) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		String uri = "http://localhost:8083/restaurantByLocality";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("city", city)
				.queryParam("locality", locality).build();

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String json = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
		JsonNode node = null;
		try {
			node = new ObjectMapper().readTree(json);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return node;

	}

}
