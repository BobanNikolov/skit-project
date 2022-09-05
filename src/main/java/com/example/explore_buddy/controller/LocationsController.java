package com.example.explore_buddy.controller;

import com.example.explore_buddy.helpers.CSVHelper;
import com.example.explore_buddy.model.DescriptionlessLocation;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.LocationFullInfo;
import com.example.explore_buddy.service.ILocationsService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/home")
@RequiredArgsConstructor
public class LocationsController {
  private final ILocationsService locationsService;

  @GetMapping
  public List<Location> getLocations() {
    return locationsService.getAll();
  }

  @PostMapping("/importCsv")
  public ResponseEntity<List<Location>> postLocations(@RequestParam("file") MultipartFile file) {
    List<Location> locations = new ArrayList<>();
    if (CSVHelper.hasCSVFormat(file)) {
      locations = locationsService.importFromCsv(file);
    }
    return ResponseEntity.status(HttpStatus.OK).body(locations);
  }

  @GetMapping("/markers")
  public List<DescriptionlessLocation> getMarkers(@RequestParam(required = false) String searchText,
                                                  @RequestParam(required = false)
                                                  String locationTypeString,
                                                  @RequestParam(required = false)
                                                  boolean isFavourite) {
    String[] locationTypes = null;
    if (locationTypeString != null) {
      locationTypes = locationTypeString.split(",");
    }
    return locationsService.getMarkers(searchText, locationTypes, isFavourite);
  }

  @GetMapping("/getLocation")
  public LocationFullInfo getLocation(@RequestParam Integer id) {
    return locationsService.getLocation(id);
  }

  @PostMapping("/add")
  public Location addLocation(@RequestBody Location location) {
    return locationsService.post(location);
  }

  @PostMapping("/delete/{id}")
  public void deleteLocation(@PathVariable Integer id) {
    locationsService.deleteLocationById(id);
  }

  @PostMapping("/update")
  public Location updateLocation(@RequestBody Location location) {
    return locationsService.updateLocation(location);
  }
}
