package fr.latlon.rest.auth;

import javax.ws.rs.core.Response;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public interface AuthenticationResource {

    public Response auth(Object request);

}
