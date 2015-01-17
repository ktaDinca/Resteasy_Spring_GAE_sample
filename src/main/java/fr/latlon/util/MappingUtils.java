package fr.latlon.util;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public class MappingUtils {

    public static <T> T mapJSONStringToType(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T response = mapper.readValue(json, type);
            return response;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
