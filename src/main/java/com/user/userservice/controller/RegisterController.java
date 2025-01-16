package com.user.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.userservice.dto.requestdto.UserRequestDto;
import com.user.userservice.dto.responsedto.UserResponseDto;
import com.user.userservice.exception.DuplicateKeyException;
import com.user.userservice.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("/api")
@Tag(name = "Register API", description = "API for registration purpose")
@CrossOrigin
public class RegisterController {
	
	private UserService userService;

	 public RegisterController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/register")
	 public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userReqDto) throws DuplicateKeyException {
			log.info("Entered into register");
			UserResponseDto resDto = userService.createUser(userReqDto);
			return ResponseEntity.ok(resDto);
		}
}
