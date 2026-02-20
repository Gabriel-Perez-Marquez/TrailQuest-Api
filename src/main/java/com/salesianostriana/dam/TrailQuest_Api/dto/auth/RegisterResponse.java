package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;

import java.util.UUID;

public record RegisterResponse(UUID id, String username, String password, String email, UserRole userRole) {
}
