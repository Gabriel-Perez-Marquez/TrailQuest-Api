package com.salesianostriana.dam.TrailQuest_Api.controller;

import com.salesianostriana.dam.TrailQuest_Api.dto.favourites.FavouriteRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.favourites.FavouriteResponse;
import com.salesianostriana.dam.TrailQuest_Api.service.FavouriteRouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favourites")
public class FavouriteRouteController {

    private final FavouriteRouteService service;

    @PostMapping
    public ResponseEntity<FavouriteResponse> addFavourite(@Valid @RequestBody FavouriteRequest req) {
        FavouriteResponse response = service.addFavourite(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> removeFavourite(@PathVariable Long routeId) {
        service.removeFavourite(routeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<FavouriteResponse>> getFavourites(@PageableDefault(size = 10) Pageable pageable) {
        Page<FavouriteResponse> response = service.getFavourites(pageable);
        return ResponseEntity.ok(response);
    }
}
