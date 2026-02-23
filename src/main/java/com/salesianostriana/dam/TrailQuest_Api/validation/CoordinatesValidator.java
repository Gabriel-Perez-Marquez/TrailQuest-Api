package com.salesianostriana.dam.TrailQuest_Api.validation;

import com.salesianostriana.dam.TrailQuest_Api.dto.poi.CreatePoiRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoordinatesValidator implements ConstraintValidator<ValidCoordinates, CreatePoiRequest> {


    @Override
    public boolean isValid(CreatePoiRequest request, ConstraintValidatorContext context) {
        if (request == null){
            return true;
        }

        Double lat = request.lat();
        Double lon = request.lon();

        if (lat == null || lon == null){
            return false;
        }

        return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
    }
}
