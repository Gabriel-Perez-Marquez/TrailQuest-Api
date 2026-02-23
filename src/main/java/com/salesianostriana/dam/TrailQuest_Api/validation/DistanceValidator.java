package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DistanceValidator implements ConstraintValidator<ValidDistance, Double> {

    @Override
    public boolean isValid(Double distance, ConstraintValidatorContext context) {
        if (distance == null) {
            return false;
        }
        return distance > 0.0;
    }
}