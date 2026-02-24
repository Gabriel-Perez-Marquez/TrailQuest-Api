package com.salesianostriana.dam.TrailQuest_Api.service;


import com.salesianostriana.dam.TrailQuest_Api.exception.InvalidFileTypeException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${storage.location:uploads}") String storageLocation) {
        if (storageLocation.trim().isEmpty()) {
            throw new StorageException("La ubicación de subida no puede estar vacía.");
        }
        this.rootLocation = Paths.get(storageLocation);
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("No se pudo inicializar el almacenamiento", e);
        }
    }

    @Override
    public FileMetadata store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Fallo al almacenar un archivo vacío.");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new InvalidFileTypeException("El archivo debe ser una imagen válida.");
            }

            String filename = StringUtils.cleanPath(file.getOriginalFilename());

            String uniqueFilename = System.currentTimeMillis() + "_" + filename;

            Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("No se puede almacenar un archivo fuera del directorio actual.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

            return LocalFileMetadataImpl.of(uniqueFilename, baseUrl);

        } catch (IOException e) {
            throw new StorageException("Fallo al almacenar el archivo.", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("No se pudo leer el archivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("No se pudo leer el archivo: " + filename, e);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Files.deleteIfExists(this.rootLocation.resolve(filename));
        } catch (IOException e) {
            throw new StorageException("No se pudo eliminar el archivo: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}

