package com.user.userservice.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
	
	String generateJwtToken(String userName);

	String extractUsername(String token);

	boolean validateToken(String token, UserDetails userDetails);

}
