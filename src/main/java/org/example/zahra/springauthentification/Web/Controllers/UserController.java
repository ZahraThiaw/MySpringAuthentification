package org.example.zahra.springauthentification.Web.Controllers;

import org.example.zahra.springauthentification.Datas.Entities.UserEntity;
import org.example.zahra.springauthentification.Web.Dtos.Responses.UserResponseDTO;
import org.example.zahra.springauthentification.Web.Filters.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController extends BaseController<UserEntity, Long, UserResponseDTO> {
    ResponseEntity<ApiResponse<UserResponseDTO>> getConnectedUserProfile();
    //ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers();
}
