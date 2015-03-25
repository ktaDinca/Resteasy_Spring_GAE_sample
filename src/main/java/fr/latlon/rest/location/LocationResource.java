package fr.latlon.rest.location;

import fr.latlon.exception.AuthenticationFailureException;
import fr.latlon.model.Location;
import fr.latlon.rest.location.to.PushLocationRequestTO;
import fr.latlon.service.location.LocationService;
import fr.latlon.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Component
@Path("/location")
@Consumes("application/json")
@Produces("application/json")
public class LocationResource {

    @Autowired
    private LocationService locationService;

    @PUT
    public Response pushLocation(PushLocationRequestTO request) {
        try {
            locationService.processLocationUpdateRequest(request);
        }
        catch(AuthenticationFailureException e) {
            return ResponseUtils.createBadRequestResponse(Arrays.asList("The user provided is unknown"));
        }
        return ResponseUtils.createSuccessfulResponse();
    }


    @GET
    @Path("/{userid}")
    public Response getFriendLocations(@PathParam("userid") Long userid) {
        Map<Long, Location> friendsLocations;
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            friendsLocations = locationService.processFetchLocations(userid);
            response.put("friends", friendsLocations);
            return ResponseUtils.createSuccessfulResponse(response);
        }
        catch (AuthenticationFailureException e) {
            return ResponseUtils.createBadRequestResponse(Arrays.asList("The user provided is unknown"));
        }
    }

}
