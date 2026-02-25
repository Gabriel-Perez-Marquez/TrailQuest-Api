package com.salesianostriana.dam.TrailQuest_Api.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {

    String message() default "La contraseña no es lo suficientemente fuerte";


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};


    int min() default 8;
    int max() default Integer.MAX_VALUE;

    boolean hasUpper() default true;
    boolean hasLower() default true;;
    boolean hasNumber() default true;
    boolean hasSpecial() default true;
}
