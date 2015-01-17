package fr.latlon.rest.auth.oauth;

import fr.latlon.service.auth.AuthenticationService;
import fr.latlon.service.social.FacebookService;
import fr.latlon.util.MiscelaneousUtils;
import fr.latlon.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */

@Component
@Path("/auth")
public class OAuthResource {

    @Autowired
    private AuthenticationService authenticationService;

    @POST
    @Path("/")
    public Response auth(OAuthRequest request) {
        if (MiscelaneousUtils.isEmptyOrNull(request.getToken())) {
            return ResponseUtils.createBadRequestResponse();
        }

        authenticationService.processAuth(request.getToken());
        return Response.status(200).build();
    }
}
