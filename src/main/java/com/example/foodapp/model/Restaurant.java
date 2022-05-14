package com.example.foodapp.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="restaurant", schema="foodapp")
public class Restaurant {
	
	@Id
    @GeneratedValue
	@Column(name="restaurantid")
	private int restaurantid;

	@Column(name="addressid")
	private String addressid;

	public int getRestaurantid() {
		return restaurantid;
	}

	public void setRestaurantid(int restaurantid) {
		this.restaurantid = restaurantid;
	}

	public String getAddressid() {
		return addressid;
	}

	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	
	}
