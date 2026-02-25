package com.salesianostriana.dam.TrailQuest_Api.model;
import jakarta.persistence.*;


import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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
    private String coverFileId;

    @Column(nullable = false)
    private Integer elevation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "route_path_points", joinColumns = @JoinColumn(name = "route_id"))
    private List<LatLng> pathPoints = new ArrayList<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Route route = (Route) o;
        return getId() != null && Objects.equals(getId(), route.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
