package models;

import java.io.Serializable;

/**
 * Commune model
 */
public class Commune implements Serializable {
    private String communeName;
    private String districtName;
    private String provinceName;

    public String getCommuneName() {
        return communeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }
}
