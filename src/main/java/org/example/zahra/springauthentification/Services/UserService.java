package org.example.zahra.springauthentification.Services;

import org.example.zahra.springauthentification.Datas.Entities.UserEntity;
import org.example.zahra.springauthentification.Web.Dtos.Responses.UserResponseDTO;

import java.util.List;

public interface UserService extends BaseService<UserEntity, Long> {
    UserResponseDTO getConnectedUserProfile();

    List<UserResponseDTO> getAllUsers();
}
