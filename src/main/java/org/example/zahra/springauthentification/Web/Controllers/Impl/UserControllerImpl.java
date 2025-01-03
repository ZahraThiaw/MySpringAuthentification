package org.example.zahra.springauthentification.Web.Controllers.Impl;

import org.example.zahra.springauthentification.Datas.Entities.UserEntity;
import org.example.zahra.springauthentification.Mappers.GenericMapper;
import org.example.zahra.springauthentification.Services.UserService;
import org.example.zahra.springauthentification.Web.Controllers.UserController;
import org.example.zahra.springauthentification.Web.Dtos.Responses.UserResponseDTO;
import org.example.zahra.springauthentification.Web.Filters.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

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

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>(
                true,
                "Liste des utilisateurs récupérée avec succès",
                users,
                Collections.emptyList(),
                "OK",
                200
        );
        return ResponseEntity.ok(response);
    }
}
