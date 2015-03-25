package fr.latlon.service.user;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import fr.latlon.dao.UserDAO;
import fr.latlon.model.User;
import fr.latlon.service.memcached.SyncMemcachedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Service
public class UserService {

    @Value("${test.totalusers}")
    private Integer totalUsers;

    @Value("${test.maxnroffriends}")
    private Integer maxFriendsPerUser;

    @Value("${cache.friends.key.suffix}")
    private String FRIENDS_KEY_SUFFIX;

    @Value("${cache.friends.exp.time}")
    private Integer FRIENDS_EXP_TIME;

    @Autowired
    private SyncMemcachedService memcachedService;

    @Autowired
    private UserDAO userDAO;


    /**
     * TODO: define the algorithm
     *
     * Checks in the datastore to ensure the user is real
     */
    public boolean assertUserIsReal() {
        return true;
    }

    public List<Long> getFriendsList(Long userid) {
        return (List<Long>)memcachedService.getMemcache().get(buildFriendsKey(userid));
    }

    public void putFriendsList(Long userid, List<Long> friendsList) {
        memcachedService.getMemcache().put(buildFriendsKey(userid), friendsList, Expiration.byDeltaSeconds(FRIENDS_EXP_TIME), MemcacheService.SetPolicy.SET_ALWAYS);
    }

    private String buildFriendsKey(Long userid) {
        return new StringBuilder(userid + FRIENDS_KEY_SUFFIX).toString();
    }

    public void saveUser(User user) {
        if (user != null) {
            userDAO.saveUser(user);
        }
    }

    public void generateRandomFriends(Long userid) {
        SecureRandom rand = new SecureRandom();

        int friends = rand.nextInt(maxFriendsPerUser);
        List<Long> friendList = new ArrayList<Long>();

        for (int i = 0; i < friends; i ++) {
            friendList.add(new Long(rand.nextInt(totalUsers)));
        }

        putFriendsList(userid, friendList);
    }
}
