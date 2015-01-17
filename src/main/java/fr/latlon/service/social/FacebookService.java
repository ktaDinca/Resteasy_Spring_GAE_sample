package fr.latlon.service.social;

import fr.latlon.model.User;
import fr.latlon.service.social.to.FacebookUserInfoResponseTO;
import fr.latlon.util.MappingUtils;
import fr.latlon.util.MiscelaneousUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
@Service
public class FacebookService {

    @Value("${facebook_base_url}")
    private String facebookApiURL;

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${facebook_user_info_fields}")
    private String userInfoFields;

    @Autowired
    private Mapper mapper;

    private RestTemplate restTemplate;

    public FacebookService() {
        initRestTemplate();
    }

    private void initRestTemplate() {
        this.restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }});
    }


    /**
     * Exchanges a temporary token gotten from the client,
     * for a permanent access token, which can be used at
     * any time to get user's information
     *
     * @param tempToken
     * @return permanent token (long lived token)
     */
    public String getPermanentToken(String tempToken) {

        StringBuilder llTokenResourceUrl = new StringBuilder(facebookApiURL).append("/oauth/access_token?grant_type=fb_exchange_token");
        llTokenResourceUrl.append("&client_id=").append(clientId);
        llTokenResourceUrl.append("&client_secret=").append(clientSecret);
        llTokenResourceUrl.append("&fb_exchange_token=").append(tempToken);

        String response = null;
        try {
            response = restTemplate.getForObject(llTokenResourceUrl.toString(), String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String token = extractTokenFromReponse(response);
        return token;
    }

    private String extractTokenFromReponse(String response) {
        if (MiscelaneousUtils.isEmptyOrNull(response)) {
            return null;
        }
        String[] halves = response.split("&");
        if (halves != null && halves.length > 0 && halves[0] != null) {
            String[] quarters = halves[0].split("=");
            if (quarters != null && quarters.length > 0) {
                return quarters[1];
            }
        }
        return null;
    }

    public User getUserInfo(String temporaryToken) {

        StringBuilder getUserInfoURL = new StringBuilder(facebookApiURL).append("/me");
        getUserInfoURL.append("?access_token=").append(temporaryToken);
        getUserInfoURL.append("&fields=").append(userInfoFields);

        String _response = restTemplate.getForObject(getUserInfoURL.toString(), String.class);
        FacebookUserInfoResponseTO response = MappingUtils.mapJSONStringToType(_response, FacebookUserInfoResponseTO.class);
        if (response == null) {
            return null;
        }

        User user = mapper.map(response, User.class);
        return user;
    }
}
