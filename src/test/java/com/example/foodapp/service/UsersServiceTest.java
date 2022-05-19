package com.example.foodapp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.foodapp.model.Account;
import com.example.foodapp.model.PasswordCrypt;
import com.example.foodapp.model.Users;

@SpringBootTest
public class UsersServiceTest {

	@Autowired
	UsersService userService;

	@Autowired
	PasswordCrypt pwdCrypt;

	int accId = 0;
	int userID = 0;

	@Test
	public void testAddAccount() {

		String encodedPassword = pwdCrypt.converttHash("12345");
		Account acc = new Account();
		acc.setEmailId("abcxyz@gmail.com");
		acc.setPassword(encodedPassword);
		acc.setUserName("YashikaYadav");

		accId = userService.addAccount(acc);
		
		DateTimeFormatter createDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dob = LocalDate.parse("18-05-2022", createDateFormatter);

		Users user = new Users();
		user.setAccId(accId);
		user.setAddressId(11);
		user.setContact(8421826749L);
		user.setDob(dob);
		user.setGender("Female");
		user.setUserName("Yashika");

		userID = userService.addUser(user);
		
		assertTrue(userID != 0);
		assertTrue(accId != 0);
	}

	/*
	 * @Test public void testAddUser() {
	 * 
	 * DateTimeFormatter createDateFormatter =
	 * DateTimeFormatter.ofPattern("dd-MM-yyyy"); LocalDate dob =
	 * LocalDate.parse("18-05-2022", createDateFormatter);
	 * 
	 * Users user = new Users(); user.setAccId(59); user.setAddressId(11);
	 * user.setContact(8421826749L); user.setDob(dob); user.setGender("Female");
	 * user.setUserName("Yashika");
	 * 
	 * userID = userService.addUser(user); assertTrue(userID != 0); }
	 */

}
