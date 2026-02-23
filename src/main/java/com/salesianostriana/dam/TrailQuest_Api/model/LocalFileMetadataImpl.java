package com.salesianostriana.dam.TrailQuest_Api.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class LocalFileMetadataImpl extends AbstractFileMetadata {

    public static FileMetadata of(String filename) {
        return LocalFileMetadataImpl.builder()
                .id(filename)
                .filename(filename)
                .URL("/api/files/" + filename)
                .build();
    }

    public static FileMetadata of(String filename, String baseUrl) {
        return LocalFileMetadataImpl.builder()
                .id(filename)
                .filename(filename)
                .URL(baseUrl + "/api/files/" + filename)
                .build();
    }
}