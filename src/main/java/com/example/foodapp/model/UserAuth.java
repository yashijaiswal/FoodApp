package com.example.foodapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "userauth", schema = "foodapp")
public class UserAuth {

	@Id
	@GeneratedValue
	@Column(name = "userauthid")
	private int userAuthId;

	@Column(name = "accid")
	private int accId;

	@Column(name = "username")
	private String userName;

	@Column(name = "usertoken")
	private String userToken;

	public int getUserAuthId() {
		return userAuthId;
	}

	public void setUserAuthId(int userAuthId) {
		this.userAuthId = userAuthId;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}
