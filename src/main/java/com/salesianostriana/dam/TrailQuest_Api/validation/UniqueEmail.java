package com.salesianostriana.dam.TrailQuest_Api.validation;


import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "El email ya existe"; // ESTO ES LO QUE FALTA
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
