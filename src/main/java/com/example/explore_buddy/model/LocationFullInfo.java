package com.example.explore_buddy.model;

import com.example.explore_buddy.model.enumeration.LocationType;
import lombok.Data;

import javax.persistence.Enumerated;

@Data
public class LocationFullInfo {
    private Integer id;
    private String name;
    private Double lon;
    private Double lat;
    private String description;
    private LocationType type;
    private boolean isFavourite;
    public LocationFullInfo(Location location,boolean isFavourite){
        this.id=location.getId();
        this.name= location.getName();
        this.lon=location.getLon();
        this.lat=location.getLat();
        this.description=location.getDescription();
        this.type=location.getType();
        this.isFavourite=isFavourite;
    }
}
