package com.salesianostriana.dam.TrailQuest_Api.dto;
import com.salesianostriana.dam.TrailQuest_Api.model.RouteRegions;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.model.RouteDifficulty;
import com.salesianostriana.dam.TrailQuest_Api.validation.ValidDistance;
import jakarta.validation.constraints.*;
import java.util.UUID;

public record RouteCreateDTO(

        @NotBlank(message = "El título es obligatorio")
        @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
        String title,

        @NotBlank(message = "La región es obligatoria")
        String region,

        @NotNull(message = "La distancia es obligatoria")
        @ValidDistance(message = "La distancia debe ser mayor a 0.1 km (100 metros)")
        Double distanceKm,

        @NotNull(message = "La dificultad es obligatoria")
        String difficulty,

        @NotNull(message = "El ID del creador es obligatorio")
        UUID creatorId,

        @NotNull(message = "El ID de la imagen es obligatorio")
        UUID coverFileId


) {
    public Route toEntity() {
    Route route = new Route();
        route.setTitle((this.title()));
        route.setRegion(RouteRegions.valueOf((this.region())));
        route.setDistanceKm((this.distanceKm()));
        route.setDifficulty(RouteDifficulty.valueOf((this.difficulty())));
        route.setCreatorId((this.creatorId()));
        route.setCoverFileId((this.coverFileId()));
    return route;
    }
}
