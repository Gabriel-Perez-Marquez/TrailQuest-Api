package com.salesianostriana.dam.TrailQuest_Api.security.auth;


import com.salesianostriana.dam.TrailQuest_Api.dto.auth.AuthResponse;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.LoginRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.RegisterRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.auth.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> doLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(201)
                .body(authService.doLogin(loginRequest));

    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> doRegister(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(201)
                .body(authService.doRegister(registerRequest));
    }
}
