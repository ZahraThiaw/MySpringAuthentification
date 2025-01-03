package org.example.zahra.springauthentification.Web.Dtos.Requests;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;


}