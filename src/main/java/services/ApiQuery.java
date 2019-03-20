package services;

import models.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Facade of Api with the most common queries
 */
public class ApiQuery {
    private Api api;

    public ApiQuery(Api api) {
        this.api = api;
    }

    /***
     * Get station by name
     * @param stationName Full name of the station
     * @return Station
     */
    public Station getStationByName(String stationName) {

        Station result = null;

        try {
            List<Station> allStations = api.findAllCached();
            result = allStations.stream()
                    .filter(x -> x.getStationName().equals(stationName))
                    .findFirst()
                    .orElse(null);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        if (result == null) System.err.println("No station named " + stationName);
        return result;
    }

    /***
     * Get index for the specified station
     * @param station
     * @return Index
     */
    public Index getIndexForStation(Station station) {

        if (station == null) return null;

        Index result = null;
        try {
            result = api.getIndexCached(station.getId());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        if (result == null) System.err.println("Can't get index for the station: " + station.getStationName());
        return result;
    }

    /***
     * Get all sensors for the specified station
     * @param station
     * @return List of Sensor
     */
    public List<Sensor> getSensorsForStation(Station station) {

        if (station == null) return null;

        List<Sensor> result = null;
        try {
            result = api.getSensorsCached(station.getId());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        if (result == null) System.err.println("Can't get sensors for the station: " + station.getStationName());
        return result;
    }

    /***
     * Get sensor for the specified station and parameter
     * @param station
     * @param paramName Code of the parameter (ex. CO)
     * @return Sensor
     */
    public Sensor getSensorForStationByParamName(Station station, String paramName) {

        if (station == null) return null;

        List<Sensor> allSensors = getSensorsForStation(station);
        Sensor result = allSensors.stream()
                .filter(x -> x.getParam().getParamCode().contains(paramName))
                .findFirst()
                .orElse(null);

        if (result == null) System.err.println("Can't get sensor for the param: " + paramName + ", " + station.getStationName());
        return result;
    }

    /***
     * Get data for the specified sensor
     * @param sensor
     * @return Data
     */
    public Data getDataForSensor(Sensor sensor) {

        if (sensor == null) return null;

        Data result = null;
        try {
            result = api.getDataCached(sensor.getId());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        if (result == null) System.err.println("Can't get data for the sensor: " + sensor.getParam().getParamName());
        return result;
    }

    /***
     * Get latest measurement before specified date (day, hour, minute)
     * @param data All data from sensor
     * @param day A day of the measurement
     * @param hour An hour of the measurement
     * @param minute A minute of the measurement
     * @return Measurement
     */
    public Measurement getLatestMeasurement(Data data, String day, int hour, int minute) {

        if (data == null) return null;

        List<Measurement> allMeasurements = Arrays.stream(data.getValues())
                .filter(x -> (x.getDate().equals(day) && x.getHour() * 60 + x.getMinute() <= hour * 60 + minute)
                        || (x.getDate().compareTo(day) < 0))
                .sorted(Comparator.comparing(Measurement::getDatetime))
                .collect(Collectors.toList());

        Collections.reverse(allMeasurements);

        return (allMeasurements.size() > 0 ? allMeasurements.get(0) : null);
    }

    /**
     * Get measurements between two dates
     * @param data data
     * @param startDay start day in format (yyyy-MM-dd)
     * @param startHour start hour
     * @param startMinute start minute
     * @param endDay end day in format (yyyy-MM-dd)
     * @param endHour end hour
     * @param endMinute end minute
     * @return List of Measurement
     */
    public List<Measurement> getMeasurementsBetween(Data data, String startDay, int startHour, int startMinute,
                                                    String endDay, int endHour, int endMinute) {

        if (data == null) return null;

        return Arrays.stream(data.getValues())
                .filter(x -> (x.getDate().equals(startDay) && x.getHour() * 60 + x.getMinute() >= startHour * 60 + startMinute)
                        || (x.getDate().compareTo(startDay) > 0))
                .filter(x -> (x.getDate().equals(endDay) && x.getHour() * 60 + x.getMinute() <= endHour * 60 + endMinute)
                        || (x.getDate().compareTo(endDay) < 0)).collect(Collectors.toList());
    }

    /**
     * Get average between two dates
     *
     * @param data        All data from sensor
     * @param startDay    Start day
     * @param startHour   Start hour
     * @param startMinute Start minute
     * @param endDay      End day
     * @param endHour     End hour
     * @param endMinute   End minute
     * @return double
     */
    public double getAverageBetween(Data data, String startDay, int startHour, int startMinute,
                                    String endDay, int endHour, int endMinute) {

        if (data == null) return -1;

        List<Double> allMeasurements = getMeasurementsBetween(data, startDay, startHour, startMinute, endDay, endHour, endMinute).stream()
                .map(x -> Double.parseDouble(x.getValue()))
                .collect(Collectors.toList());

        if (allMeasurements.size() > 0) {
            return allMeasurements.stream()
                    .reduce((x, y) -> x + y)
                    .get() / allMeasurements.size();
        }
        return -1;
    }

    /**
     * Get station by Id
     * @param stationId station ID
     * @return
     */
    public Station getStationById(Integer stationId) {
        Station result = null;

        try {
            List<Station> allStations = api.findAllCached();
            result = allStations.stream()
                    .filter(x -> x.getId() == stationId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        if (result == null) System.err.println("No station with id " + stationId);
        return result;
    }

    /**
     * Get all stations by names
     * @param stationNames List of names
     * @return List of Station
     */
    public List<Station> getAllStationsByNames(List<String> stationNames) {

        List<Station> stations = new ArrayList<>();
        for (String stationName : stationNames) {
            stations.add(getStationByName(stationName));
        }
        return stations;
    }

    /**
     * Get all sensors for stations
     * @param stations List of stations
     * @return List of Sensor
     */
    public List<Sensor> getAllSensorsForStations(List<Station> stations) {

        List<Sensor> sensors = new ArrayList<>();
        for (Station station : stations) {
            List<Sensor> sensorsForStation = getSensorsForStation(station);
            if (sensorsForStation != null)
                sensors.addAll(getSensorsForStation(station));
        }
        return sensors;
    }

    /**
     * Get all data for sensors
     * @param sensors List of sensors
     * @return List of Data
     */
    public List<Data> getAllDataForSensors(List<Sensor> sensors) {

        List<Data> data = new ArrayList<>();
        for (Sensor sensor : sensors) {
            data.add(getDataForSensor(sensor));
        }
        return data;
    }

    /**
     * Get highest change since start datetime
     * @param allData data
     * @param startDay start day
     * @param startHour start hour
     * @param startMinute start minute
     * @return List with two elements (key, difference)
     */
    public List<Object> getHighestChange(List<Data> allData, String startDay, int startHour, int startMinute) {

        Date date = new Date();
        String endDay = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
        int endHour = Integer.parseInt((new SimpleDateFormat("HH")).format(date));
        int endMinute = Integer.parseInt((new SimpleDateFormat("mm")).format(date));

        Map<String, List<Double>> allMeasurements = new TreeMap<>();
        for (Data data : allData) {
            allMeasurements.computeIfAbsent(data.getKey(), k -> new ArrayList<>());
            allMeasurements.get(data.getKey()).addAll(
                    getMeasurementsBetween(data, startDay, startHour, startMinute,
                            endDay, endHour, endMinute).stream()
                            .filter(x -> x.getValue() != null)
                            .map(x -> Double.parseDouble(x.getValue()))
                            .collect(Collectors.toList()));
        }

        Double actualDifference = -1.0;
        String actualKey = "";
        for (Map.Entry<String, List<Double>> entry : allMeasurements.entrySet()) {
            Double difference = 0d;
            if (entry.getValue().size() > 0)
                difference = Collections.max(entry.getValue()) - Collections.min(entry.getValue());

            if (difference > actualDifference) {
                actualDifference = difference;
                actualKey = entry.getKey();
            }
        }
        return Arrays.asList(actualKey, actualDifference);
    }

    /**
     * Get lowest parameter for list of stations
     * @param allData All data
     * @param day day in (yyyy-MM-dd) format
     * @param hour An hour
     * @param minute A minute
     * @return List of two elements (key, value)
     */
    public List<Object> getLowestParam(List<Data> allData, String day, int hour, int minute) {
        String key = "";
        double value = 1e8d;

        for (Data data : allData) {
            Measurement measurement = getLatestMeasurement(data, day, hour, minute);
            if (measurement != null && measurement.getValue() != null && Double.parseDouble(measurement.getValue()) < value) {
                value = Double.parseDouble(measurement.getValue());
                key = data.getKey();
            }
        }
        return Arrays.asList(key, value);
    }

}
