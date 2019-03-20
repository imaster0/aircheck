package services;

import models.Data;
import models.Index;
import models.Sensor;
import models.Station;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class GiosApiTest {

    static Api giosApi;

    @BeforeAll
    static void setUp() {
        giosApi = spy(GiosApi.class);
    }

    @Test
    void shouldFindAll() throws Exception {

        //given
        when(giosApi.getContentFromUrl("http://api.gios.gov.pl/pjp-api/rest/station/findAll")).thenReturn(
                "[{\n" +
                "    \"id\": 14,\n" +
                "    \"stationName\": \"Działoszyn\",\n" +
                "    \"gegrLat\": \"50.972167\",\n" +
                "    \"gegrLon\": \"14.941319\",\n" +
                "    \"city\": {\n" +
                "        \"id\": 192,\n" +
                "        \"name\": \"Działoszyn\",\n" +
                "        \"commune\": {\n" +
                "            \"communeName\": \"Bogatynia\",\n" +
                "            \"districtName\": \"zgorzelecki\",\n" +
                "            \"provinceName\": \"DOLNOŚLĄSKIE\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"addressStreet\": null\n" +
                "}]");

        //when
        List<Station> stations = giosApi.findAll();

        //then
        assertEquals(1, stations.size());
        assertEquals(14, stations.get(0).getId());
        assertEquals("Bogatynia", stations.get(0).getCity().getCommune().getCommuneName());
    }

    @Test
    void shouldGetSensors() throws Exception {

        //given
        when(giosApi.getContentFromUrl("http://api.gios.gov.pl/pjp-api/rest/station/sensors/14")).thenReturn(
               "[{\n" +
                       "    \"id\": 92,\n" +
                       "    \"stationId\": 14,\n" +
                       "    \"param\": {\n" +
                       "        \"paramName\": \"pył zawieszony PM10\",\n" +
                       "        \"paramFormula\": \"PM10\",\n" +
                       "        \"paramCode\": \"PM10\",\n" +
                       "        \"idParam\": 3\n" +
                       "    }\n" +
                       "},\n" +
                       "{\n" +
                       "    \"id\": 88,\n" +
                       "    \"stationId\": 14,\n" +
                       "    \"param\": {\n" +
                       "        \"paramName\": \"dwutlenek azotu\",\n" +
                       "        \"paramFormula\": \"NO2\",\n" +
                       "        \"paramCode\": \"NO2\",\n" +
                       "        \"idParam\": 6\n" +
                       "    }\n" +
                       "}]");

        //when
        List<Sensor> sensors = giosApi.getSensors(14);

        //then
        assertEquals(2, sensors.size());
        assertEquals(92, sensors.get(0).getId());
        assertEquals("NO2", sensors.get(1).getParam().getParamCode());
    }

    @Test
    void shouldGetData() throws Exception {

        //given
        when(giosApi.getContentFromUrl("http://api.gios.gov.pl/pjp-api/rest/data/getData/1")).thenReturn(
                "{\n" +
                        "    \"key\": \"PM10\",\n" +
                        "    \"values\": [\n" +
                        "    {\n" +
                        "        \"date\": \"2017-03-28 11:00:00\",\n" +
                        "        \"value\": 30.3018\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"date\": \"2017-03-28 12:00:00\",\n" +
                        "        \"value\": 27.5946\n" +
                        "    }]\n" +
                        "}");

        //when
        Data data = giosApi.getData(1);

        //then
        assertEquals("PM10", data.getKey());
        assertEquals("30.3018", data.getValues()[0].getValue());
    }

    @Test
    void shouldGetIndex() throws Exception {

        //given
        when(giosApi.getContentFromUrl("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/1")).thenReturn(
                "{\n" +
                        "    \"id\": 52,\n" +
                        "    \"stCalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"stIndexLevel\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"indexLevelName\": \"Umiarkowany\"\n" +
                        "    },\n" +
                        "    \"stSourceDataDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"so2CalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"so2IndexLevel\": null,\n" +
                        "    \"so2SourceDataDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"no2CalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"no2IndexLevel\": null,\n" +
                        "    \"no2SourceDataDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"coCalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"coIndexLevel\": null,\n" +
                        "    \"coSourceDataDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"pm10CalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"pm10IndexLevel\": null,\n" +
                        "    \"pm10SourceDataDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"pm25CalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"pm25IndexLevel\": null,\n" +
                        "    \"pm25SourceDataDate\": null,\n" +
                        "    \"o3CalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"o3IndexLevel\": null,\n" +
                        "    \"o3SourceDataDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"c6h6CalcDate\": \"2017-03-28 12:00:00\",\n" +
                        "    \"c6h6IndexLevel\": null,\n" +
                        "    \"c6h6SourceDataDate\": \"2017-03-28 12:00:00\"\n" +
                        "}");

        //when
        Index index = giosApi.getIndex(1);

        //then
        assertEquals(52, index.getId());
        assertEquals("Umiarkowany", index.getStIndexLevel().getIndexLevelName());
    }
}