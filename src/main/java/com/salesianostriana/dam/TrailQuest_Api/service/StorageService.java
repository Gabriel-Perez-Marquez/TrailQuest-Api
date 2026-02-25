package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.model.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    FileMetadata store(MultipartFile file);

    Resource loadAsResource(String filename);

    void deleteFile(String filename);

    void deleteAll();
}