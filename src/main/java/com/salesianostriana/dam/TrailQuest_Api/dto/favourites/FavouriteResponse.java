package com.salesianostriana.dam.TrailQuest_Api.dto.favourites;

import io.swagger.v3.oas.annotations.media.Schema;

public record FavouriteResponse(
        @Schema(description = "ID de la ruta añadida a favoritos")
        Long id,

        @Schema(description = "Nombre de la ruta")
        String routeName,

        @Schema(description = "Región de la ruta", example = "GALICIA")
        String region
) {
}
