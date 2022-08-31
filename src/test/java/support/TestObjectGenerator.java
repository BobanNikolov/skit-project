package support;

import static java.util.UUID.randomUUID;

import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.enumeration.LocationType;
import com.example.explore_buddy.model.enumeration.UserRole;
import java.util.Random;
import lombok.experimental.UtilityClass;

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
}
