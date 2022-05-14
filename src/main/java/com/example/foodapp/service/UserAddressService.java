package com.example.foodapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodapp.model.UserAddress;
import com.example.foodapp.repository.UserAddressRepository;

@Service
public class UserAddressService {
	
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	public int addUserAddress(UserAddress userAddress) {
		UserAddress userAddrEntity = userAddressRepository.save(userAddress);
		return userAddrEntity.getAddressId();
	}

}
