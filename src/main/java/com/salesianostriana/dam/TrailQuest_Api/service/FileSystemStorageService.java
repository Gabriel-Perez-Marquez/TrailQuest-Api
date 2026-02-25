package com.salesianostriana.dam.TrailQuest_Api.service;

import com.salesianostriana.dam.TrailQuest_Api.exception.StorageException;
import com.salesianostriana.dam.TrailQuest_Api.model.FileMetadata;
import com.salesianostriana.dam.TrailQuest_Api.model.LocalFileMetadataImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    private Path rootLocation;


    @PostConstruct
    @Override
    public void init() {
        rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
            javax.imageio.ImageIO.scanForPlugins();
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }

    }

    @Override
    public FileMetadata store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("El archivo está vacío");
            }

            String extension = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
            String newFilename = UUID.randomUUID().toString() + "." + (extension != null ? extension : "jpg");

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
            }

            return LocalFileMetadataImpl.of(newFilename);
        } catch (IOException e) {
            throw new StorageException("Error al guardar el archivo en disco", e);
        }
    }

    @Override
    public Resource loadAsResource(String id) {
        try {
            Path file = load(id);
            UrlResource resource =
                    new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + id);
            }

        } catch (MalformedURLException ex) {
            throw new StorageException("Could not read file: " + id);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Files.delete(load(filename));
        } catch (IOException e) {
            throw new StorageException("Could not delete file:" + filename);
        }
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not delete all");
        }
    }
}