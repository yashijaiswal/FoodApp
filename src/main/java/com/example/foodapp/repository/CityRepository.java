package com.example.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodapp.model.City;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
