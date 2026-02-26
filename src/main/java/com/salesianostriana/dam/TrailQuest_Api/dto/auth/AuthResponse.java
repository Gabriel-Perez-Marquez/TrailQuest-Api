package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthResponse(
        @NotBlank(message = "{authResponse.username.notblank}")
        @Size(min = 4, max = 20, message = "{authResponse.username.size}")
        String username,

        @NotBlank(message = "{authResponse.accessToken.notblank}")
        String accessToken
) {
}
