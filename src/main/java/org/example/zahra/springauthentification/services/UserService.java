package org.example.zahra.springauthentification.services;

import org.example.zahra.springauthentification.datas.entities.UserEntity;
import org.example.zahra.springauthentification.web.dtos.responses.UserResponseDTO;


public interface UserService extends BaseService<UserEntity, Long> {
    UserResponseDTO getConnectedUserProfile();
}
