package controller;

import dto.RouteCreateDTO;
import dto.RouteFilterDTO;
import dto.RouteResponseDTO;
import dto.RouteUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.RouteService;

import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<RouteResponseDTO> createRoute(@Valid @RequestBody RouteCreateDTO createDTO) {
        RouteResponseDTO createdRoute = routeService.createRoute(createDTO);
        return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> updateRoute(
            @PathVariable Long id,
            @Valid @RequestBody RouteUpdateDTO updateDTO) {
        RouteResponseDTO updatedRoute = routeService.updateRoute(id, updateDTO);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> getRouteById(@PathVariable Long id) {
        RouteResponseDTO route = routeService.getRouteById(id);
        return ResponseEntity.ok(route);
    }

    @GetMapping
    public ResponseEntity<Page<RouteResponseDTO>> getAllRoutes(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<RouteResponseDTO> routes = routeService.getAllRoutes(pageable);
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<RouteResponseDTO>> filterRoutes(
            @RequestBody RouteFilterDTO filterDTO,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<RouteResponseDTO> routes = routeService.filterRoutes(filterDTO, pageable);
        return ResponseEntity.ok(routes);
    }
}
