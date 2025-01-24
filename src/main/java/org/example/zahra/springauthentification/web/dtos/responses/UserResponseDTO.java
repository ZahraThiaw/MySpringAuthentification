package org.example.zahra.springauthentification.web.dtos.responses;

import lombok.Data;
import org.example.zahra.springauthentification.datas.entities.UserEntity;

@Data
public class UserResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private UserEntity.Role role;
}
