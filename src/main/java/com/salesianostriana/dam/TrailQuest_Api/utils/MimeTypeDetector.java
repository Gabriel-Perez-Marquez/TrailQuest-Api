package com.salesianostriana.dam.TrailQuest_Api.utils;

import org.springframework.core.io.Resource;

public interface MimeTypeDetector {
    String getMimeType(Resource resource);
}