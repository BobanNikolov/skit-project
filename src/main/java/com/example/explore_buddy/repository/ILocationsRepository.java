package com.example.explore_buddy.repository;

import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.enumeration.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ILocationsRepository extends JpaRepository<Location,Integer> {
    List<Location> findByName(String name);
    List<Location> findLocationsByTypeIsIn(List<LocationType> locationType);
    List<Location> findLocationsByNameContainingIgnoreCase(String query);
    List<Location> findLocationsByTypeIsInAndNameContainingIgnoreCase(List<LocationType> locationType,String query);
}
