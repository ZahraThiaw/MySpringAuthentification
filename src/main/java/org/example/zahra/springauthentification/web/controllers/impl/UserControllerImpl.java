package org.example.zahra.springauthentification.web.controllers.impl;

import org.example.zahra.springauthentification.datas.entities.UserEntity;
import org.example.zahra.springauthentification.web.dtos.mappers.GenericMapper;
import org.example.zahra.springauthentification.services.UserService;
import org.example.zahra.springauthentification.web.controllers.UserController;
import org.example.zahra.springauthentification.web.dtos.responses.UserResponseDTO;
import org.example.zahra.springauthentification.filters.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequestMapping("/users")
@RestController
public class UserControllerImpl extends BaseControllerImpl<UserEntity, Long, UserResponseDTO> implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService,
                              GenericMapper<UserEntity, ?, UserResponseDTO> userMapper) {
        super(userService, userMapper);
        this.userService = userService;
    }

    @Override
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getConnectedUserProfile() {
        UserResponseDTO profile = userService.getConnectedUserProfile();
        ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                true,
                "Profil utilisateur récupéré avec succès",
                profile,
                Collections.emptyList(),
                "OK",
                200
        );
        return ResponseEntity.ok(response);
    }




}
