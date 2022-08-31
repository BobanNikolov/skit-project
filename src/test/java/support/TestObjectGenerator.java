package support;

import static java.util.UUID.randomUUID;

import com.example.explore_buddy.config.LoginRequest;
import com.example.explore_buddy.config.RegistrationRequest;
import com.example.explore_buddy.config.token.ConfirmationToken;
import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.enumeration.LocationType;
import com.example.explore_buddy.model.enumeration.UserRole;

import java.time.LocalDateTime;
import java.util.Random;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.units.qual.C;

@UtilityClass
public class TestObjectGenerator {
  public static Location generateLocation() {
    var location = new Location();
    Random r = new Random();

    location.setName(randomUUID().toString().substring(0,7));
    location.setDescription(randomUUID().toString().substring(0,10));
    location.setType(LocationType.CAVE);
    location.setLat(r.nextDouble());
    location.setLon(r.nextDouble());

    return location;
  }

  public static AppUser generateAppUser() {
    var appUser = new AppUser();

    appUser.setEmail("test@test.com");
    appUser.setPassword(randomUUID().toString());
    appUser.setLocked(false);
    appUser.setEnabled(true);
    appUser.setUserRole(UserRole.ROLE_USER);

    return appUser;
  }

  public static ConfirmationToken generateConfirmationToken() {
    var confirmationToken = new ConfirmationToken();

    confirmationToken.setToken(randomUUID().toString());
    confirmationToken.setConfirmedAt(LocalDateTime.now());
    confirmationToken.setCreatedAt(LocalDateTime.now());
    confirmationToken.setExpiresAt(LocalDateTime.now());

    return confirmationToken;
  }

  public static LoginRequest generateLoginRequest() {
    return new LoginRequest(randomUUID().toString(), randomUUID().toString());
  }

  public static RegistrationRequest generateRegistrationRequest() {
    return new RegistrationRequest(randomUUID().toString(), randomUUID().toString(), UserRole.ROLE_USER);
  }
}
