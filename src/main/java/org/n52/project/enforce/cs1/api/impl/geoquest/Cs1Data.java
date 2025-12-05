package org.n52.project.enforce.cs1.api.impl.geoquest;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * <p>
 * Data DTO.
 * </p>
 *
 * @author Benjamin Pross (b.pross @52north.org)
 * @since 1.0.0
 */
@Entity
@Table(
        name = "cs1_data")
public class Cs1Data {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cs1_data_generator")
    @SequenceGenerator(
            name = "cs1_data_generator",
            sequenceName = "cs1_data_seq",
            allocationSize = 1)
    private Integer id;

    @Column(
            name = "incident_type_id")
    private Integer incidentTypeId = 1;

    @Column(
            name = "municipality_id")
    private Integer municipalityId = 1;
    
    @Column(
            name = "place")
    private String place;

    @Column(
            name = "date")
    private LocalDateTime date;

    @Column(
            name = "odor_inducing_type_id")
    private Integer odorInducingTypeId = 1;

    @Column(
            name = "intensity")
    private Double intensity;

    @Column(
            name = "burn_surface")
    private Double burnSurface;

    @Column(
            name = "visible_smoke")
    private Boolean visibleSmoke;

    @Column(
            name = "link")
    private String link;

    @Column(
            name = "coordinate",
            columnDefinition = "geometry(Point,4326)")
    private Point coordinate;

    public Cs1Data() {
    }

    public Cs1Data(Integer incidentTypeId, Integer municipalityId, String place, LocalDateTime date, Integer odorInducingTypeId,
            Double intensity, Double burnSurface, Boolean visibleSmoke, String link, Point coordinate) {
        this();
        this.incidentTypeId = incidentTypeId;
        this.municipalityId = municipalityId;
        this.place = place;
        this.date = date;
        this.odorInducingTypeId = odorInducingTypeId;
        this.intensity = intensity;
        this.burnSurface = burnSurface;
        this.visibleSmoke = visibleSmoke;
        this.link = link;
        this.coordinate = coordinate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIncidentTypeId() {
        return incidentTypeId;
    }

    public void setIncidentTypeId(Integer incidentTypeId) {
        this.incidentTypeId = incidentTypeId;
    }

    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getOdorInducingTypeId() {
        return odorInducingTypeId;
    }

    public void setOdorInducingTypeId(Integer odorInducingTypeId) {
        this.odorInducingTypeId = odorInducingTypeId;
    }

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    public Double getBurnSurface() {
        return burnSurface;
    }

    public void setBurnSurface(Double burnSurface) {
        this.burnSurface = burnSurface;
    }

    public Boolean getVisibleSmoke() {
        return visibleSmoke;
    }

    public void setVisibleSmoke(Boolean visibleSmoke) {
        this.visibleSmoke = visibleSmoke;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + ", ");
        sb.append("incidentTypeId: " + incidentTypeId + ", ");
        sb.append("municipalityId: " + municipalityId + ", ");
        sb.append("place: " + place + ", ");
        sb.append("date: " + date + ", ");
        sb.append("odorInducingTypeId: " + odorInducingTypeId + ", ");
        sb.append("intensity: " + intensity + ", ");
        sb.append("burnSurface: " + burnSurface + ", ");
        sb.append("visibleSmoke: " + visibleSmoke + ", ");
        sb.append("link: " + link + ", ");
        sb.append("coordinate: " + coordinate + ", ");
        return sb.toString();
    }
}
