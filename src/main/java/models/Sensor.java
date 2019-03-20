package models;

import java.io.Serializable;

/**
 * Sensor model
 */
public class Sensor implements Serializable {
    private int id;
    private int stationId;
    private Param param;

    public Sensor(int id, int stationId, Param param) {
        this.id = id;
        this.stationId = stationId;
        this.param = param;
    }

    public int getId() {
        return id;
    }

    public int getStationId() {
        return stationId;
    }

    public Param getParam() {
        return param;
    }
}
