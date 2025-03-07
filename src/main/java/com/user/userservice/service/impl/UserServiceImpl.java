package com.user.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.userservice.dto.requestdto.UserRequestDto;
import com.user.userservice.dto.responsedto.UserResponseDto;
import com.user.userservice.entity.User;
import com.user.userservice.exception.BadRequestException;
import com.user.userservice.exception.DuplicateKeyException;
import com.user.userservice.exception.ResourceNotFoundException;
import com.user.userservice.exception.ServiceLayerException;
import com.user.userservice.mapper.UserMapper;
import com.user.userservice.repository.UserRepository;
import com.user.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;
	
	

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User savedUser = null;
		try {
			log.info("Entered into service method createUser");
			User user = userMapper.toEntity(userRequestDto);
			user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
			savedUser = userRepository.save(user);
			log.info("Exit from service method createUser");
			return mapToResponseDto(savedUser);
		} catch (DataIntegrityViolationException ex) {
			if (ex.getMessage().contains("Violation of UNIQUE KEY constraint 'user_name'")) {
				throw new DuplicateKeyException("Duplicate entry for user: " + userRequestDto.getUserName());
			} else if (ex.getMessage().contains("Violation of UNIQUE KEY constraint 'user_email'")) {
				throw new DuplicateKeyException("Duplicate entry for email: " + userRequestDto.getUserEmail());
			} else if (ex.getMessage().contains("Violation of UNIQUE KEY constraint 'user_phone'")) {
				throw new DuplicateKeyException("Duplicate entry for mobile: " + userRequestDto.getUserPhone());
			}
		} catch (BadRequestException e) {
			throw new BadRequestException("Internal server error");
		}
		return null;
	}

	@Override
	public List<UserResponseDto> getAllUsers() throws ServiceLayerException {
		log.info("Entered into service method getAllUsers");
		return userRepository.findAll().stream().map(this::mapToResponseDto).toList();
	}

	@Override
	public Optional<UserResponseDto> getUserById(Integer id) throws ServiceLayerException {
		log.info("Entered into service method getUserById");
		return userRepository.findById(id).map(this::mapToResponseDto);
	}

	@Override
	public UserResponseDto updateUser(Integer id, UserRequestDto userRequestDto) {
		try {
			log.info("Entered into service method updateUser");
			Optional<User> userOptional = userRepository.findById(id);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				userMapper.updateUserFromDto(userRequestDto, user);
				User updatedUser = userRepository.save(user);
				log.info("Exit from service method updateUser with response ," + updatedUser);
				return userMapper.toDto(updatedUser);
			} else {
				log.info("No User data found for given id," + id);
				throw new ResourceNotFoundException("No data found");
			}
		} catch (DuplicateKeyException ex) {
			if (ex.getMessage().contains("Violation of UNIQUE KEY constraint 'user_name'")) {
				throw new DuplicateKeyException("Duplicate entry for user: " + userRequestDto.getUserName());
			} else if (ex.getMessage().contains("Violation of UNIQUE KEY constraint 'user_email'")) {
				throw new DuplicateKeyException("Duplicate entry for email: " + userRequestDto.getUserEmail());
			} else if (ex.getMessage().contains("Violation of UNIQUE KEY constraint 'user_phone'")) {
				throw new DuplicateKeyException("Duplicate entry for mobile: " + userRequestDto.getUserPhone());
			}
		} catch (BadRequestException e) {
			throw new BadRequestException("Internal server error");
		}
		return null;
	}

	@Override
	public boolean deleteUser(Integer id) throws ServiceLayerException {
		log.info("Entered into service method deleteUser");
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			log.info("Entered into service method deleteUser");
			return true;
		} else {
			log.info("No User data found for given id," + id);
			throw new ResourceNotFoundException("No data found");
		}
	}

	private UserResponseDto mapToResponseDto(User user) {
		log.info("Entered into service method mapToResponseDto");
		return userMapper.toDto(user);
	}
}
