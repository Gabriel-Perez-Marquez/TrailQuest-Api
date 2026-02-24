package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.dto.checkin.CheckinRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.checkin.CheckinResponse;
import com.salesianostriana.dam.TrailQuest_Api.model.Checkin;
import com.salesianostriana.dam.TrailQuest_Api.model.Poi;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.repository.CheckinRepository;
import com.salesianostriana.dam.TrailQuest_Api.repository.PoiRepository;
import com.salesianostriana.dam.TrailQuest_Api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckinService {
    private final CheckinRepository checkinRepository;
    private final PoiRepository poiRepository;
    private final UserRepository userRepository;

    public CheckinResponse createCheckin(CheckinRequest request, MultipartFile fotoLlegada) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Poi poi = poiRepository.findById(request.poiId())
                .orElseThrow(() -> new EntityNotFoundException("POI no encontrado: " + request.poiId()));

        String fotoUrl = saveFotoLlegada(fotoLlegada, user.getId(), poi.getId());

        Checkin checkin = Checkin.builder()
                .poi(poi)
                .user(user)
                .fotoUrl(fotoUrl)
                .build();

        checkin = checkinRepository.save(checkin);
        return toCheckinResponse(checkin);
    }

    public Page<CheckinResponse> getHistorial(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return checkinRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable)
                .map(this::toCheckinResponse);
    }

    private CheckinResponse toCheckinResponse(Checkin checkin) {
        return new CheckinResponse(
                checkin.getId(),
                checkin.getPoi().getName(),
                checkin.getCreatedAt(),
                checkin.getFotoUrl()
        );
    }

    private String saveFotoLlegada(MultipartFile foto, UUID userId, Long poiId) {
        if (foto == null || foto.isEmpty()) return null;

        if (!"image/jpeg".equals(foto.getContentType()) && !"image/png".equals(foto.getContentType())) {
            throw new IllegalArgumentException("Solo se permiten fotos en formato JPEG o PNG");
        }

        String dir = "uploads/checkins/" + userId + "/" + poiId + "/";
        String filename = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
        String path = dir + filename;

        try {
            foto.transferTo(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la foto", e);
        }

        return "/static/" + path;
    }
}
