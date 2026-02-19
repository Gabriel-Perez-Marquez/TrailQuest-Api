package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoordinatesValidator.class)
@Documented
public @interface ValidCoordinates {

    String message() default "Las coordenadas están fuera de los rangos permitidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
