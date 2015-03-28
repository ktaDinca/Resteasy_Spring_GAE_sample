package fr.latlon.model;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public class User {

    private String accessToken;
    private SocialInfo socialInfo;
    private String lastname;
    private String firstname;
    private String email;

    public User() {
    }

    public User(String accessToken, SocialInfo socialInfo, String lastname, String firstname, String email) {
        this.accessToken = accessToken;
        this.socialInfo = socialInfo;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public SocialInfo getSocialInfo() {
        return socialInfo;
    }

    public void setSocialInfo(SocialInfo socialInfo) {
        this.socialInfo = socialInfo;
    }
}
