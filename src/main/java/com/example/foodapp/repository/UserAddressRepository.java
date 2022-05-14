package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodapp.model.UserAddress;


@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

}
