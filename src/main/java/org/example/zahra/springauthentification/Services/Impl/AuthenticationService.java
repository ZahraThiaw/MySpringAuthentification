package org.example.zahra.springauthentification.Services.Impl;


import org.example.zahra.springauthentification.Datas.Entities.UserEntity;
import org.example.zahra.springauthentification.Datas.Repositories.UserRepository;
import org.example.zahra.springauthentification.Web.Dtos.Requests.LoginUserDto;
import org.example.zahra.springauthentification.Web.Dtos.Requests.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signup(RegisterUserDTO input) {
        UserEntity user = new UserEntity();
        user.setNom(input.getNom());
        user.setPrenom(input.getPrenom());
        user.setEmail(input.getEmail());

        user.setRole(UserEntity.Role.USERSIMPLE);
        user.setPassword(passwordEncoder.encode(input.getPassword()));


        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
