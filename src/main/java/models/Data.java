package models;

import java.io.Serializable;

/**
 * Data model
 */
public class Data implements Serializable {
    private String key;
    private Measurement[] values;

    public Data(String key, Measurement[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public Measurement[] getValues() {
        return values;
    }
}
