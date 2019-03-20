package models;

import java.io.Serializable;

/**
 * Measurement model
 */
public class Measurement implements Serializable {
    private String date;
    private String value;

    public Measurement(String date, String value) {
        this.date = date;
        this.value = value;
    }

    public String getDatetime() {
        return date;
    }

    /**
     * Get date
     * @return string
     */
    public String getDate() {
        return date.substring(0, 10);
    }

    /**
     * Get hour
     * @return Integer
     */
    public Integer getHour() {
        return Integer.parseInt(date.substring(11, 13));
    }

    /**
     * Get minute
     * @return Integer
     */
    public Integer getMinute() {
        return Integer.parseInt(date.substring(14, 16));
    }


    public String getValue() {
        return value;
    }
}
