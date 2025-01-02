package com.user.userservice.dto.requestDto;

import lombok.Data;

@Data
public class UserRequestDto {

	private String userName;
    private String userPassword;
    private String userEmail;
    private String userPhone;
    private String userAddressLine1;
    private String userAddressLine2;
    private Integer userIsRegistered;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
}
