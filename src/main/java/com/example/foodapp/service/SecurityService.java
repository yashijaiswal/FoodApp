package com.example.foodapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.foodapp.model.UserAuth;
import com.example.foodapp.repository.UserAuthRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityService {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${expirationTimeInMilliseconds}")
	private String expirationTime;

	@Autowired
	private UserAuthRepository userAuthRepository;

	public UserAuth storeTokenInCache(String username, int accid) {

		String jwtToken = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(this.expirationTime)))
				.signWith(SignatureAlgorithm.HS512, this.jwtSecret.getBytes()).compact();
		UserAuth userAuth = new UserAuth();
		userAuth.setUserName(username);
		userAuth.setUserToken(jwtToken);
		userAuth.setAccId(accid);
		UserAuth usr = userAuthRepository.save(userAuth);
		return usr;

	}

	public String getUserToken(String username) {
		List<UserAuth> authList = userAuthRepository.findByUserName(username);
		UserAuth usr = authList.get(0);
		if (usr != null) {
			return usr.getUserToken();
		} else
			return null;
	}

}
