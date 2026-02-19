package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.dto.RouteCreateDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteFilterDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteResponseDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteUpdateDTO;
import com.salesianostriana.dam.TrailQuest_Api.exception.ResourceNotFoundException;
import com.salesianostriana.dam.TrailQuest_Api.model.PosiblesRegiones;
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
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public RouteResponseDTO createRoute(RouteCreateDTO createDTO) {
        Route route = new Route();
        route.setTitle(createDTO.title());
        route.setRegion(PosiblesRegiones.valueOf(createDTO.region()));
        route.setDistanceKm(createDTO.distanceKm());
        route.setDifficulty(createDTO.difficulty());
        route.setCreatorId(createDTO.creatorId());
        route.setCoverFileId(createDTO.coverFileId());

        Route savedRoute = routeRepository.save(route);
        return RouteResponseDTO.of(savedRoute);
    }
    @Transactional
    @Override
    public RouteResponseDTO updateRoute(Long id, RouteUpdateDTO updateDTO) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));

        if (updateDTO.title() != null) {
            route.setTitle(updateDTO.title());
        }
        if (updateDTO.region() != null) {
            route.setRegion(PosiblesRegiones.valueOf(updateDTO.region()));
        }
        if (updateDTO.distanceKm() != null) {
            route.setDistanceKm(updateDTO.distanceKm());
        }
        if (updateDTO.difficulty() != null) {
            route.setDifficulty(updateDTO.difficulty());
        }
        if (updateDTO.coverFileId() != null) {
            route.setCoverFileId(updateDTO.coverFileId());
        }

        Route updatedRoute = routeRepository.save(route);
        return RouteResponseDTO.of(updatedRoute);
    }
    @Transactional
    @Override
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ruta no encontrada con id: " + id);
        }
        routeRepository.deleteById(id);
    }
    @Transactional
    @Override
    public RouteResponseDTO getRouteById(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));
        return RouteResponseDTO.of(route);
    }
    @Transactional(readOnly = true)
    @Override
    public Page<RouteResponseDTO> getAllRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable)
                .map(RouteResponseDTO::of);
    }
    @Transactional
    @Override
    public Page<RouteResponseDTO> filterRoutes(RouteFilterDTO filterDTO, Pageable pageable) {
        if (filterDTO.sortBy() != null && !filterDTO.sortBy().isEmpty()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if ("desc".equalsIgnoreCase(filterDTO.sortDirection())) {
                direction = Sort.Direction.DESC;
            }
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(direction, filterDTO.sortBy())
            );
        }

        return routeRepository.findAll(
                        RouteSpecification.filterBy(filterDTO),
                        pageable
                )
                .map(RouteResponseDTO::of);
    }


}