package fr.latlon.util.rest;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public class ResponseUtils {

    private static final String ERROR_KEY = "errors";
    private static final String DATA_KEY = "data";

    /**
     * Creates an empty {@link javax.ws.rs.core.Response} with status 400
     * @return
     */
    public static Response createBadRequestResponse() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    /**
     * Creates a bad request {@link javax.ws.rs.core.Response}.
     * Status code: 400.
     *
     * Holds information such as, the invalid field, and a describing message,
     * which are contained by a {@link fr.latlon.util.rest.ErrorItem}.
     *
     * {
     *      "errors" : [
     *          {
     *              "field" : "username",
     *              "message" : "invalid characters"
     *          },
     *          {
     *              "field" : "password",
     *              "message" : "invalid chars"
     *          }
     *     ]
     * }
     *
     * @param errorList
     * @return
     */
    public static Response createBadRequestResponse(List<ErrorItem> errorList) {
        Map<String, List<ErrorItem>> responseMap = new HashMap<String, List<ErrorItem>>();
        responseMap.put(ERROR_KEY, errorList);

        return Response.status(Response.Status.BAD_REQUEST).entity(responseMap).build();
    }


    /**
     * Creates a successful {@link javax.ws.rs.core.Response}.
     * Status code: 200.
     *
     * {
     *     "data" : {
     *         "user" : {
     *             "username" : "java",
     *             "password" : "development"
     *         }
     *     }
     * }
     *
     *
     */
    public static <K> Response createSuccessfulResponse(K data) {
        Map<String, K> response = new HashMap<String, K>();
        response.put(DATA_KEY, data);

        return Response.status(Response.Status.OK).entity(response).build();
    }

    public static Response createSuccessfulResponse() {
        return Response.status(Response.Status.OK).build();
    }

}
