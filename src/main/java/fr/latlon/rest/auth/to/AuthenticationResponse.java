package fr.latlon.rest.auth.to;

/**
 *
 *
 * @author Alexandru Dinca (alexandru.dinca2110@gmail.com)
 * @since 24/03/15
 */
public class AuthenticationResponse {

    private String accessToken;
    private Long id;

    public AuthenticationResponse(String accessToken, Long id) {
        this.accessToken = accessToken;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
