package service;

import dto.RouteCreateDTO;
import dto.RouteFilterDTO;
import dto.RouteResponseDTO;
import dto.RouteUpdateDTO;
import model.Route;
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
