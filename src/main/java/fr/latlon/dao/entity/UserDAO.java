package fr.latlon.dao.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import fr.latlon.model.SocialInfo;
import fr.latlon.model.User;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Repository
public class UserDAO extends BaseEntityDAO {

    public Long saveUser(User user) {

        Entity _user = new Entity(User.class.getName());
        _user.setProperty("firstname", user.getFirstname());
        _user.setProperty("lastname", user.getLastname());
        _user.setProperty("email", user.getEmail());
        _user.setProperty("facebook-id", user.getSocialInfo() != null ? user.getSocialInfo().getId() : "");
        _user.setProperty("facebook-token", user.getSocialInfo() != null ? user.getSocialInfo().getToken() : "");

        datastore.put(_user);

        return _user.getKey().getId();
    }

    public User getUser(Long id) throws EntityNotFoundException {
        Entity _entity = datastore.get(KeyFactory.createKey(User.class.getName(), id));

        User user = new User();
        user.setLastname((String) _entity.getProperty("lastname"));
        user.setFirstname((String) _entity.getProperty("firstname"));
        user.setSocialInfo(new SocialInfo((String)_entity.getProperty("facebook-token"), (Long) _entity.getProperty("facebook-id")));
        user.setEmail((String) _entity.getProperty("email"));

        return user;
    }

}
