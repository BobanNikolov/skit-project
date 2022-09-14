package com.example.explore_buddy.service;

import com.example.explore_buddy.ExploreBuddyApplication;
import com.example.explore_buddy.helpers.CSVHelper;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.repository.IUserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static support.TestObjectGenerator.generateAppUser;
import static support.TestObjectGenerator.generateLocation;

class LocationsServiceTest {
  private ILocationsRepository locationsRepository;
  private IUserRepository userRepository;
  private MultipartFile mockFile;
  private LocationsService instanceUnderTest;

  @BeforeEach
  void init() {
    mockFile = mock(MultipartFile.class);
    locationsRepository = mock(ILocationsRepository.class);
    userRepository = mock(IUserRepository.class);
    instanceUnderTest = new LocationsService(locationsRepository, userRepository);
  }

  @Test
  void testGetAll() {
    var location1 = generateLocation();
    location1.setName("location1");
    var location2 = generateLocation();
    location2.setName("location2");
    var location3 = generateLocation();
    location3.setName("location3");

    var locations = List.of(location3, location1, location2);

    when(locationsRepository.findAll()).thenReturn(locations);

    final var result = instanceUnderTest.getAll();

    assertEquals("location3", result.get(0).getName());
    assertEquals("location1", result.get(1).getName());
    assertEquals("location2", result.get(2).getName());
    verify(locationsRepository).findAll();
  }

