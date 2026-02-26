package com.salesianostriana.dam.TrailQuest_Api.dto;

import java.util.UUID;

public record UploadResponse(
        String  coverFileId,
        String message,
        String fileName
) {
    public static UploadResponse of(String  coverFileId, String fileName) {
        return new UploadResponse(
                coverFileId,
                "Imagen subida correctamente",
                fileName
        );
    }
}
