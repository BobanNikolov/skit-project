package com.example.explore_buddy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static support.TestObjectGenerator.generateLocation;

import com.example.explore_buddy.helpers.CSVHelper;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.repository.IUserRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class LocationsServiceTest {
  private ILocationsRepository locationsRepository;
  private IUserRepository userRepository;
  private LocationsService instanceUnderTest;
  private CSVHelper csvHelper;

  @BeforeEach
  void init() {
    locationsRepository = mock(ILocationsRepository.class);
    userRepository = mock(IUserRepository.class);
    csvHelper = mock(CSVHelper.class);
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
  void testImportFromCsv() throws IOException {
    var file =
        new MockMultipartFile("waterfall.csv", new FileInputStream(new File("src/test/resources/waterfall.csv")));

    var location1 = generateLocation();
    location1.setName("location1");
    var location2 = generateLocation();
    location2.setName("location2");
    var location3 = generateLocation();
    location3.setName("location3");

    var locations = List.of(location3, location1, location2);

    when(CSVHelper.csvToLocations(file.getInputStream(), "waterfall")).thenReturn(locations);

    final var result = this.instanceUnderTest.importFromCsv(file);

    assertNotNull(result);
    verify(locationsRepository).saveAll(anyList());
  }
}