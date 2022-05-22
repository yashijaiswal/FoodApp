package com.example.foodapp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.foodapp.service.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	private String jwtSecret;

	@Autowired
	private SecurityService secService;

	public void setJwtSecret(String s) {
		this.jwtSecret = s;
	}
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request.getRequestURI().startsWith("/addUserAccount") || request.getRequestURI().startsWith("/validateUser")
				|| request.getRequestURI().startsWith("/swagger")
				|| request.getRequestURI().startsWith("/webjars/springfox-swagger-ui")) {

			chain.doFilter(request, response);
			return;
		}
		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}

		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
			throws AuthenticationException {

		String token = request.getHeader("Authorization");

		if (token != null) {
			final Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes())
					.parseClaimsJws(token.replace("Bearer ", "")).getBody();

			String username = claims.getSubject();

			if (username != null) {
				ArrayList<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority("USER"));

				String userToken = secService.getUserToken(username);
				if (token.equals(userToken)) {
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
				} else
					return null;
			}
			throw new AuthenticationException("Failed to find user in Subject");
		}
		logger.debug("Missing Authorization Header");
		throw new AuthenticationException("Missing Authorization Header");

	}

}
