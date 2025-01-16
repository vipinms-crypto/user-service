package com.user.userservice.dto.responsedto;

import lombok.Data;

@Data
public class UserResponseDto {
    private String userEmail;
    private String userPhone;
    private String userAddressLine1;
    private String userAddressLine2;
    private Integer userIsRegistered;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
}