  @Test
  void testCheckAndSaveLocationNameNull() {
    var location = generateLocation();
    location.setName(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocationNameEmpty() {
    var location = generateLocation();
    location.setName("");

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocationLonNull() {
    var location = generateLocation();
    location.setLon(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocationLatNull() {
    var location = generateLocation();
    location.setLat(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocationLonNan() {
    var location = generateLocation();
    location.setLon(Double.NaN);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocationLatNan() {
    var location = generateLocation();
    location.setLat(Double.NaN);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocationTypeNull() {
    var location = generateLocation();
    location.setType(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.checkAndSaveLocation(location);
    });
  }

  @Test
  void testCheckAndSaveLocation() {
    var location = generateLocation();

    when(locationsRepository.save(any(Location.class))).thenReturn(location);

    final var result = this.instanceUnderTest.checkAndSaveLocation(location);

    assertNotNull(result);
    assertEquals(location.getId(), result.getId());
    verify(locationsRepository).save(any(Location.class));
  }

  @Test
  void testPostLocationNameNull() {
    var location = generateLocation();
    location.setName(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocationNameEmpty() {
    var location = generateLocation();
    location.setName("");

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocationLonNull() {
    var location = generateLocation();
    location.setLon(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocationLatNull() {
    var location = generateLocation();
    location.setLat(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocationLonNan() {
    var location = generateLocation();
    location.setLon(Double.NaN);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocationLatNan() {
    var location = generateLocation();
    location.setLat(Double.NaN);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocationTypeNull() {
    var location = generateLocation();
    location.setType(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.post(location);
    });
  }

  @Test
  void testPostLocation() {
    var location = generateLocation();

    when(locationsRepository.save(any(Location.class))).thenReturn(location);

    final var result = this.instanceUnderTest.post(location);

    assertNotNull(result);
    assertEquals(location.getId(), result.getId());
    verify(locationsRepository).save(any(Location.class));
  }

  @Test
  void testUpdateLocationIdNull() {
    var location = generateLocation();

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationNameNull() {
    var location = generateLocation();
    location.setName(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationNameEmpty() {
    var location = generateLocation();
    location.setName("");

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationLonNull() {
    var location = generateLocation();
    location.setLon(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationLatNull() {
    var location = generateLocation();
    location.setLat(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationLonNan() {
    var location = generateLocation();
    location.setLon(Double.NaN);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationLatNan() {
    var location = generateLocation();
    location.setLat(Double.NaN);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocationTypeNull() {
    var location = generateLocation();
    location.setType(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.updateLocation(location);
    });
  }

  @Test
  void testUpdateLocation() {
    var location = generateLocation();
    location.setId(11);

    when(locationsRepository.save(any(Location.class))).thenReturn(location);

    final var result = this.instanceUnderTest.updateLocation(location);

    assertNotNull(result);
    assertEquals(location.getId(), result.getId());
    verify(locationsRepository).save(any(Location.class));
  }

  @Test
  void testGetLocationNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.getLocation(null);
    });
  }

  @Test
  void testGetLocationTokenNull() {
    var location = generateLocation();
    location.setId(1);
    var location1 = generateLocation();
    location1.setId(2);
    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(null);
      when(locationsRepository.getById(anyInt())).thenReturn(location);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);

      final var result = this.instanceUnderTest.getLocation(2);

      assertNull(result);
    }
  }

  @Test
  void testGetLocation() {
    var location = generateLocation();
    location.setId(1);
    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(token);
      when(locationsRepository.getById(anyInt())).thenReturn(location);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);

      final var result = this.instanceUnderTest.getLocation(1);

      assertNotNull(result);
      verify(locationsRepository).getById(anyInt());
      verify(userRepository).findUserByEmail(anyString());
    }
  }

  @Test
  void testGetLocationNullFinal() {
    var location = generateLocation();
    location.setId(1);
    var location1 = generateLocation();
    location1.setId(2);
    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(token);
      when(locationsRepository.getById(anyInt())).thenReturn(location);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);

      final var result = this.instanceUnderTest.getLocation(2);

      assertNull(result);
    }
  }

  @Test
  void testGetMarkersQueryNullIsFavouriteFalse() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);
    String[] types = new String[2];
    types[0] = "CAVE";
    types[1] = "WATERFALL";

    var locations = List.of(location1, location2, location3);

    when(locationsRepository.findLocationsByTypeIsIn(anyList())).thenReturn(locations);

    final var result = this.instanceUnderTest.getMarkers(null, types, false);

    assertNotNull(result);
    verify(locationsRepository).findLocationsByTypeIsIn(anyList());
  }

  @Test
  void testGetMarkersTypesNullIsFavouriteFalse() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    when(locationsRepository.findLocationsByNameContainingIgnoreCase(anyString())).thenReturn(locations);

    final var result = this.instanceUnderTest.getMarkers("test", null, false);

    assertNotNull(result);
    verify(locationsRepository).findLocationsByNameContainingIgnoreCase(anyString());
  }

  @Test
  void testGetMarkersBothNullIsFavouriteFalse() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    when(locationsRepository.findAll()).thenReturn(locations);

    final var result = this.instanceUnderTest.getMarkers(null, null, false);

    assertNotNull(result);
    verify(locationsRepository).findAll();
  }

  @Test
  void testGetMarkersBothNotNullIsFavouriteFalse() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);
    String[] types = new String[2];
    types[0] = "CAVE";
    types[1] = "WATERFALL";

    var locations = List.of(location1, location2, location3);

    when(locationsRepository.findLocationsByTypeIsInAndNameContainingIgnoreCase(anyList(),anyString())).thenReturn(locations);

    final var result = this.instanceUnderTest.getMarkers("test", types, false);

    assertNotNull(result);
    verify(locationsRepository).findLocationsByTypeIsInAndNameContainingIgnoreCase(anyList(),anyString());
  }

  @Test
  void testGetMarkersBothNullIsFavouriteTrue() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(token);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);
      when(locationsRepository.findAll()).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers(null, null, true);

      assertNotNull(result);
      verify(locationsRepository).findAll();
      verify(userRepository).findUserByEmail(anyString());
    }
  }

  @Test
  void testGetMarkersQueryNullIsFavouriteTrue() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);
    String[] types = new String[2];
    types[0] = "CAVE";
    types[1] = "WATERFALL";

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(token);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);
      when(locationsRepository.findLocationsByTypeIsIn(anyList())).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers(null, types, true);

      assertNotNull(result);
      verify(locationsRepository).findLocationsByTypeIsIn(anyList());
      verify(userRepository).findUserByEmail(anyString());
    }
  }

  @Test
  void testGetMarkersTypesNullIsFavouriteTrue() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(token);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);
      when(locationsRepository.findLocationsByNameContainingIgnoreCase(anyString())).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers("test", null, true);

      assertNotNull(result);
      verify(locationsRepository).findLocationsByNameContainingIgnoreCase(anyString());
      verify(userRepository).findUserByEmail(anyString());
    }
  }

