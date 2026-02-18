package com.salesianostriana.dam.TrailQuest_Api.security.auth;


import com.salesianostriana.dam.TrailQuest_Api.dto.auth.AuthResponse;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.LoginRequest;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.repository.UserRepository;
import com.salesianostriana.dam.TrailQuest_Api.security.jwt.JwtAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final UserRepository userRepository;

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

}
