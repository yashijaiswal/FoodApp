package com.example.foodapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodapp.model.Account;
import com.example.foodapp.model.PasswordCrypt;
import com.example.foodapp.model.UserAuth;
import com.example.foodapp.model.Users;
import com.example.foodapp.repository.AccountRepository;
import com.example.foodapp.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	AccountRepository accRepository;

	@Autowired
	SecurityService securityService;

	public int addUser(Users user) {
		Users userEntity = usersRepository.save(user);
		return userEntity.getUserId();
	}

	public int addAccount(Account acc) {
		Account accEntity = accRepository.save(acc);
		return accEntity.getAccId();
	}

	public UserAuth validateUser(String username, String pwd) {
		PasswordCrypt pwdCrypt = new PasswordCrypt();
		String password = pwdCrypt.converttHash(pwd);
		UserAuth userAuth = null;
		List<Account> accList = accRepository.findByUserName(username);
		if (accList != null && accList.size() != 0) {
			Account acc1 = accList.get(0);
			String pass = acc1.getPassword();
			int accid = acc1.getAccId();
			if (pass.equals(password)) {
				userAuth = securityService.storeTokenInCache(username, accid);
			}
		}
		return userAuth;

	}

}
