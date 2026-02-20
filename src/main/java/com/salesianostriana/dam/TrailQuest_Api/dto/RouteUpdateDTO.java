package com.salesianostriana.dam.TrailQuest_Api.dto;

import com.salesianostriana.dam.TrailQuest_Api.model.PosiblesRegiones;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.model.RouteDifficulty;
import jakarta.validation.constraints.*;
import java.util.UUID;

public record RouteUpdateDTO(
        @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
        String title,

        String region,

        @Positive(message = "La distancia debe ser positiva")
        Double distanceKm,

        String difficulty,

        UUID coverFileId
) {
    public void updateEntity(Route route) {
        if (this.title() != null) route.setTitle(this.title());
        if (this.region() != null) route.setRegion(PosiblesRegiones.valueOf(this.region()));
        if (this.difficulty() != null) route.setDifficulty(RouteDifficulty.valueOf(this.difficulty()));
        if (this.coverFileId() != null) route.setCoverFileId(this.coverFileId());
    }
}
