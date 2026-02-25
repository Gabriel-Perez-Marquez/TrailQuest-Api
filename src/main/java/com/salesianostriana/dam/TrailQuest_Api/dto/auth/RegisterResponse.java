package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

public record RegisterResponse( @NotNull(message = "{registerResponse.id.notnull}")
                                UUID id,

                                @NotBlank(message = "{registerResponse.username.notblank}")
                                @Size(min = 4, max = 20, message = "{registerResponse.username.size}")
                                String username,

                                @NotBlank(message = "{registerResponse.email.notblank}")
                                @Email(message = "{registerResponse.email.email}")
                                String email,

                                @NotEmpty(message = "{registerResponse.roles.notempty}")
                                Set<UserRole> roles) {
}
