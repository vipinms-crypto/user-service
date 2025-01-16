package com.user.userservice.dto.responsedto;

import lombok.Data;

@Data
public class LoginResponseDto {
	private String userName;
	private int userId;
	private String token;
	private String userRole;
	private int roleId;
}
