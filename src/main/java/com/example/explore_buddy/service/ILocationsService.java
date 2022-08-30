package com.example.explore_buddy.service;

import com.example.explore_buddy.model.DescriptionlessLocation;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.LocationFullInfo;
import com.example.explore_buddy.model.enumeration.LocationType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ILocationsService {
    List<Location> getAll();
    Location post(Location location);
    Location updateLocation(Location location);
    List<Location> importFromCsv(MultipartFile file);
    LocationFullInfo getLocation(Integer id);
    List<DescriptionlessLocation> getMarkers(String query,String[] locationTypes,boolean isFavourite);
    void deleteLocationById(Integer id);
    List<DescriptionlessLocation> convertToDescriptionless(List<Location> locations);
    List<LocationType> convertToTypeFromString(String[] locationTypes);
}
