package models;

import java.io.Serializable;

/**
 * Station model
 */
public class Station implements Serializable {
    private int id;
    private String stationName;
    private double gegrLat;
    private double gegrLon;
    private City city;
    private Object addressStreet;

    public Station(int id, String stationName, double gegrLat, double gegrLon, City city, Object addressStreet) {
        this.id = id;
        this.stationName = stationName;
        this.gegrLat = gegrLat;
        this.gegrLon = gegrLon;
        this.city = city;
        this.addressStreet = addressStreet;
    }

    public int getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public double getGegrLat() {
        return gegrLat;
    }

    public double getGegrLon() {
        return gegrLon;
    }

    public City getCity() {
        return city;
    }

    public Object getAddressStreet() {
        return addressStreet;
    }


}
