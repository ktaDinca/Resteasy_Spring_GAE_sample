package fr.latlon.service.auth;

import com.google.appengine.api.datastore.Key;
import fr.latlon.dao.entity.UserDAO;
import fr.latlon.exception.TokenExchangeFailedException;
import fr.latlon.exception.UserAuthFailedException;
import fr.latlon.model.SocialInfo;
import fr.latlon.model.User;
import fr.latlon.rest.auth.to.AuthenticationResponse;
import fr.latlon.service.auth.jwt.JsonWebTokenService;
import fr.latlon.service.social.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */

@Service
public class AuthenticationService {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JsonWebTokenService jsonWebTokenService;


    /**
     * Processes a short-lived token
     * @param slToken
     */
    public AuthenticationResponse processAuth(String slToken) throws UserAuthFailedException, TokenExchangeFailedException {
        User user = facebookService.getUserInfo(slToken);
        if (user == null) {
            throw new UserAuthFailedException();
        }

        String facebookPermToken = facebookService.getPermanentToken(slToken);
        if (facebookPermToken == null) {
            throw new TokenExchangeFailedException();
        }
        if (user.getSocialInfo() == null) {
            user.setSocialInfo(new SocialInfo());
        }
        user.getSocialInfo().setToken(facebookPermToken);

        Long userId = userDAO.saveUser(user);

        /*
         In order to authenticate the user into this API, a new Json Web Token must be
         created, with whom the further authentication process will continue.
         */
        String accessToken = jsonWebTokenService.createJsonWebToken(userId.toString());

        return new AuthenticationResponse(accessToken, userId);
    }
}
