package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthResponse(
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,
        @NotBlank(message = "El token de accesso no puede estar vacio")
        String accessToken
) {
}
