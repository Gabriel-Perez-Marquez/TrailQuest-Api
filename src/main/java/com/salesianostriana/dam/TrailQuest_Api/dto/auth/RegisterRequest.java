package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;

public record RegisterRequest(String username, String password, String email) {
}
