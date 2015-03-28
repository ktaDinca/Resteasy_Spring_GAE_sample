package fr.latlon.service.auth.jwt;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

/**
 *
 * Json Web Token service based on the google guava library
 *
 * @author Alexandru Dinca (alexandru.dinca2110@gmail.com)
 * @since 26/03/15
 */
@Service
public class JsonWebTokenService {

    @Value("${token.audience}")
    private String AUDIENCE;

    @Value("${token.issuer}")
    private String ISSUER;

    @Value("${token.signingkey}")
    private String SIGNING_KEY;

    @Value("${token.validity}")
    private Long DEFAULT_TOKEN_VALIDITY;

    private static final String TOKEN_DATA_KEY = "data";
    private static final String TOKEN_USER_ID_KEY = "user_id";

    public String createJsonWebToken(String userId) {
        return createJsonWebToken(userId, DEFAULT_TOKEN_VALIDITY);
    }

    /**
     * Creates a json web token which is a digitally signed token that contains a payload (e.g. userId to identify
     * the user). The signing key MUST be kept secret.
     *
     * @return - the token
     */
    public String createJsonWebToken(String userId, Long durationDays)    {

        Calendar cal = Calendar.getInstance();
        HmacSHA256Signer signer;
        try {
            signer = new HmacSHA256Signer(ISSUER, null, SIGNING_KEY.getBytes());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
        token.setAudience(AUDIENCE);
        token.setIssuedAt(new Instant(cal.getTimeInMillis()));
        token.setExpiration(new Instant(cal.getTimeInMillis() + 1000L * 60L * 60L * 24L * durationDays));

        //Configure request object, which provides information of the item
        JsonObject request = new JsonObject();
        request.addProperty(TOKEN_USER_ID_KEY, userId);

        JsonObject payload = token.getPayloadAsJsonObject();
        payload.add(TOKEN_DATA_KEY, request);

        try {
            return token.serializeAndSign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifies a json web token's validity and extracts the user id and other information from it.
     * @param token
     * @return userId
     */
    public String verifyToken(String token)
    {
        try {
            final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());

            VerifierProvider hmacLocator = new VerifierProvider() {

                @Override
                public List<Verifier> findVerifier(String id, String key){
                    return Lists.newArrayList(hmacVerifier);
                }
            };
            VerifierProviders locators = new VerifierProviders();
            locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);
            net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker(){

                @Override
                public void check(JsonObject payload) throws SignatureException {
                    // don't throw - allow anything
                }

            };

            JsonTokenParser parser = new JsonTokenParser(locators, checker);
            JsonToken _token;
            try {
                _token = parser.verifyAndDeserialize(token);
            } catch (SignatureException e) {
                e.printStackTrace();
                return null;
            }

            JsonObject payload = _token.getPayloadAsJsonObject();

            return payload.getAsJsonObject(TOKEN_DATA_KEY).getAsJsonPrimitive(TOKEN_USER_ID_KEY).getAsString();

        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
            return null;
        }
    }

}
