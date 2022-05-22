package com.example.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodapp.model.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long>  {
	List<UserAuth> findByUserName(String username);

}
