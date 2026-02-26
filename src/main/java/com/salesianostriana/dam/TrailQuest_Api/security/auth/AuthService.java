package com.salesianostriana.dam.TrailQuest_Api.security.auth;


import com.salesianostriana.dam.TrailQuest_Api.dto.auth.*;
import com.salesianostriana.dam.TrailQuest_Api.exception.ResourceNotFoundException;
import com.salesianostriana.dam.TrailQuest_Api.exception.UsernameAlredyInUseException;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;
import com.salesianostriana.dam.TrailQuest_Api.repository.UserRepository;
import com.salesianostriana.dam.TrailQuest_Api.security.jwt.JwtAccessTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse doLogin(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password())
        );

        // Rescatar al usuario por username para obtener su id
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.username()));


        String token = jwtAccessTokenService.generateAccessToken(user);

        return new AuthResponse(loginRequest.username(), token);

    }


    public RegisterResponse doRegister(RegisterRequest registerRequest){
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new UsernameAlredyInUseException("El nombre de usuario ya está en uso");
        }

        String userAvatar = registerRequest.avatar() != null ? registerRequest.avatar() : "default-avatar.png";

        User user = userRepository.save(User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .roles(Set.of(UserRole.USER))
                .build());

        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        );
    }


    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public UserResponse assignRoleToUser(UUID userId, UserRole newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el usuario con id: " + userId));

        user.setRoles(Set.of(newRole));

        return UserResponse.of(userRepository.save(user));
    }

}