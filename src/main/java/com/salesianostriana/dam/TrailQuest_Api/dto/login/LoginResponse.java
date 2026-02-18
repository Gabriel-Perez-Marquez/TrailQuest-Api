package com.salesianostriana.dam.TrailQuest_Api.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponse(
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,
        @NotBlank(message = "El token de accesso no puede estar vacio")
        String accessToken
) {
}
