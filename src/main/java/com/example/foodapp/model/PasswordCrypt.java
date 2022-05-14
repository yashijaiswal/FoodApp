package com.example.foodapp.model;

import java.util.Base64;
import java.util.Base64.Encoder;

public class PasswordCrypt {

	public String converttHash(final String password) {
		String hashpwd = String.valueOf(password.hashCode());
		String pwd = encode(hashpwd);
		return pwd;
	}

	public String encode(String password) {
		Encoder encoder = Base64.getEncoder();
		String encodedPassword = encoder.encodeToString(password.getBytes());
		return encodedPassword;
	}

	public String decode(String encodedStr) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}

}
