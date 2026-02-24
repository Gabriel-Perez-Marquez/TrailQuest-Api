package com.salesianostriana.dam.TrailQuest_Api.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class LocalFileMetadataImpl extends AbstractFileMetadata {

    public static FileMetadata of(String filename) {
        return LocalFileMetadataImpl.builder()
                .id(filename)
                .filename(filename)
                .build();
    }

}