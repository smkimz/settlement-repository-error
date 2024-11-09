package com.hanghae.user.dto;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String email;
    private String password;
    private String nickname;
}
