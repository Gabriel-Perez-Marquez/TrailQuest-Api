package com.salesianostriana.dam.TrailQuest_Api.dto.checkin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CheckinRequest(
        @Schema(description = "ID del POI para check-in", example = "1")
        @NotNull(message = "El ID del POI es obligatorio")
        Long poiId,

        @Schema(description = "Foto de llegada al POI (JPEG/PNG, máx 5MB)", example = "foto_llegada.jpg")
        MultipartFile fotoLlegada
) {
}
