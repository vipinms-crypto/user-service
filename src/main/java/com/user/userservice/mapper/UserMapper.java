package com.user.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.user.userservice.dto.requestdto.UserRequestDto;
import com.user.userservice.dto.responsedto.UserResponseDto;
import com.user.userservice.entity.User;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
	User toEntity(UserRequestDto reqDto);
	UserResponseDto toDto(User course);
	void updateUserFromDto(UserRequestDto reqDto, @MappingTarget User user);
}
