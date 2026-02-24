package com.salesianostriana.dam.TrailQuest_Api.repository;

import com.salesianostriana.dam.TrailQuest_Api.model.FavouriteRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavouriteRepository extends JpaRepository<FavouriteRoute, Long> {
    boolean existsByRouteIdAndUserId(Long routeId, UUID userId);
    Page<FavouriteRoute> findByUserIdOrderByIdDesc(UUID userId, Pageable pageable);
    void deleteByRouteIdAndUserId(Long routeId, UUID userId);
}
