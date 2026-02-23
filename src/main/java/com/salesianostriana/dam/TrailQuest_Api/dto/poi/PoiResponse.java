package com.salesianostriana.dam.TrailQuest_Api.dto.poi;

import com.salesianostriana.dam.TrailQuest_Api.model.Poi;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PoiResponse(
        @NotNull(message = "El ID no puede ser nulo")
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotNull
        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
        Double lat,

        @NotNull
        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
        Double lon,

        String photoFileId,

        @NotNull(message = "El ID de la ruta es obligatorio")
        Long routeId
) {
    public static PoiResponse of(Poi poi) {
        return new PoiResponse(
                poi.getId(),
                poi.getName(),
                poi.getLat(),
                poi.getLon(),
                poi.getPhotoFileId(),
                poi.getRoute().getId()
        );
    }
}
