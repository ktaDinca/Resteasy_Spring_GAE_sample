package fr.latlon.dao;

import com.google.appengine.api.datastore.Entity;
import fr.latlon.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Repository
public class UserDAO extends BaseDAO {

    public void saveUser(User user) {
        Entity _user = new Entity(User.class.getName(), user.getId());
        _user.setProperty("id", user.getId());
        _user.setProperty("lastname", user.getLastname());
        _user.setProperty("firstname", user.getFirstname());
        _user.setProperty("email", user.getEmail());
        _user.setProperty("facebookId", user.getFacebookId());

        datastore.put(_user);
    }

}
