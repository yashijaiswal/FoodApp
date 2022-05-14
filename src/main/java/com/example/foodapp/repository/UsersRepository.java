package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodapp.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{

}
