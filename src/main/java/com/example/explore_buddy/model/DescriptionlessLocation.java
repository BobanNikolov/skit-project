package com.example.explore_buddy.model;

import com.example.explore_buddy.model.enumeration.LocationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DescriptionlessLocation {
    private Integer id;
    private String name;
    private Double lon;
    private Double lat;
    private LocationType type;
    public DescriptionlessLocation(Integer id,String name, Double lon, Double lat, LocationType type){
        this.id=id;
        this.name=name;
        this.lon=lon;
        this.lat=lat;
        this.type=type;
    }
}
