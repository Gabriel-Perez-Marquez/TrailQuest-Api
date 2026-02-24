package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.Payload;

public @interface UniqueCheckin {
    String message() default "Ya has hecho check-in en este POI hoy, no puedes hacer más";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
