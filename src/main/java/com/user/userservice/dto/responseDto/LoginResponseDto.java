package com.user.userservice.dto.responseDto;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String userName;
	private int userId;
	private String token;
	private String userRole;
	private int roleId;
}
