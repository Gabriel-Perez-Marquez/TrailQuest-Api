package com.salesianostriana.dam.TrailQuest_Api.dto.route;

import com.salesianostriana.dam.TrailQuest_Api.model.RouteRegions;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.model.RouteDifficulty;
import com.salesianostriana.dam.TrailQuest_Api.model.LatLng;
import com.salesianostriana.dam.TrailQuest_Api.validation.ValidDistance;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.util.List;

public record RouteUpdateDTO(
        @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
        String title,

        String region,

        @ValidDistance(message = "La distancia debe ser mayor a 0.1 km (100 metros)")
        Double distanceKm,

        String difficulty,

        String  coverFileId,

        @Min(value = 0, message = "La elevación no puede ser negativa")
        Integer elevation,

        List<LatLng> pathPoints
) {
    public void updateEntity(Route route) {
        if (this.title() != null) route.setTitle(this.title());
        if (this.region() != null) route.setRegion(RouteRegions.valueOf(this.region()));
        if (this.difficulty() != null) route.setDifficulty(RouteDifficulty.valueOf(this.difficulty()));
        if (this.coverFileId() != null) route.setCoverFileId(this.coverFileId());
        if (this.elevation() != null) route.setElevation(this.elevation());
        if (this.pathPoints() != null) route.setPathPoints(this.pathPoints());
    }

    public Route toEntity() {
        Route route = new Route();
        route.setTitle((this.title()));
        route.setRegion(RouteRegions.valueOf((this.region())));
        route.setDistanceKm((this.distanceKm()));
        route.setDifficulty(RouteDifficulty.valueOf((this.difficulty())));
        route.setCoverFileId((this.coverFileId()));
        route.setElevation((this.elevation()));
        route.setPathPoints((this.pathPoints()));
        return route;
    }
}
