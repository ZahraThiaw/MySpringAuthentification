package org.example.zahra.springauthentification.web.controllers;

import org.example.zahra.springauthentification.datas.entities.UserEntity;
import org.example.zahra.springauthentification.web.dtos.responses.UserResponseDTO;
import org.example.zahra.springauthentification.filters.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserController extends BaseController<UserEntity, Long, UserResponseDTO> {
    ResponseEntity<ApiResponse<UserResponseDTO>> getConnectedUserProfile();
    //ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers();
}
