package services;

import models.*;

import java.util.*;

public interface IApi {
    /**
     * Get content from url
     * @param url URL specified
     * @return content (as JSON)
     * @throws Exception
     */
    String getContentFromUrl(String url) throws Exception;

    /**
     * Get list of all stations
     * @return List of stations
     * @throws Exception
     */
    List<Station> findAll() throws Exception;

    /**
     * Get all sensors for station
     * @param stationId station ID
     * @return All sensors for station with station ID
     * @throws Exception
     */
    List<Sensor> getSensors(int stationId) throws Exception;

    /**
     * Get all data for sensor
     * @param sensorId sensor ID
     * @return all data for sensor with sensor ID
     * @throws Exception
     */
    Data getData(int sensorId) throws Exception;

    /**
     * Get index for station
     * @param stationId station ID
     * @return Index for station
     * @throws Exception
     */
    Index getIndex(int stationId) throws Exception;

    /**
     * Get all stations using cache
     * @return List of stations
     * @throws Exception
     */
    List<Station> findAllCached() throws Exception;

    /**
     * Get all sensors for station using cache
     * @param stationId station ID
     * @return List of sensors
     * @throws Exception
     */
    List<Sensor> getSensorsCached(int stationId) throws Exception;

    /**
     * Get data for sensor using cache
     * @param sensorId sensor ID
     * @return Data
     * @throws Exception
     */
    Data getDataCached(int sensorId) throws Exception;

    /**
     * Get index for station using cache
     * @param stationId station ID
     * @return Index
     * @throws Exception
     */
    Index getIndexCached(int stationId) throws Exception;
}
