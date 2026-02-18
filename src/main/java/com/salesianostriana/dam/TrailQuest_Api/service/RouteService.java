package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.dto.RouteCreateDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteFilterDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteResponseDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RouteService {
    RouteResponseDTO createRoute(RouteCreateDTO createDTO);
    RouteResponseDTO updateRoute(Long id, RouteUpdateDTO updateDTO);
    void deleteRoute(Long id);
    RouteResponseDTO getRouteById(Long id);
    Page<RouteResponseDTO> getAllRoutes(Pageable pageable);
    Page<RouteResponseDTO> filterRoutes(RouteFilterDTO filterDTO, Pageable pageable);
}
