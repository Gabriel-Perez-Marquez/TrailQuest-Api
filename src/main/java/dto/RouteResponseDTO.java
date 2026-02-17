package dto;

import java.util.UUID;

public record RouteResponseDTO(
        Long id,
        String title,
        String region,
        Double distanceKm,
        String difficulty,
        UUID creatorId,
        UUID coverFileId
) {}
