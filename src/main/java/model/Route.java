package model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String Title;

    @Enumerated(EnumType.STRING)
    private PosiblesRegiones region;

    @Column(nullable = false)
    private Double distanceKm;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private UUID creatorId;

    @Column(nullable = false)
    private UUID coverFileId;

    public Route() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public PosiblesRegiones getRegion() {
        return region;
    }

    public void setRegion(PosiblesRegiones region) {
        this.region = region;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public UUID getCoverFileId() {
        return coverFileId;
    }

    public void setCoverFileId(UUID coverFileId) {
        this.coverFileId = coverFileId;
    }



}
