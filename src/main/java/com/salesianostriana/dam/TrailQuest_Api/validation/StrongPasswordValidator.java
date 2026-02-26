package com.salesianostriana.dam.TrailQuest_Api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {


    private int min, max;
    private boolean upper, lower, number, special;

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.upper = constraintAnnotation.hasUpper();
        this.lower = constraintAnnotation.hasLower();
        this.number = constraintAnnotation.hasNumber();
        this.special = constraintAnnotation.hasSpecial();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password == null) return true;

        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(min, max));

        if (upper) rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        if (lower) rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        if (number) rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        if (special) rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordValidator validator = new PasswordValidator(rules);
        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        String messages = validator.getMessages(result).stream()
                .collect(Collectors.joining(", "));

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messages)
                .addConstraintViolation();

        return false;
    }
}
