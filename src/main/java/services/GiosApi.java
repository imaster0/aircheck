package services;

import com.google.gson.Gson;
import models.Data;
import models.Index;
import models.Sensor;
import models.Station;

import java.util.Arrays;
import java.util.List;

/**
 * Connects to Gios API
 */
public class GiosApi extends Api {
    private final String  CONNECTION_URL = "http://api.gios.gov.pl/pjp-api/rest/";

    /**
     * Get all stations
     * @return List of stations
     * @throws Exception
     */
    public List<Station> findAll() throws Exception {
        String sURL = CONNECTION_URL + "station/findAll";

        // Connect to the URL using java's native library
        String json = getContentFromUrl(sURL);

        // Convert JSON to List<Station>
        Gson gson = new Gson();
        Station[] response = gson.fromJson(json, Station[].class);
        return Arrays.asList(response);
    }

    public List<Sensor> getSensors(int stationId) throws Exception {
        String sURL = CONNECTION_URL + "station/sensors/" + stationId;

        // Connect to the URL using java's native library
        String json = getContentFromUrl(sURL);

        // Convert JSON to List<Sensor>
        Gson gson = new Gson();
        Sensor[] response = gson.fromJson(json, Sensor[].class);
        return Arrays.asList(response);
    }

    public Data getData(int sensorId) throws Exception {
        String sURL = CONNECTION_URL + "data/getData/" + sensorId;

        // Connect to the URL using java's native library
        String json = getContentFromUrl(sURL);

        // Convert JSON to List<Sensor>
        Gson gson = new Gson();
        Data response = gson.fromJson(json, Data.class);
        return response;
    }

    public Index getIndex(int stationId) throws Exception {
        String sURL = CONNECTION_URL + "aqindex/getIndex/" + stationId;

        // Connect to the URL using java's native library
        String json = getContentFromUrl(sURL);

        // Convert JSON to List<Sensor>
        Gson gson = new Gson();
        Index response = gson.fromJson(json, Index.class);

        return response;
    }
}
