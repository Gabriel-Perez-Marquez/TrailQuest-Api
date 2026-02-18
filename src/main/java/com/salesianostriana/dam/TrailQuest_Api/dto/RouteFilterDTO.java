package com.salesianostriana.dam.TrailQuest_Api.dto;

import java.util.List;

public record RouteFilterDTO(
        String title,
        List<String> regions,
        List<String> difficulties,
        String sortBy,
        String sortDirection
) {}