package services;

import models.Data;
import models.Index;
import models.Sensor;
import models.Station;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract class implements IApi
 */
public abstract class Api implements IApi {
    private List<Station> stations;
    private Map<Integer, List<Sensor>> sensors;
    private Map<Integer, Data> data;
    private Map<Integer, Index> indexes;
    private LocalDateTime lastTime;

    /**
     * Action on isActual
     * @return boolean
     */
    private boolean isActual() {
        if (!getActualStatus()) {
            stations = null;
            sensors = new TreeMap<>();
            data = new TreeMap<>();
            indexes = new TreeMap<>();
            lastTime = LocalDateTime.now();
            return false;
        }
        return true;
    }

    /**
     * Is data actual
     * @return Boolean
     */
    public boolean getActualStatus() {
        if (lastTime == null || lastTime.plusHours(1).isBefore(LocalDateTime.now())) return false;
        return true;
    }

    /**
     * Api constructor
     */
    Api() {
        sensors = new TreeMap<>();
        data = new TreeMap<>();
        indexes = new TreeMap<>();
        load();
    }

    /**
     * Save data to cache
     */
    private void save() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("cache.bin"))) {
            outputStream.writeObject(stations);
            outputStream.writeObject(sensors);
            outputStream.writeObject(data);
            outputStream.writeObject(indexes);
            outputStream.writeObject(lastTime);
        } catch (Exception ex) {
            System.err.println("Failed to save data");
        }
    }

    /**
     * Load data from cache
     */
    private void load() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("cache.bin"))) {
            stations = (List<Station>) inputStream.readObject();
            sensors = (TreeMap<Integer, List<Sensor>>) inputStream.readObject();
            data = (TreeMap<Integer, Data>) inputStream.readObject();
            indexes = (TreeMap<Integer, Index>) inputStream.readObject();
            lastTime = (LocalDateTime) inputStream.readObject();
        } catch (Exception ex) {
            System.err.println("Failed to load data");
        }
        System.out.println("Last update time: " + lastTime);
    }

    /**
     * Get last update time
     * @return LocalDateTime
     */
    public LocalDateTime getLastTime() {
        return lastTime;
    }

    @Override
    public String getContentFromUrl(String url) throws Exception {

        URL con = new URL(url);
        URLConnection request = con.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader
                (request.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    @Override
    public List<Station> findAllCached() throws Exception {

        if (stations != null && isActual()) return stations;
        stations = findAll();
        save();
        return stations;
    }

    @Override
    public List<Sensor> getSensorsCached(int stationId) throws Exception {

        List<Sensor> sensorList = sensors.get(stationId);
        if (sensorList != null && isActual()) return sensorList;
        sensors.put(stationId, getSensors(stationId));
        save();
        return sensors.get(stationId);
    }

    @Override
    public Data getDataCached(int sensorId) throws Exception {

        Data dataList = data.get(sensorId);
        if (dataList != null && isActual()) return dataList;
        data.put(sensorId, getData(sensorId));
        save();
        return data.get(sensorId);
    }

    @Override
    public Index getIndexCached(int stationId) throws Exception {

        Index index = indexes.get(stationId);
        if (index != null && isActual()) return index;
        indexes.put(stationId, getIndex(stationId));
        save();
        return indexes.get(stationId);
    }
}
