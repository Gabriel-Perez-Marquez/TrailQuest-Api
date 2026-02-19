package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Schema(description = "Nombre de usuario para autenticación", example = "usuario_demo")
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,

        @Schema(description = "Contraseña de acceso", example = "P@ssword123")
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password) {
}
