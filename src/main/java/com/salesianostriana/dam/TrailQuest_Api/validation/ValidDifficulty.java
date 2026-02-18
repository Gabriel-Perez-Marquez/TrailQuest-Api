package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifficultyValidator.class)
@Documented
public @interface ValidDifficulty {
    String message() default "La dificultad debe ser: FÁCIL, MEDIA, DIFÍCIL o EXTREMA";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}