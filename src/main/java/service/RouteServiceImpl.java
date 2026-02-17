package service;

import dto.RouteCreateDTO;
import dto.RouteFilterDTO;
import dto.RouteResponseDTO;
import dto.RouteUpdateDTO;
import exception.ResourceNotFoundException;
import model.PosiblesRegiones;
import model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RouteRepository;
import specification.RouteSpecification;

import java.util.UUID;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

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
        return mapToResponseDTO(savedRoute);
    }

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
        return mapToResponseDTO(updatedRoute);
    }

    @Override
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ruta no encontrada con id: " + id);
        }
        routeRepository.deleteById(id);
    }

    @Override
    public RouteResponseDTO getRouteById(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));
        return mapToResponseDTO(route);
    }

    @Override
    public Page<RouteResponseDTO> getAllRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    public Page<RouteResponseDTO> filterRoutes(RouteFilterDTO filterDTO, Pageable pageable) {
        // Aplicar ordenación si se especifica
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
                .map(this::mapToResponseDTO);
    }

    private RouteResponseDTO mapToResponseDTO(Route route) {
        return new RouteResponseDTO(
                route.getId(),
                route.getTitle(),
                route.getRegion() != null ? route.getRegion().name() : null,
                route.getDistanceKm(),
                route.getDifficulty(),
                route.getCreatorId(),
                route.getCoverFileId()
        );
    }
}