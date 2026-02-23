package com.salesianostriana.dam.TrailQuest_Api.controller;

import com.salesianostriana.dam.TrailQuest_Api.dto.FileResponse;
import com.salesianostriana.dam.TrailQuest_Api.model.FileMetadata;
import com.salesianostriana.dam.TrailQuest_Api.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.salesianostriana.dam.TrailQuest_Api.utils.MimeTypeDetector;

import java.net.URI;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final MimeTypeDetector mimeTypeDetector;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> upload(@RequestPart("file") MultipartFile file) {
        FileMetadata metadata = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/")
                .path(metadata.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(uri)).body(
                FileResponse.of(metadata, file, uri)
        );
    }

    @GetMapping("/{id:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String id) {
        Resource resource = storageService.loadAsResource(id);
        String mimeType = mimeTypeDetector.getMimeType(resource);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(resource);
    }


    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) {
        storageService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }
}