package com.salesianostriana.dam.TrailQuest_Api.controller;

import com.salesianostriana.dam.TrailQuest_Api.dto.poi.CreatePoiRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.poi.PoiResponse;
import com.salesianostriana.dam.TrailQuest_Api.model.Poi;
import com.salesianostriana.dam.TrailQuest_Api.model.Route;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.service.PoiService;
import com.salesianostriana.dam.TrailQuest_Api.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pois")
@RequiredArgsConstructor
public class PoiController {

    private final PoiService poiService;
    private final RouteService routeService;

    @GetMapping("/route/{routeId}")
    public ResponseEntity<Page<PoiResponse>> findAllByRoute(
            @PathVariable Long routeId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<PoiResponse> response = poiService.findAllByRoute(routeId, pageable)
                .map(PoiResponse::of);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PoiResponse> createPoi(
            @Valid @RequestBody CreatePoiRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        Route route = routeService.getRouteById(request.routeId());
        Poi newPoi = request.toEntity(route);
        Poi savedPoi = poiService.save(newPoi, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PoiResponse.of(savedPoi));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoi(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        poiService.deleteById(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<PoiResponse> uploadPoiPhoto(
            @PathVariable Long id,
            @RequestPart("file")MultipartFile file,
            @AuthenticationPrincipal User currentUser
    ) {
        String contentType = file.getContentType();

        if (contentType == null || !List.of("image/jpeg", "image/png", "image/gif").contains(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten imagenes (JPEG, PNG, GIF)");
        }

        // String photoFileId = storageService.store(file);
        String photoFileId = "simulated-id-123";
        Poi updated = poiService.updatePhoto(id, photoFileId, currentUser);

        return ResponseEntity.ok(PoiResponse.of(updated));
    }

}
