package com.salesianostriana.dam.TrailQuest_Api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "poi_entity")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "photo_field", length = 500)
    private String photoField;

}
