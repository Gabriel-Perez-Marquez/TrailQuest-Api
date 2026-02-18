package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class DifficultyValidator implements ConstraintValidator<ValidDifficulty, String> {

    private static final List<String> VALID_DIFFICULTIES = Arrays.asList(
            "FÁCIL", "MEDIA", "DIFÍCIL", "EXTREMA"
    );

    @Override
    public boolean isValid(String difficulty, ConstraintValidatorContext context) {
        if (difficulty == null) {
            return false;
        }
        return VALID_DIFFICULTIES.contains(difficulty.toUpperCase());
    }
}