package com.salesianostriana.dam.TrailQuest_Api.utils;

import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TikaMimeTypeDetector implements MimeTypeDetector {

    private final Tika tika = new Tika();

    @Override
    public String getMimeType(Resource resource) {
        try {
            return tika.detect(resource.getInputStream());
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

}