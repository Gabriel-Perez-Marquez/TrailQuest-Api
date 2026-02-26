package com.salesianostriana.dam.TrailQuest_Api.dto.favourites;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FavouriteRequest(
        @NotNull(message = "el ID de la ruta es obligatorio")
        @Schema(description = "ID de la ruta a añadir a favoritos", example = "1")
        Long routeId
) {
}
