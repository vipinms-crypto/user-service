package com.user.userservice.dto.responseDto;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private final String jwtToken;
}
