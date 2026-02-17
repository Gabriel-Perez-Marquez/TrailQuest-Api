package dto;

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
) {}
