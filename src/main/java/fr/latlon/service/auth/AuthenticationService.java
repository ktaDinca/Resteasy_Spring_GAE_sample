package fr.latlon.service.auth;

import fr.latlon.exception.TokenExchangeFailedException;
import fr.latlon.exception.UserAuthFailedException;
import fr.latlon.model.User;
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


    /**
     * Processes a short-lived token
     * @param slToken
     */
    public void processAuth(String slToken) throws UserAuthFailedException, TokenExchangeFailedException {
        User user = facebookService.getUserInfo(slToken);
        if (user == null) {
            throw new UserAuthFailedException();
        }

        String accessToken = facebookService.getPermanentToken(slToken);
        if (accessToken == null) {
            throw new TokenExchangeFailedException();
        }
        user.setAccessToken(accessToken);

    }
}
