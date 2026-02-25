package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DistanceValidator.class)
@Documented
public @interface ValidDistance {
    String message() default "La distancia debe ser un número positivo y no puede ser cero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
