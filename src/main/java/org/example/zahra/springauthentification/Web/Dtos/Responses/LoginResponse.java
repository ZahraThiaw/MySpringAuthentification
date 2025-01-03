package org.example.zahra.springauthentification.Web.Dtos.Responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;

    private long expiresIn;

}