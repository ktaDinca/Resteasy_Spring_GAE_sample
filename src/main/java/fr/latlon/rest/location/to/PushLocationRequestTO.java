package fr.latlon.rest.location.to;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public class PushLocationRequestTO {

    private Long userid;
    private Float longitude;
    private Float latitude;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
