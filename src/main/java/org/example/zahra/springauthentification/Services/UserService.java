package org.example.zahra.springauthentification.Services;

import org.example.zahra.springauthentification.Datas.Entities.UserEntity;
import org.example.zahra.springauthentification.Web.Dtos.Responses.UserResponseDTO;


public interface UserService extends BaseService<UserEntity, Long> {
    UserResponseDTO getConnectedUserProfile();
}
