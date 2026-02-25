package com.salesianostriana.dam.TrailQuest_Api.validation;

import com.salesianostriana.dam.TrailQuest_Api.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository repo;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (repo == null) {
            return true;
        }
        return email != null && !repo.existsByEmail(email);
    }
}
