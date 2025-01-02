package com.user.userservice.service;

import java.util.List;
import java.util.Optional;

import com.user.userservice.dto.requestDto.UserRequestDto;
import com.user.userservice.dto.responseDto.UserResponseDto;

public interface UserService {

	 // Create a new user
    UserResponseDto createUser(UserRequestDto userRequestDto) throws Exception;

    // Get all users
    List<UserResponseDto> getAllUsers() throws Exception;

    // Get a user by ID
    Optional<UserResponseDto> getUserById(Integer id) throws Exception;

    // Update a user
    UserResponseDto updateUser(Integer id, UserRequestDto userRequestDto) throws Exception;

    // Delete a user
    boolean deleteUser(Integer id) throws Exception;
}
