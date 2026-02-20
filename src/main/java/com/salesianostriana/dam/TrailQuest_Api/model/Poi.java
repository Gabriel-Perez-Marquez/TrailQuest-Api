package com.salesianostriana.dam.TrailQuest_Api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pois")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @NotNull(message = "El punto de interes debe pertenecer a una ruta")
    private Route route;

    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Double lat;

    @NotNull
    @Column(nullable = false)
    private Double lon;

    private String photoFileId;

    @DecimalMin(value = "0.0") @DecimalMax(value = "5.0")
    private double rating;

    @PositiveOrZero
    private int reviews;

    @NotBlank
    private String difficulty;

    @NotBlank
    private String duration;

    @NotBlank
    private String type;

    @Column(columnDefinition = "TEXT")
    @Size(max = 1000)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String historicalNote;

    @ElementCollection
    private List<String> features;

}
