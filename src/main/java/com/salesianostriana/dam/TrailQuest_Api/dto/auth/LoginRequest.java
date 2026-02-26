package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import com.salesianostriana.dam.TrailQuest_Api.validation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Schema(description = "Nombre de usuario para autenticación", example = "usuario_demo")
        @NotBlank(message = "{loginRequest.username.notblank}")
        @Size(min = 4, max = 20, message = "{loginRequest.username.size}")
        String username,

        @Schema(description = "Contraseña de acceso", example = "P@ssword123")
        @NotBlank(message = "{loginRequest.password.notblank}")
        @StrongPassword
        @Size(min = 8, message = "{loginRequest.password.size}")
        String password) {
}