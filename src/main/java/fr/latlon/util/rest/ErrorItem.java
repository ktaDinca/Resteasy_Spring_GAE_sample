package fr.latlon.util.rest;

/**
 * @author Alexandru Dinca (alexandru.dinca2110@gmail.com)
 * @since 26/03/15
 */
public class ErrorItem {

    private String field;
    private String message;

    public ErrorItem() {
    }

    public ErrorItem(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
