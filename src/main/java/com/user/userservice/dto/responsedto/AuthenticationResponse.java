package com.user.userservice.dto.responsedto;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private final String jwtToken;
}
