package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.model.Poi;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.model.UserRole;
import com.salesianostriana.dam.TrailQuest_Api.repository.PoiRepository;
import com.salesianostriana.dam.TrailQuest_Api.repository.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PoiService {

    private final PoiRepository poiRepository;
    private final RouteRepository routeRepository;

    public Page<Poi> findAllByRoute(Long routeId, Pageable pageable) {
        return poiRepository.findByRouteId(routeId, pageable);
    }

    public Poi save(Poi newPoi, User user) {
        Route route = routeRepository.findById(newPoi.getRoute().getId())
                .orElseThrow(() -> new EntityNotFoundException("La ruta no existe"));

        boolean isOwner = route.getCreatorId().equals(user.getId());
        boolean isAdmin = user.getRoles().contains(UserRole.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Solo el creador de la ruta puede añadir POIs");
        }

        return poiRepository.save(newPoi);
    }

    // Refactorizar metodo cuando metamos logica para ficheros
    public Poi updatePhoto(Long poiId, String photoFileId, User user) {
        Poi poi = poiRepository.findById(poiId)
                .orElseThrow(() -> new EntityNotFoundException("POI no encontrado"));

        if (!poi.getRoute().getCreatorId().equals(user.getId()) &&
                !user.getRoles().contains(UserRole.ADMIN)) {
            throw new AccessDeniedException("No tienes permiso para editar este POI");
        }

        poi.setPhotoFileId(photoFileId);
        return poiRepository.save(poi);
    }

    public void deleteById(Long id, User user) {
        Poi poi = poiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("POI no encontrado"));

        boolean isOwner = poi.getRoute().getCreatorId().equals(user.getId());
        boolean isAdmin = user.getRoles().contains(UserRole.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("No tienes permiso para eliminar este punto de interés");
        }

        poiRepository.delete(poi);
    }

}
