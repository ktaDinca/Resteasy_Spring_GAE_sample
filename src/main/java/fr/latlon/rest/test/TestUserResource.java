package fr.latlon.rest.test;

import fr.latlon.model.User;
import fr.latlon.service.user.UserService;
import fr.latlon.util.rest.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Resource created for testing puposes only.
 *
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Component
@Path("/test/user")
@Consumes("application/json")
@Produces("application/json")
public class TestUserResource {

    @Autowired
    private UserService userService;


    /**
     * Retrieves information about a {@link fr.latlon.model.User}
     */
    @Path("/{userId}")
    @GET
    public Response getUser(@PathParam("userId") Long userId) {
        User user = userService.getUser(userId);
        return ResponseUtils.createSuccessfulResponse(user);
    }

}
