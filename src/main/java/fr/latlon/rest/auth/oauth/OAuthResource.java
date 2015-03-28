package fr.latlon.rest.auth.oauth;

import fr.latlon.rest.auth.to.AuthenticationResponse;
import fr.latlon.service.auth.AuthenticationService;
import fr.latlon.util.MiscelaneousUtils;
import fr.latlon.util.rest.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */

@Component
@Path("/auth")
@Consumes("application/json")
@Produces("application/json")
public class OAuthResource {

    @Autowired
    private AuthenticationService authenticationService;

    @POST
    @Path("/")
    public Response auth(OAuthRequest request) {
        if (MiscelaneousUtils.isEmptyOrNull(request.getToken())) {
            return ResponseUtils.createBadRequestResponse();
        }

        AuthenticationResponse response = authenticationService.processAuth(request.getToken());
        return Response.status(201).entity(response).build();
    }
}
