package fr.latlon.service.auth;

import fr.latlon.dao.UserDAO;
import fr.latlon.exception.TokenExchangeFailedException;
import fr.latlon.exception.UserAuthFailedException;
import fr.latlon.model.User;
import fr.latlon.rest.auth.to.AuthenticationResponse;
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

        /*
         TODO: generate a JWToken and send it back
         This is actually the social token used for connecting with the social networks
         for user's information.

         In order to authenticate the user into this API, a new Json Web Token must be
         created, with whom the further authentication process will continue.
         */
        String accessToken = facebookPermToken;
        user.setAccessToken(facebookPermToken);

        user.setFacebookToken(facebookPermToken);
        userDAO.saveUser(user);

        // TODO: get the real user_id and send it back to the client
        return new AuthenticationResponse(accessToken, 1L);
    }
}
