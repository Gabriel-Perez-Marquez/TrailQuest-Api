package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.exception.ResourceNotFoundException;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.salesianostriana.dam.TrailQuest_Api.repository.RouteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteService {

    private final RouteRepository routeRepository;


    @Transactional
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }


    @Transactional
    public Route updateRoute(Long id, Route routeUpdates) {
        Route existingRoute = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));


        return routeRepository.save(existingRoute);
    }


    @Transactional
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ruta no encontrada con id: " + id);
        }
        routeRepository.deleteById(id);
    }


    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));
    }


    public Page<Route> getAllRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable);
    }

    public Page<Route> filterRoutes(Specification<Route> spec, Pageable pageable) {
        return routeRepository.findAll(spec, pageable);
    }
}
