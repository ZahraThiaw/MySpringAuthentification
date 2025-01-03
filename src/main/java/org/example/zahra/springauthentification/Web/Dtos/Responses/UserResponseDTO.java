package org.example.zahra.springauthentification.Web.Dtos.Responses;

import lombok.Data;
import org.example.zahra.springauthentification.Datas.Entities.UserEntity;

import java.math.BigDecimal;

@Data
public class UserResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private UserEntity.Role role;
}
