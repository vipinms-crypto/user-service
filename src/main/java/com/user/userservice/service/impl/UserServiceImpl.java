package com.user.userservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.userservice.dto.requestDto.UserRequestDto;
import com.user.userservice.dto.responseDto.UserResponseDto;
import com.user.userservice.entity.User;
import com.user.userservice.exception.ResourceNotFoundException;
import com.user.userservice.mapper.UserMapper;
import com.user.userservice.repository.UserRepository;
import com.user.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository courseRepository;
	
	@Autowired
	private UserMapper courseMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto courseRequestDto) throws Exception {
    	log.info("Entered into service method createUser");
        User course = courseMapper.toEntity(courseRequestDto);
        User savedUser = courseRepository.save(course);
        log.info("Exit from service method createUser");
        return mapToResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() throws Exception{
    	log.info("Entered into service method getAllUsers");
        return courseRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDto> getUserById(Integer id) throws Exception{
    	log.info("Entered into service method getUserById");
        return courseRepository.findById(id)
                .map(this::mapToResponseDto);
    }

    @Override
    public UserResponseDto updateUser(Integer id, UserRequestDto courseRequestDto) throws Exception{
    	log.info("Entered into service method updateUser");
        Optional<User> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            User course = courseOptional.get();
            course = courseMapper.toEntity(courseRequestDto);
            User updatedUser = courseRepository.save(course);
            log.info("Exit from service method updateUser with response ,"+ updatedUser);
            return  courseMapper.toDto(updatedUser);
        } else {
        	log.info("No User data found for given id,"+id);
        	throw new ResourceNotFoundException("No data found");
        }
    }

    @Override
    public boolean deleteUser(Integer id) throws Exception{
    	log.info("Entered into service method deleteUser");
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            log.info("Entered into service method deleteUser");
            return true;
        } else {
        	log.info("No User data found for given id,"+id);
        	throw new ResourceNotFoundException("No data found");
        }
    }

    private UserResponseDto mapToResponseDto(User course) {
    	log.info("Entered into service method mapToResponseDto");
    	return courseMapper.toDto(course);
    }
}
