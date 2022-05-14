package com.example.foodapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="restaurantaddress", schema="foodapp")
public class RestaurantAddress {
    
	@Id
    @GeneratedValue
	@Column(name="addressid")
	private int addressId;
	
	@Column(name="cityid")
	private int cityId;
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	
}

