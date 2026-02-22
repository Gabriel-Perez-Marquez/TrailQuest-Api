package com.salesianostriana.dam.TrailQuest_Api.dto.poi;

import com.salesianostriana.dam.TrailQuest_Api.model.Poi;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.validation.ValidCoordinates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidCoordinates
public record CreatePOIRequest(
        @NotBlank(message = "El nombre del punto de interés es obligatorio")
        String name,

        @NotNull(message = "La latitud es obligatoria")
        Double lat,

        @NotNull(message = "La longitud es obligatoria")
        Double lon,

        @NotNull(message = "El ID de la ruta es obligatorio")
        Long routeId
) {
    public Poi toEntity(Route route) {
        return Poi.builder()
                .name(this.name())
                .lat(this.lat())
                .lon(this.lon())
                .route(route)
                .build();
    }
}
