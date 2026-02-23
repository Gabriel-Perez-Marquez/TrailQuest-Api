package com.salesianostriana.dam.TrailQuest_Api.dto;


import com.salesianostriana.dam.TrailQuest_Api.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public record FileResponse(
        String id,
        String name,
        String uri,
        String type,
        long size
) {

    public static FileResponse of(FileMetadata metadata, MultipartFile file, String uri) {
        return new FileResponse(
                metadata.getId(),
                metadata.getFilename(),
                uri,
                file.getContentType(),
                file.getSize()
        );
    }
}
