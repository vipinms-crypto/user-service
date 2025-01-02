package com.user.userservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.userservice.dto.requestDto.UserRequestDto;
import com.user.userservice.dto.responseDto.UserResponseDto;
import com.user.userservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "API for managing users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@Operation(summary = "Fetch all users", description = "Retrieve a list of all users")
	public List<UserResponseDto> getAllUsers() throws Exception {
		log.info("Entered into getAllUsers");
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Fetch a user", description = "Retrieve a user by passing its ID")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) throws Exception {
		log.info("Entered into getUserById");
		Optional<UserResponseDto> user = userService.getUserById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Persist user", description = "Persist user data")
	public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userReqDto) throws Exception {
		log.info("Entered into createUser");
		UserResponseDto resDto = userService.createUser(userReqDto);
		return ResponseEntity.ok(resDto);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user", description = "Update a user by passing its ID")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable Integer id,
			@RequestBody UserRequestDto userReqDto) throws Exception {
		log.info("Entered into updateUser");
		UserResponseDto resDto = userService.updateUser(id, userReqDto);
		return ResponseEntity.ok(resDto);
	}

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by passing its ID")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) throws Exception {
    	log.info("Entered into deleteUser");
		return ResponseEntity.ok(userService.deleteUser(id));
    }
}