package com.salesianostriana.dam.TrailQuest_Api.model;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class LatLng {
    private double latitude;
    private double longitude;
}