package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.dto.favourites.FavouriteRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.favourites.FavouriteResponse;
import com.salesianostriana.dam.TrailQuest_Api.model.FavouriteRoute;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.repository.FavouriteRepository;
import com.salesianostriana.dam.TrailQuest_Api.repository.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FavouriteRouteService {
    private final FavouriteRepository favouriteRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public FavouriteResponse addFavourite(FavouriteRequest req) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (favouriteRepository.existsByRouteIdAndUserId(req.routeId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya está añadida a favoritos");
        }

        Route route = routeRepository.findById(req.routeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada"));

        FavouriteRoute favourite = FavouriteRoute.builder()
                .route(route)
                .user(user)
                .build();

        favouriteRepository.save(favourite);
        return toResponse(favourite);
    }

    @Transactional
    public void removeFavourite(Long routeId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        favouriteRepository.deleteByRouteIdAndUserId(routeId, user.getId());
    }

    public Page<FavouriteResponse> getFavourites(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return favouriteRepository.findByUserIdOrderByIdDesc(user.getId(), pageable)
                .map(this::toResponse);
    }

    private FavouriteResponse toResponse(FavouriteRoute f) {
        return new FavouriteResponse(
                f.getId(),
                f.getRoute().getTitle(),
                f.getRoute().getRegion().name()
        );
    }
}

