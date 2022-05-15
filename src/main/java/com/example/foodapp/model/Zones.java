package com.example.foodapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="zones", schema="foodapp")
public class Zones {
	
	@Id
    @GeneratedValue
	@Column(name="zoneid")
	private int zoneId;
	
	@Column(name="locality")
	private String locality;
	
	@Column(name="zonearea")
	private String zoneArea;

	public String getZoneArea() {
		return zoneArea;
	}

	public void setZoneArea(String zoneArea) {
		this.zoneArea = zoneArea;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	

}
