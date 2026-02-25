package com.salesianostriana.dam.TrailQuest_Api.dto.checkin;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CheckinResponse(
        @Schema(description = "ID del check-in", example = "1")
        Long id,

        @Schema(description = "Nombre del POI visitado", example = "Lago del Lago Castiñeiras")
        String poiNombre,

        @Schema(description = "Fecha y hora del check-in", example = "2026-02-23T13:00:00")
        LocalDateTime createdAt,

        @Schema(description = "URL de la foto de llegada (en caso de estar subida)", example = "/images/checkins/1/foto.jpg")
        String fotoUrl
) {
}
