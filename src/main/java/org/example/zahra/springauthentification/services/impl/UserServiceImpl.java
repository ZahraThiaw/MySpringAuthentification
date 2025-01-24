package org.example.zahra.springauthentification.services.impl;

import org.example.zahra.springauthentification.datas.entities.UserEntity;
import org.example.zahra.springauthentification.datas.repositories.UserRepository;
import org.example.zahra.springauthentification.web.dtos.mappers.GenericMapper;
import org.example.zahra.springauthentification.services.UserService;
import org.example.zahra.springauthentification.web.dtos.responses.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, Long> implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final GenericMapper<UserEntity, ?, UserResponseDTO> userMapper;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, GenericMapper<UserEntity, ?, UserResponseDTO> userMapper) {
        super(userRepository);
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO getConnectedUserProfile() {
        String email = jwtService.extractUsernameFromToken(); // Récupérer l'e-mail depuis le JWT
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'e-mail : " + email));
        return userMapper.toResponseDto(user); // Mapper vers UserResponseDTO
    }

}
