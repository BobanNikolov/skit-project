package integration;

import com.example.explore_buddy.ExploreBuddyApplication;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.repository.IUserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static support.TestObjectGenerator.generateAppUser;

@Testcontainers
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = UserTest.DataSourceInitializer.class)
@SpringBootTest(classes = {ExploreBuddyApplication.class})
public class UserTest {
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
  private IUserRepository userRepository;

  @Test
  void testSaveUser() {
    var user = generateAppUser();

    final var result = userRepository.save(user);

    assertNotNull(result.getEmail());
    assertNotNull(result);
  }

  @Test
  void testFindAllUsers() {
    var user = generateAppUser();

    userRepository.save(user);

    final var result = userRepository.findAll();

    assertNotNull(result);
    assertTrue(result.size() > 0);
  }

  @Test
  void testFindUserByEmail() {
    var user = generateAppUser();

    userRepository.save(user);

    final var result = userRepository.findUserByEmail(user.getEmail());

    assertNotNull(result.getEmail());
    assertNotNull(result);
    assertEquals(user.getEmail(), result.getEmail());
  }

  @Test
  void testEnableUser() {
    var user = generateAppUser();

    userRepository.save(user);

    userRepository.enableUser(user.getEmail());
    userRepository.flush();

    assertTrue(user.isEnabled());
  }
}
