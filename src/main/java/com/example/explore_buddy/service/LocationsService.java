package com.example.explore_buddy.service;

import static com.example.explore_buddy.ExploreBuddyApplication.getToken;

import com.example.explore_buddy.helpers.CSVHelper;
import com.example.explore_buddy.model.DescriptionlessLocation;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.LocationFullInfo;
import com.example.explore_buddy.model.enumeration.LocationType;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.repository.IUserRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LocationsService implements ILocationsService {
  private final ILocationsRepository locationsRepository;
  private final IUserRepository userRepository;

  @Override
  public List<Location> getAll() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return locationsRepository.findAll();
  }

  @Override
  public Location post(Location location) {
    return checkAndSaveLocation(location);
  }

  public Location checkAndSaveLocation(Location location) {
    if (location.getName() == null || location.getName().isEmpty()) {
      throw new IllegalArgumentException("No name");
    }
    if (location.getLon() == null) {
      throw new IllegalArgumentException("Missing longitude");
    }
    if (location.getLon().isNaN()) {
      throw new IllegalArgumentException("Invalid longitude");
    }
    if (location.getLat() == null) {
      throw new IllegalArgumentException("Missing latitude");
    }
    if (location.getLat().isNaN()) {
      throw new IllegalArgumentException("Invalid latitude");
    }
    if (location.getType() == null) {
      throw new IllegalArgumentException("Missing type");
    }
    return locationsRepository.save(location);
  }

  @Override
  public Location updateLocation(Location location) {
    if (location.getId() == null) {
      throw new IllegalArgumentException("Missing id");
    }
    return checkAndSaveLocation(location);
  }

  public List<Location> importFromCsv(MultipartFile file, CSVFormat format) {
    try {
      String name =
          file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4);
      List<Location> locations = CSVHelper.csvToLocations(file.getInputStream(), name, format);
      locationsRepository.saveAll(locations);
      return locations;
    } catch (NullPointerException | IOException e ) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  @Override
  public LocationFullInfo getLocation(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("Missing id");
    }
    String token = getToken();
    Location location = locationsRepository.getById(id);
    if (token != null &&
        userRepository.findUserByEmail(token).getFavouriteLocations().contains(location)) {
      return new LocationFullInfo(location, false);
    }
    return null;
  }


  @Override
  public List<DescriptionlessLocation> getMarkers(String query, String[] types,
                                                  boolean isFavourite) {
    List<Location> locationsToReturn;
    if (query == null && types == null) {
      locationsToReturn = locationsRepository.findAll();
    } else if (query == null) {
      List<LocationType> locationTypes = convertToTypeFromString(types);
      locationsToReturn = locationsRepository.findLocationsByTypeIsIn(locationTypes);
    } else if (types == null) {
      locationsToReturn = locationsRepository.findLocationsByNameContainingIgnoreCase(query);
    } else {
      List<LocationType> locationTypes = convertToTypeFromString(types);
      locationsToReturn =
          locationsRepository.findLocationsByTypeIsInAndNameContainingIgnoreCase(locationTypes,
              query);
    }
    if (isFavourite) {
      String token = getToken();
      if (token != null) {
        List<Location> favouriteLocations =
            userRepository.findUserByEmail(token).getFavouriteLocations();
        return convertToDescriptionless(
            locationsToReturn.stream().filter(favouriteLocations::contains)
                .collect(Collectors.toList()));
      }
    }
    return convertToDescriptionless(locationsToReturn);
  }

  @Override
  public void deleteLocationById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("Missing id");
    }
    locationsRepository.deleteById(id);
  }

  @Override
  public List<DescriptionlessLocation> convertToDescriptionless(List<Location> locations) {
    return locations.stream()
        .map(location -> new DescriptionlessLocation(location.getId(), location.getName(),
              location.getLon(), location.getLat(), location.getType()))
        .collect(Collectors.toList());
  }

  @Override
  public List<LocationType> convertToTypeFromString(String[] locationTypes) {
    return Arrays.stream(locationTypes).map(LocationType::valueOf).collect(Collectors.toList());
  }

}
