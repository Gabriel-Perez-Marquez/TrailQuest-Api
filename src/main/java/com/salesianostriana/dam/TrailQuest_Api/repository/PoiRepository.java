package com.salesianostriana.dam.TrailQuest_Api.repository;

import com.salesianostriana.dam.TrailQuest_Api.model.Poi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {


    Page<Poi> findByRouteId(Long routeId, Pageable pageable);
}
