package com.salesianostriana.dam.TrailQuest_Api.dto;


import lombok.Builder;


@Builder
public record FileResponse(
        String id,
        String name,
        String uri,
        String type,
        long size
)
{}

