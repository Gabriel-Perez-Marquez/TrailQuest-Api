package com.salesianostriana.dam.TrailQuest_Api.dto;

import com.salesianostriana.dam.TrailQuest_Api.model.Route;

import java.util.UUID;

public record RouteResponseDTO(
        Long id,
        String title,
        String region,
        Double distanceKm,
        String difficulty,
        UUID creatorId,
        UUID coverFileId
) {

    public static RouteResponseDTO of(Route route) {
        return new RouteResponseDTO(
                route.getId(),
                route.getTitle(),
                route.getRegion() != null ? route.getRegion().name() : null,
                route.getDistanceKm(),
                route.getDifficulty(),
                route.getCreatorId(),
                route.getCoverFileId()
        );
    }
}