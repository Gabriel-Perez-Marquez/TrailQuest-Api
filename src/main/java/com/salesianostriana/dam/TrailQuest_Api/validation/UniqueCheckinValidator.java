package com.salesianostriana.dam.TrailQuest_Api.validation;

import com.salesianostriana.dam.TrailQuest_Api.dto.checkin.CheckinRequest;
import com.salesianostriana.dam.TrailQuest_Api.repository.CheckinRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class UniqueCheckinValidator implements ConstraintValidator<UniqueCheckin, CheckinRequest> {
    @Autowired
    CheckinRepository checkinRepository;

    @Override
    public boolean isValid(CheckinRequest request, ConstraintValidatorContext context) {
        if (request.poiId() == null) return true;

        Long userId = getCurrentUserId();
        return !checkinRepository.existsByPoiIdAndUserIdAndCheckinDate(
                request.poiId(), userId, LocalDate.now());
    }

    private Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
