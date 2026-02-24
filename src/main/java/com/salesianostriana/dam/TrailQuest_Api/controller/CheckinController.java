package com.salesianostriana.dam.TrailQuest_Api.controller;

import com.salesianostriana.dam.TrailQuest_Api.dto.checkin.CheckinRequest;
import com.salesianostriana.dam.TrailQuest_Api.dto.checkin.CheckinResponse;
import com.salesianostriana.dam.TrailQuest_Api.model.User;
import com.salesianostriana.dam.TrailQuest_Api.service.CheckinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class CheckinController {
    private final CheckinService checkinService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CheckinResponse> createCheckin(@Valid @RequestPart("checkin") CheckinRequest request, @RequestPart(value = "fotoLlegada", required = false) MultipartFile fotoLlegada) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CheckinResponse response = checkinService.createCheckin(request, fotoLlegada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/historial")
    public ResponseEntity<Page<CheckinResponse>> getHistorial(@PageableDefault(size = 10) Pageable pageable) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<CheckinResponse> response = checkinService.getHistorial(pageable);
        return ResponseEntity.ok(response);
    }

}
