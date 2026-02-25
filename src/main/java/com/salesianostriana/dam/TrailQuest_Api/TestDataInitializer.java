package com.salesianostriana.dam.TrailQuest_Api;

import com.salesianostriana.dam.TrailQuest_Api.model.*;
import com.salesianostriana.dam.TrailQuest_Api.repository.PoiRepository;
import com.salesianostriana.dam.TrailQuest_Api.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements CommandLineRunner {

    private final RouteRepository repository;
    private final PoiRepository poiRepository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {


            Route r1 = Route.builder()
                    .title("Ruta del Cares")
                    .region(RouteRegions.ESPANYA)
                    .difficulty(RouteDifficulty.MEDIA)
                    .distanceKm(11.5)
                    .elevation(450)
                    .creatorId(UUID.randomUUID())
                    .coverFileId("foto1")
                    .pathPoints(new ArrayList<>(List.of(
                            new LatLng(43.2383, -4.9667),
                            new LatLng(43.2420, -4.9600),
                            new LatLng(43.2480, -4.9520)
                    )))
                    .pois(new ArrayList<>())
                    .build();

            r1 = repository.save(r1);


            Poi poi1 = Poi.builder()
                    .name("Mirador de los Martínez")
                    .lat(43.2420)
                    .lon(-4.9600)
                    .rating(4.8)
                    .reviews(150)
                    .difficulty("Fácil")
                    .type("Mirador")
                    .duration("15 min")
                    .route(r1)
                    .build();

            poiRepository.save(poi1);


            Route r2 = Route.builder()
                    .title("Caminito del Rey")
                    .region(RouteRegions.ESPANYA)
                    .difficulty(RouteDifficulty.DIFICIL)
                    .distanceKm(7.7)
                    .elevation(215)
                    .creatorId(UUID.randomUUID())
                    .coverFileId("foto2")
                    .pathPoints(new ArrayList<>(List.of(
                            new LatLng(36.9319, -4.7725),
                            new LatLng(36.9150, -4.7650),
                            new LatLng(36.9030, -4.7590)
                    )))
                    .pois(new ArrayList<>())
                    .build();

            r2 = repository.save(r2);

            Poi poi2 = Poi.builder()
                    .name("Puente Colgante")
                    .lat(36.9150)
                    .lon(-4.7650)
                    .rating(4.9)
                    .reviews(320)
                    .difficulty("Media")
                    .type("Atracción")
                    .duration("30 min") 
                    .route(r2)
                    .build();

            poiRepository.save(poi2);
            System.out.println("✅ " + repository.count() + " rutas de TrailQuest inicializadas.");
        }
    }
}