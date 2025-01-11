package com.user.userservice.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.user.userservice.dto.responseDto.LoginResponseDto;

public interface JWTService {
	
	String generateJwtToken(String userName);

	String extractUsername(String token);

	boolean validateToken(String token, UserDetails userDetails);

	void getUserDetails(LoginResponseDto resDto, String username);

}
