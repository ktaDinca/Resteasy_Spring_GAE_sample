package fr.latlon.util;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public class MiscelaneousUtils {

    public static boolean isEmptyOrNull(String token) {
        if (token == null || token.length() < 1) {
            return true;
        }
        return false;
    }
}
