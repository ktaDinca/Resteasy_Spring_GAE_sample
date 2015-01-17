package fr.latlon.rest.test;

import fr.latlon.model.User;
import fr.latlon.service.user.UserService;
import fr.latlon.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Resource created for testing puposes only.
 *
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Component
@Path("/test/user")
public class TestUserResource {

    @Autowired
    private UserService userService;


    /**
     * Creates a new user and generates imaginary friends
     */
    @Path("/")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response saveUser(User request) {
        userService.saveUser(request);
        userService.generateRandomFriends(request.getId());

        return ResponseUtils.createSuccessfulResponse();
    }

}
