package com.user.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.userservice.dto.requestdto.AuthenticationRequest;
import com.user.userservice.dto.responsedto.LoginResponseDto;
import com.user.userservice.service.JWTService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private JWTService JWTService;
	
	AuthenticationController(AuthenticationManager authenticationManager , JWTService JWTService){
		this.authenticationManager = authenticationManager;
		this.JWTService = JWTService;
	}

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
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> doLogin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		LoginResponseDto resDto = new LoginResponseDto();
		if (auth.isAuthenticated()) {
			log.info("User authenticated");
			resDto.setToken(JWTService.generateJwtToken(authenticationRequest.getUsername()));
			JWTService.getUserDetails(resDto, authenticationRequest.getUsername());
		}
		log.info("User not authenticated");
		return ResponseEntity.ok(resDto);

	}
}
