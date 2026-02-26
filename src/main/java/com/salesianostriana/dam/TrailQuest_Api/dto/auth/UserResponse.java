package com.salesianostriana.dam.TrailQuest_Api.dto.auth;

import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;

import java.util.Set;
import java.util.UUID;

public record UserResponse(UUID id, String username, String email, String avatar, Set<UserRole> role) {

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getRoles()
        );
    }
}
