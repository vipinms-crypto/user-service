package com.user.userservice.mapper;

import org.mapstruct.Mapper;

import com.user.userservice.dto.requestDto.UserRequestDto;
import com.user.userservice.dto.responseDto.UserResponseDto;
import com.user.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserRequestDto reqDto);
	UserResponseDto toDto(User course);
}
