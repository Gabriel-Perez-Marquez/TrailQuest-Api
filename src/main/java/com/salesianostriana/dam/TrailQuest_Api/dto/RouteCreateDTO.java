package com.salesianostriana.dam.TrailQuest_Api.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;

public record RouteCreateDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
        String title,

        @NotNull(message = "La región es obligatoria")
        String region,

        @NotNull(message = "La distancia es obligatoria")
        @Positive(message = "La distancia debe ser positiva")
        Double distanceKm,

        @NotNull(message = "La dificultad es obligatoria")
        String difficulty,

        @NotNull(message = "El ID del creador es obligatorio")
        UUID creatorId,

        @NotNull(message = "El ID de la imagen es obligatorio")
        UUID coverFileId
) {}
