package fr.latlon.rest.test;

import fr.latlon.util.ResponseUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Component
@Path("/muie")
public class SimpleTest {

    @GET
    public Response testMic() {
        return ResponseUtils.createSuccessfulResponse();
    }
}
