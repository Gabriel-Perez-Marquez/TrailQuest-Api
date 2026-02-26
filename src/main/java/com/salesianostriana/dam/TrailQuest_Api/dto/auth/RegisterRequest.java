package com.salesianostriana.dam.TrailQuest_Api.dto.auth;


import com.salesianostriana.dam.TrailQuest_Api.validation.StrongPassword;
import com.salesianostriana.dam.TrailQuest_Api.validation.UniqueEmail;
import com.salesianostriana.dam.TrailQuest_Api.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "{registerRequest.username.notblank}")
        @Size(min = 4, max = 20, message = "{registerRequest.username.size}")
        @UniqueUsername
        String username,

        @NotBlank(message = "{registerRequest.password.notblank}")
        @StrongPassword(min = 8)
        String password,

        @NotBlank(message = "{registerRequest.email.notblank}")
        @Email(message = "{registerRequest.email.email}")
        @UniqueEmail
        String email,

        String avatar
) {
}