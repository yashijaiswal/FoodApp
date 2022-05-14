package com.example.foodapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodapp.model.Account;
import com.example.foodapp.model.Users;
import com.example.foodapp.repository.AccountRepository;
import com.example.foodapp.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	AccountRepository accRepository;

	public int addUser(Users user) {
		Users userEntity = usersRepository.save(user);
		return userEntity.getUserId();
	}
	
	public int addAccount(Account acc) {
		Account accEntity = accRepository.save(acc);
		return accEntity.getAccId();
	}

}
