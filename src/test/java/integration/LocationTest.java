package integration;

import com.example.explore_buddy.ExploreBuddyApplication;
import com.example.explore_buddy.model.enumeration.LocationType;
import com.example.explore_buddy.repository.ILocationsRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static support.TestObjectGenerator.generateLocation;

@Testcontainers
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = LocationTest.DataSourceInitializer.class)
@SpringBootTest(classes = {ExploreBuddyApplication.class})
class LocationTest {

  @Container
  private static final PostgreSQLContainer<?> database =
      new PostgreSQLContainer<>("postgres:12.12-alpine");

  public static class DataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + database.getJdbcUrl(),
          "spring.datasource.username=" + database.getUsername(),
          "spring.datasource.password=" + database.getPassword()
      );
    }
  }

  @Autowired
  private ILocationsRepository locationsRepository;

  @Test
  void testSaveLocation() {
    var location = generateLocation();

    final var result = locationsRepository.save(location);

    assertNotNull(result.getId());
    assertNotNull(result);
  }

  @Test
  void testSaveAllLocations() {
    var location1 = generateLocation();
    var location2 = generateLocation();

    final var result = locationsRepository.saveAll(List.of(location1, location2));

    assertNotNull(result);
    assertTrue(result.contains(location1));
    assertTrue(result.contains(location2));
  }

  @Test
  void testGetLocationById() {
    var location = generateLocation();

    final var result = locationsRepository.save(location);

    var savedLocation = locationsRepository.getById(result.getId());

    assertNotNull(result.getId());
    assertNotNull(savedLocation);
  }

  @Test
  void testDeleteLocationById() {
    var location = generateLocation();

    final var result = locationsRepository.save(location);

    locationsRepository.deleteById(result.getId());
    ;
  }

  @Test
  void testGetAllLocations() {
    var location1 = generateLocation();
    var location2 = generateLocation();

    locationsRepository.saveAll(List.of(location1, location2));

    final var result = locationsRepository.findAll();

    assertNotNull(result);
    assertTrue(result.size() > 0);
  }

  @Test
  void testGetAllLocationsByLocationType() {
    var location1 = generateLocation();
    location1.setType(LocationType.LAKE);
    var location2 = generateLocation();

    final var listOfLocations = locationsRepository.saveAll(List.of(location1, location2));
    final var result = locationsRepository.findLocationsByTypeIsIn(List.of(LocationType.LAKE));

    assertNotEquals(listOfLocations, result);
    assertEquals(1, result.size());
    assertNotNull(result);
    assertEquals(location1.getId(), result.get(0).getId());
  }

  @Test
  void testGetAllLocationsByNameContainingIgnoreCase() {
    var location1 = generateLocation();
    location1.setName("test");
    var location2 = generateLocation();

    final var listOfLocations = locationsRepository.saveAll(List.of(location1, location2));
    final var result = locationsRepository.findLocationsByNameContainingIgnoreCase("te");

    assertNotEquals(listOfLocations, result);
    assertEquals(1, result.size());
    assertNotNull(result);
    assertEquals(location1.getId(), result.get(0).getId());
  }

  @Test
  void testGetAllLocationsByLocationTypeAndNameContainingIgnoreCase() {
    var location1 = generateLocation();
    location1.setName("test");
    location1.setType(LocationType.LAKE);
    var location2 = generateLocation();

    final var listOfLocations = locationsRepository.saveAll(List.of(location1, location2));
    final var result = locationsRepository.findLocationsByTypeIsInAndNameContainingIgnoreCase(
        List.of(LocationType.LAKE), "te");

    assertNotEquals(listOfLocations, result);
    assertEquals(1, result.size());
    assertNotNull(result);
    assertEquals(location1.getId(), result.get(0).getId());
  }

}
