package com.salesianostriana.dam.TrailQuest_Api.model;
import jakarta.persistence.*;

import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RouteRegions region;

    @Column(nullable = false)
    private Double distanceKm;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RouteDifficulty difficulty;

    @Column(nullable = false)
    private UUID creatorId;

    @Column(nullable = false)
    private UUID coverFileId;



}