  @Test
  void testGetMarkersBothNotNullIsFavouriteTrue() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);
    String[] types = new String[2];
    types[0] = "CAVE";
    types[1] = "WATERFALL";

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(token);
      when(userRepository.findUserByEmail(anyString())).thenReturn(user);
      when(locationsRepository.findLocationsByTypeIsInAndNameContainingIgnoreCase(anyList(), anyString())).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers("test", types, true);

      assertNotNull(result);
      verify(locationsRepository).findLocationsByTypeIsInAndNameContainingIgnoreCase(anyList(), anyString());
      verify(userRepository).findUserByEmail(anyString());
    }
  }

  @Test
  void testGetMarkersBothNullIsFavouriteTrueTokenNull() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(null);
      when(locationsRepository.findAll()).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers(null, null, true);

      assertNotNull(result);
      verify(locationsRepository).findAll();
    }
  }

  @Test
  void testGetMarkersQueryNullIsFavouriteTrueTokenNull() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);
    String[] types = new String[2];
    types[0] = "CAVE";
    types[1] = "WATERFALL";

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(null);
      when(locationsRepository.findLocationsByTypeIsIn(anyList())).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers(null, types, true);

      assertNotNull(result);
      verify(locationsRepository).findLocationsByTypeIsIn(anyList());
    }
  }

  @Test
  void testGetMarkersTypesNullIsFavouriteTrueTokenNull() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(null);
      when(locationsRepository.findLocationsByNameContainingIgnoreCase(anyString())).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers("test", null, true);

      assertNotNull(result);
      verify(locationsRepository).findLocationsByNameContainingIgnoreCase(anyString());
    }
  }

  @Test
  void testGetMarkersBothNotNullIsFavouriteTrueTokenNull() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);
    String[] types = new String[2];
    types[0] = "CAVE";
    types[1] = "WATERFALL";

    var locations = List.of(location1, location2, location3);

    var user = generateAppUser();
    user.setFavouriteLocations(List.of(location1));
    var token = user.getEmail();

    try (MockedStatic<ExploreBuddyApplication> utilities = mockStatic(ExploreBuddyApplication.class)) {
      utilities.when(ExploreBuddyApplication::getToken).thenReturn(null);
      when(locationsRepository.findLocationsByTypeIsInAndNameContainingIgnoreCase(anyList(), anyString())).thenReturn(locations);

      final var result = this.instanceUnderTest.getMarkers("test", types, true);

      assertNotNull(result);
      verify(locationsRepository).findLocationsByTypeIsInAndNameContainingIgnoreCase(anyList(), anyString());
    }
  }


  @Test
  void testDeleteLocationByIdNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.deleteLocationById(null);
    });
  }

  @Test
  void testDeleteLocationById() {
    this.instanceUnderTest.deleteLocationById(15);

    verify(locationsRepository, times(1)).deleteById(anyInt());
  }

  @Test
  void testConvertToDescriptionless() {
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    var location3 = generateLocation();
    location3.setId(3);

    var locations = List.of(location1, location2, location3);

    final var result = this.instanceUnderTest.convertToDescriptionless(locations);

    assertNotNull(result);
  }

  @Test
  void testConvertToTypeFromString() {
    var type1 = "CAVE";
    var type2 = "WATERFALL";
    String[] locationTypes = new String[2];
    locationTypes[0] = type1;
    locationTypes[1] = type2;

    final var result = this.instanceUnderTest.convertToTypeFromString(locationTypes);

    assertNotNull(result);
  }

  @Test
  void testImportFromCsv() throws IOException {
    File f = new File("src/test/resources/waterfall.csv");
    MockMultipartFile file =
        new MockMultipartFile("file", "waterfall.csv", "text/csv",
            new FileInputStream(f));

    final var result = this.instanceUnderTest.importFromCsv(file, CSVFormat.DEFAULT);

    assertNotNull(result);
    verify(locationsRepository).saveAll(anyList());
  }

  @Test
  void testImportFromCsvException() throws IOException {
    File f = new File("src/test/resources/waterfall.txt");
    MockMultipartFile file =
        new MockMultipartFile("file", "waterfall.txt", "text/plain",
            new FileInputStream(f));

    CSVParser csvParser = mock(CSVParser.class);

    doThrow(IOException.class).when(csvParser).getRecords();

    assertThrows(RuntimeException.class, () -> {
      this.instanceUnderTest.importFromCsv(file, null);
    });
  }
}