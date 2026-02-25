package com.salesianostriana.dam.TrailQuest_Api.repository;

import com.salesianostriana.dam.TrailQuest_Api.model.Checkin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {
    boolean existsByPoiIdAndUserIdAndCheckinDate(Long poiId, Long userId, LocalDate date);

    Page<Checkin> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
