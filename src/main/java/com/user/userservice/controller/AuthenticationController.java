package com.user.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.userservice.dto.requestDto.AuthenticationRequest;
import com.user.userservice.service.JWTService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JWTService JWTService;

	@PostMapping("/authenticate")
	public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		if (auth.isAuthenticated()) {
			log.info("User authenticated");
			return JWTService.generateJwtToken(authenticationRequest.getUsername());
		}
		log.info("User not authenticated");
		return null;

	}
}
