package com.salesianostriana.dam.TrailQuest_Api.dto.auth.poi;

import com.salesianostriana.dam.TrailQuest_Api.validation.ValidCoordinates;
import io.swagger.v3.oas.annotations.media.Schema;
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
}
