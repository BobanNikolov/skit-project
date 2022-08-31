package com.example.explore_buddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static support.TestObjectGenerator.generateLocation;

import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.repository.IUserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationsServiceTest {
  private ILocationsRepository locationsRepository;
  private IUserRepository userRepository;
  private LocationsService instanceUnderTest;

  @BeforeEach
  void init() {
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
    locationTypes[0]  = type1;
    locationTypes[1] = type2;

    final var result = this.instanceUnderTest.convertToTypeFromString(locationTypes);

    assertNotNull(result);
  }
}