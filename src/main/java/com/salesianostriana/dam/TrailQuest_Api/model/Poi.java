package com.salesianostriana.dam.TrailQuest_Api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private Route route;

    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Double lat;

    @NotNull
    @Column(nullable = false)
    private Double lon;

    private String photoField;

}
