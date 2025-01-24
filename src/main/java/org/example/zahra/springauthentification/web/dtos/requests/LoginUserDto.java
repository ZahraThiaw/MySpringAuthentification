package org.example.zahra.springauthentification.web.dtos.requests;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;


}