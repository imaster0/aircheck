package services;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiQueryTest {

    private Api mockedApi;
    private ApiQuery query;
    private City mockedCity;
    private Object mockedObject;

    @BeforeEach
    void setUp() {
        mockedApi = mock(Api.class);
        query = new ApiQuery(mockedApi);
        mockedCity = mock(City.class);
        mockedObject = mock(Object.class);
    }

    @Test
    void shouldReturnStation() throws Exception {

        //given
        List<Station> stationsList = Arrays.asList(
                new Station(2, "Nawza stacji", 10d, 10d, mockedCity, mockedObject),
                new Station(7, "Nazwa stacji", 10d, 10d, mockedCity, mockedObject),
                new Station(3, "Nazwa stacjix", 10d, 10d, mockedCity, mockedObject),
                new Station(1, "xNazwa stacji", 10d, 10d, mockedCity, mockedObject)
        );
        when(mockedApi.findAllCached()).thenReturn(stationsList);

        //when
        Station station = query.getStationByName("Nazwa stacji");
        Station station2 = query.getStationByName("xNazwa");

        //then
        assertEquals(7, station.getId());
        assertNull(station2);
    }

    @Test
    void shouldReturnSensor() throws Exception {

        //given
        Station station = new Station(2, "Nawza stacji", 10d, 10d, mockedCity, mockedObject);
        List<Sensor> sensorsList = Arrays.asList(
                new Sensor(4, 2, new Param("Pył zawieszony PM10", "PM10", "PM10", 13)),
                new Sensor(2, 2, new Param("dwutlenek węgla CO2", "CO2", "CO2", 1)),
                new Sensor(3, 2, new Param("PM12", "PM12", "PM12", 14)),
                new Sensor(1, 2, new Param("HCO3", "HCO3", "HCO3", 11))
        );
        when(mockedApi.getSensorsCached(station.getId())).thenReturn(sensorsList);

        //when
        Sensor sensorPM10 = query.getSensorForStationByParamName(station, "PM10");
        Sensor sensorCO2 = query.getSensorForStationByParamName(station, "CO2");
        Sensor sensorPM12 = query.getSensorForStationByParamName(station, "PM12");

        //then
        assertEquals(4, sensorPM10.getId());
        assertEquals(2, sensorCO2.getId());
        assertEquals(3, sensorPM12.getId());
    }

    @Test
    void shouldReturnLatestMeasurement() {

        //given
        Data data = new Data("abc", new Measurement[]{
                new Measurement("2019-01-15 19:11:00", "33"),
                new Measurement("2019-01-16 19:10:00", "19"),
                new Measurement("2019-01-16 19:09:00", "18"),
                new Measurement("2019-01-16 19:12:00", "5")
        });

        //when
        Measurement exactDatetime = query.getLatestMeasurement(data, "2019-01-16", 19, 10);
        Measurement minuteBefore = query.getLatestMeasurement(data, "2019-01-16", 19, 8);
        Measurement minuteAfter = query.getLatestMeasurement(data, "2019-01-16", 19, 13);
        Measurement noData = query.getLatestMeasurement(data, "2019-01-15", 19, 10);

        //then
        assertEquals("19", exactDatetime.getValue());
        assertEquals("33", minuteBefore.getValue());
        assertEquals("5", minuteAfter.getValue());
        assertNull(noData);
    }

    @Test
    void shouldReturnMeasurementsBetween() {

        //given
        Data data = new Data("abc", new Measurement[]{
                new Measurement("2019-01-15 19:11:00", "33"),
                new Measurement("2019-01-16 19:10:00", "19"),
                new Measurement("2019-01-16 19:09:00", "18"),
                new Measurement("2019-01-16 19:12:00", "5")
        });

        //when
        List<Measurement> exactDatetime = query.getMeasurementsBetween(data, "2019-01-16", 19, 10,
                "2019-01-16", 19, 10);
        List<Measurement> oneBetween = query.getMeasurementsBetween(data, "2019-01-16", 19, 11,
                "2019-01-16", 19, 13);
        List<Measurement> empty = query.getMeasurementsBetween(data, "2019-01-16", 19, 13,
                "2019-01-17", 19, 13);
        List<Measurement> all = query.getMeasurementsBetween(data, "2019-01-01", 01, 00,
                "2019-01-20", 01, 00);

        //then
        assertEquals(1, exactDatetime.size());
        assertEquals(1, oneBetween.size());
        assertEquals(0, empty.size());
        assertEquals(4, all.size());
    }

    @Test
    void shouldReturnAverageBetween() {

        //given
        Data data = new Data("abc", new Measurement[]{
                new Measurement("2019-01-15 19:11:00", "33"),
                new Measurement("2019-01-16 19:10:00", "19"),
                new Measurement("2019-01-16 19:09:00", "18"),
                new Measurement("2019-01-16 19:12:00", "5")
        });

        //when
        double exactDatetime = query.getAverageBetween(data, "2019-01-16", 19, 10,
                "2019-01-16", 19, 10);
        double oneBetween = query.getAverageBetween(data, "2019-01-16", 19, 11,
                "2019-01-16", 19, 13);
        double empty = query.getAverageBetween(data, "2019-01-16", 19, 13,
                "2019-01-17", 19, 13);
        double all = query.getAverageBetween(data, "2019-01-01", 01, 00,
                "2019-01-20", 01, 00);

        //then
        assertEquals(19, exactDatetime);
        assertEquals(5, oneBetween);
        assertEquals(-1, empty);
        assertEquals(18.75, all);
    }

    @Test
    void shouldReturnHighestChange() {

        //given
        Data data = new Data("abc", new Measurement[]{
                new Measurement("2019-01-14 19:11:00", "35"),
                new Measurement("2019-01-15 19:10:00", "19"),
                new Measurement("2019-01-15 19:09:00", "18"),
                new Measurement("2019-01-15 19:12:00", "5")
        });

        //when
        List<Object> all = query.getHighestChange(Collections.singletonList(data), "2019-01-01", 01, 00);

        //then
        assertEquals(30d, all.get(1));
        assertEquals("abc", all.get(0));
    }


    @Test
    void shouldReturnLowestParam() {

        //given
        List<Data> data = Arrays.asList(
                new Data("abc", new Measurement[]{
                        new Measurement("2019-01-14 19:11:00", "35"),
                        new Measurement("2019-01-15 19:10:00", "19"),
                        new Measurement("2019-01-15 19:09:00", "18"),
                        new Measurement("2019-01-15 19:12:00", "5")
                }),
                new Data("axn", new Measurement[]{
                        new Measurement("2019-01-14 19:11:00", "25"),
                        new Measurement("2019-01-15 19:10:00", "19"),
                        new Measurement("2019-01-15 19:09:00", "18"),
                        new Measurement("2019-01-15 19:12:00", "5")
                })
        );

        //when
        List<Object> lowestParam = query.getLowestParam(data, "2019-01-14", 19, 11);
        String key = (String) lowestParam.get(0);
        Double value = (Double) lowestParam.get(1);

        //then
        assertEquals("axn", key);
        assertEquals(25d, value);
    }
}