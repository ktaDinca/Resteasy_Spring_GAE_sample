package fr.latlon.service.location;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import fr.latlon.exception.AuthenticationFailureException;
import fr.latlon.model.Location;
import fr.latlon.service.memcached.SyncMemcachedService;
import fr.latlon.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */

@Service
public class LocationService {

    @Value("${cache.location.key.suffix}")
    private String LOCATION_KEY_SUFFIX;

    @Value("${cache.location.exp.time}")
    private Integer LOCATION_EXP_TIME;

    @Autowired
    private UserService userService;

    @Autowired
    private SyncMemcachedService memcachedService;


    /**
     * Saves user's location in the Memcache.
     * {"userid_loc":{location}}
     *
     * @param userid
     * @param latitude
     * @param longitude
     * @throws AuthenticationFailureException
     */
    public void processLocationUpdate(Long userid, Float latitude, Float longitude) throws AuthenticationFailureException {
        if (!userService.assertUserIsReal()) {
            throw new AuthenticationFailureException();
        }
        putLocation(userid, latitude, longitude);
    }

    private void putLocation(Long userid, Float latitude, Float longitude) {
        memcachedService.getMemcache().put(buildLocationKey(userid), new Location(latitude, longitude), Expiration.byDeltaSeconds(LOCATION_EXP_TIME), MemcacheService.SetPolicy.SET_ALWAYS);
    }

    private Location getLocation(Long userid) {
        return (Location) memcachedService.getMemcache().get(buildLocationKey(userid));
    }

    private String buildLocationKey(Long userid) {
        return new StringBuilder(userid + LOCATION_KEY_SUFFIX).toString();
    }

    public Map<Long, Location> processFetchLocations(Long userid) throws AuthenticationFailureException {
        if (!userService.assertUserIsReal()) {
            throw new AuthenticationFailureException();
        }

        List<Long> friendsList = userService.getFriendsList(userid);
        Map<Long, Location> locations = new HashMap<Long, Location>();
        for (Long friend : friendsList) {
            locations.put(friend, getLocation(friend));
        }

        return locations;
    }

}
