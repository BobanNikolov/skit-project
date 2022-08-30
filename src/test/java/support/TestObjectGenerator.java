package support;

import static java.util.UUID.randomUUID;

import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.enumeration.LocationType;
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
}
