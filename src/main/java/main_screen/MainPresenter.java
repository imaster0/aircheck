package main_screen;


import models.*;
import services.Api;
import services.ApiQuery;
import services.GiosApi;

import java.util.*;
import java.util.stream.Collectors;

/***
 * Class calculating and updating the view
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private Api api;
    private ApiQuery apiQuery;

    public MainPresenter() {
        this.api = new GiosApi();
        this.apiQuery = new ApiQuery(this.api);
    }

    @Override
    public void attach(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void getAllStationNames() {
        List<Station> stations = Collections.emptyList();
        try {
            stations = api.findAllCached();
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.showAllStationNames(stations);
    }

    @Override
    public void getActualIndex(String stationName) {
        Station station = apiQuery.getStationByName(stationName);
        Index index = apiQuery.getIndexForStation(station);

        view.showActualIndex(station, index);
    }

    @Override
    public void getStationParam(String stationName, String day, int hour, int minute, String param) {
        Station station = apiQuery.getStationByName(stationName);
        Sensor paramSensor = apiQuery.getSensorForStationByParamName(station, param);
        Data allData = apiQuery.getDataForSensor(paramSensor);
        Measurement latestMeasurement = apiQuery.getLatestMeasurement(allData, day, hour, minute);

        view.showStationParam(latestMeasurement);
    }

    @Override
    public void getStationParamAvg(String stationName, String startDay, int startHour, int startMinute,
                                   String endDay, int endHour, int endMinute, String param) {
        Station station = apiQuery.getStationByName(stationName);
        Sensor sensorParam = apiQuery.getSensorForStationByParamName(station, param);
        Data allData = apiQuery.getDataForSensor(sensorParam);
        Double average = apiQuery.getAverageBetween(allData, startDay, startHour, startMinute, endDay, endHour, endMinute);

        view.showStationParamAvg(average);
    }

    @Override
    public void getHighestChange(List<String> stations, String startDay, int startHour, int startMinute) {
        List<Station> allStations = apiQuery.getAllStationsByNames(stations);
        List<Sensor> allSensors = apiQuery.getAllSensorsForStations(allStations);
        List<Data> allData = apiQuery.getAllDataForSensors(allSensors);
        List<Object> highestChange = apiQuery.getHighestChange(allData, startDay, startHour, startMinute);

        String actualKey = (String) highestChange.get(0);
        Double actualDifference = (Double) highestChange.get(1);

        view.showHighestChange(actualKey, actualDifference);
    }

    @Override
    public void getLowestParam(List<String> stations, String day, int hour, int minute) {
        List<Station> allStations = apiQuery.getAllStationsByNames(stations);
        List<Sensor> allSensors = apiQuery.getAllSensorsForStations(allStations);
        List<Data> allData = apiQuery.getAllDataForSensors(allSensors);
        List<Object> result = apiQuery.getLowestParam(allData, day, hour, minute);
        String key = (String) result.get(0);
        Double value = (Double) result.get(1);
        view.showLowestParam(key, value);
    }

    @Override
    public void getParamsExceededNorm(String stationName, String day, int hour, int minute, int n) {
        Station station = apiQuery.getStationByName(stationName);
        List<Sensor> sensors = apiQuery.getSensorsForStation(station);

        List<Map.Entry<Param, Double>> params = new ArrayList<>();

        for (Sensor sensor : sensors) {
            Data sensorData = apiQuery.getDataForSensor(sensor);
            Measurement measurement = apiQuery.getLatestMeasurement(sensorData, day, hour, minute);
            if (measurement == null || measurement.getValue() == null) continue;
            params.add(new AbstractMap.SimpleEntry<>(sensor.getParam(), Double.parseDouble(measurement.getValue()) - sensor.getParam().getNorm()));
        }

        List<Map.Entry<Param, Double>> result = params.stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .filter(x -> x.getValue() > 0)
                .limit(n)
                .collect(Collectors.toList());

        view.showParamsExceededNorm(result);
    }

    @Override
    public void getStationsWithHighestAndLowest(String param) {
        List<Station> stations = null;
        try {
            stations = api.findAllCached();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Sensor> sensors = new ArrayList<>();
        for (Station station : stations) {
            sensors.add(apiQuery.getSensorForStationByParamName(station, param));
        }

        Double maks = -1e8, min = 1e8;
        Station maxStation = null, minStation = null;
        String maxDate = "", minDate = "";
        for (Sensor sensor : sensors) {
            Data data = apiQuery.getDataForSensor(sensor);
            if (data == null) continue;
            List<Measurement> measurements = Arrays.asList(data.getValues());
            for (Measurement measurement : measurements) {
                if (measurement.getValue() == null) continue;
                if (Double.parseDouble(measurement.getValue()) > maks) {
                    maxStation = apiQuery.getStationById(sensor.getStationId());
                    maks = Double.parseDouble(measurement.getValue());
                    maxDate = measurement.getDatetime();
                }
                if (Double.parseDouble(measurement.getValue()) < min) {
                    minStation = apiQuery.getStationById(sensor.getStationId());
                    min = Double.parseDouble(measurement.getValue());
                    minDate = measurement.getDatetime();
                }
            }
        }

        List<Station> resultStations = Arrays.asList(maxStation, minStation);
        List<Double> resultValues = Arrays.asList(maks, min);
        List<String> dates = Arrays.asList(maxDate, minDate);

        view.showStationsWithHighestAndLowest(resultStations, resultValues, dates);
    }

    @Override
    public void getStationHourlyParam(List<String> stations, String paramName) {
        List<Station> allStations = apiQuery.getAllStationsByNames(stations);
        List<Data> allData = new ArrayList<>();

        for (Station station : allStations) {
            Sensor sensor = apiQuery.getSensorForStationByParamName(station, paramName);
            allData.add(apiQuery.getDataForSensor(sensor));
        }

        view.showStationHourlyParam(allStations, allData);
    }
}
