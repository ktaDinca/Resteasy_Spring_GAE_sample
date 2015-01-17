package fr.latlon.util;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public class ResponseUtils {

    private static final String ERROR_KEY = "error";

    public static Response createBadRequestResponse() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public static Response createBadRequestResponse(List<String> errorList) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(ERROR_KEY, errorList);

        return Response.status(Response.Status.BAD_REQUEST).entity(responseMap).build();
    }

    public static Response createSuccessfulResponse(Map<String, Object> map) {
        return Response.status(Response.Status.OK).entity(map).build();
    }

    public static Response createSuccessfulResponse() {
        return Response.status(Response.Status.OK).build();
    }

}
