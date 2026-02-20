package com.salesianostriana.dam.TrailQuest_Api.controller;

import com.salesianostriana.dam.TrailQuest_Api.dto.RouteCreateDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteFilterDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteResponseDTO;
import com.salesianostriana.dam.TrailQuest_Api.dto.RouteUpdateDTO;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.specification.RouteSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.salesianostriana.dam.TrailQuest_Api.service.RouteService;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponseDTO> createRoute(@Valid @RequestBody RouteCreateDTO createDTO) {
        Route route = routeService.createRoute(createDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(RouteResponseDTO.of(route));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> updateRoute(
            @PathVariable Long id,
            @Valid @RequestBody RouteUpdateDTO updateDTO) {
        Route updatedRoute = routeService.updateRoute(id, updateDTO.toEntity());
        return ResponseEntity.ok(RouteResponseDTO.of(updatedRoute));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> getRouteById(@PathVariable Long id) {
        Route route = routeService.getRouteById(id);
        return ResponseEntity.ok(RouteResponseDTO.of(route));
    }

    @GetMapping
    public ResponseEntity<Page<RouteResponseDTO>> getAllRoutes(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<RouteResponseDTO> response = routeService.getAllRoutes(pageable)
                .map(RouteResponseDTO::of);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<RouteResponseDTO>> filterRoutes(
            @RequestBody RouteFilterDTO filterDTO,
            @PageableDefault(size = 10) Pageable pageable) {
        Specification<Route> spec = RouteSpecification.filterBy(filterDTO);
        Page<RouteResponseDTO> routes = routeService.filterRoutes(spec, pageable)
                .map(RouteResponseDTO::of);

        return ResponseEntity.ok(routes);
    }
}
