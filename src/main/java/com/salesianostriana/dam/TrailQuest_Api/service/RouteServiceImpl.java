package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.dto.RouteCreateDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteFilterDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteResponseDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteUpdateDTO;
import com.salesianostriana.dam.TrailQuest_Api.exception.ResourceNotFoundException;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.salesianostriana.dam.TrailQuest_Api.repository.RouteRepository;
import com.salesianostriana.dam.TrailQuest_Api.specification.RouteSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteServiceImpl {

    private final RouteRepository routeRepository;


    @Transactional
    public RouteResponseDTO createRoute(RouteCreateDTO createDTO) {
        Route route = createDTO.toEntity();

        Route savedRoute = routeRepository.save(route);

        return RouteResponseDTO.of(savedRoute);
    }


    @Transactional
    public RouteResponseDTO updateRoute(Long id, RouteUpdateDTO updateDTO) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));

        updateDTO.updateEntity(route);

        return RouteResponseDTO.of(routeRepository.save(route));
    }

    @Transactional
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ruta no encontrada con id: " + id);
        }
        routeRepository.deleteById(id);
    }

    public RouteResponseDTO getRouteById(Long id) {
        return routeRepository.findById(id)
                .map(RouteResponseDTO::of)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));
    }

    public Page<RouteResponseDTO> getAllRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable)
                .map(RouteResponseDTO::of);
    }

    public Page<RouteResponseDTO> filterRoutes(RouteFilterDTO filterDTO, Pageable pageable) {
        return routeRepository.findAll(
                        RouteSpecification.filterBy(filterDTO),
                        pageable
                )
                .map(RouteResponseDTO::of);
    }
}
