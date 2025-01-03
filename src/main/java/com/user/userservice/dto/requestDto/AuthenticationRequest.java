package com.user.userservice.dto.requestDto;

import lombok.Data;

@Data
public class AuthenticationRequest {
	private String username;
	private String password;
}
