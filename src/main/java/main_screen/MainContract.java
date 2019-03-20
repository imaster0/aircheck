package main_screen;

import models.Index;
import models.Measurement;
import models.Param;
import models.Data;
import models.Station;

import java.util.List;
import java.util.Map;

/**
 * Presenter - View relation
 */
public interface MainContract {

    /***
     * Main screen view
     */
    interface View {

        /**
         * Get all station names
         */
        void getAllStationNames();

        /***
         * Get actual index action
         * @param stationName Full name of the station
         */
        void getActualIndex(String stationName);

        /***
         * Get station parameter action
         * @param stationName Full name of the station
         * @param day Date in YYYY-mm-dd format
         * @param param Code of the parameter (ex. SO2)
         */
        void getStationParam(String stationName, String day, int hour, int minute, String param);

        /***
         * Get an average value of parameter for the specific station action
         * @param stationName Full name of the station
         * @param startDay Starting date in YYYY-mm-dd format
         * @param endDay Ending date in YYYY-mm-dd format
         */
        void getStationParamAvg(String stationName, String startDay, int startHour, int startMinute,
                                String endDay, int endHour, int endMinute, String param);

        /***
         * Get the most hasitating parameter for specified stations
         * @param stations Full names of specified stations
         * @param startDay Date starting time range (YYYY-mm-dd format)
         * @param startHour An hour starting time range
         * @param startMinute An minute starting time range
         */
        void getHighestChange(List<String> stations, String startDay, int startHour, int startMinute);

        /**
         * Get lowest param for specified date
         * @param day day in (yyyy-MM-dd) format
         * @param hour An hour
         * @param minute A minute
         */
        void getLowestParam(List<String> stations, String day, int hour, int minute);

        /**
         * Get N params exceeded norm
         * @param station Station name
         * @param day Date in format YYYY-mm-dd
         * @param hour An hour
         * @param minute A minute
         * @param N Number of params
         */
        void getParamsExceededNorm(String station, String day, int hour, int minute, int N);

        /**
         * Get stations with highest and lowest param
         * @param paramName Param name
         */
        void getStationsWithHighestAndLowest(String paramName);

        /**
         * Get station with hourly param
         * @param stations List of stations
         * @param paramName Param name
         */
        void getStationHourlyParam(List<String> stations, String paramName);
        //display
        /**
         * Show all station names
         */
        void showAllStationNames(List<Station> stations);
        /***
         * Display actual index
         * @param station A station
         * @param index An index
         */
        void showActualIndex(Station station, Index index);

        /***
         * Display station param
         * @param measurement measurement of concrete param
         */
        void showStationParam(Measurement measurement);

        /***
         * Display average value of param
         * @param average average value
         */
        void showStationParamAvg(Double average);

        /***
         * Display highest change
         * @param paramName Name of parameter
         * @param difference A difference
         */
        void showHighestChange(String paramName, Double difference);

        /**
         * Display lowest param
         * @param key Name of parameter
         * @param value Parameter value
         */
        void showLowestParam(String key, Double value);

        /**
         * Display N params exceeeded norm
         * @param params List of params
         */
        void showParamsExceededNorm(List<Map.Entry<Param, Double>> params);

        /**
         * Display stations and date with lowest and highest param
         * @param stations Stations max and min
         * @param values Values max and min
         * @param dates Dates for max and min
         */
        void showStationsWithHighestAndLowest(List<Station> stations, List<Double> values, List<String> dates);

        /**
         * Show hourly rate
         * @param stations List of stations
         * @param data List of dates
         */
        void showStationHourlyParam(List<Station> stations, List<Data> data);
    }

    /***
     * Main screen presenter
     */
    interface Presenter {

        /***
         * Attach view
         * @param view A view to attach
         */
        void attach(View view);

        /***
         * Detach view
         */
        void detach();

        /**
         * Get all station names
         */
        void getAllStationNames();

        /***
         * Get actual index for the specified station
         * @param stationName Full name of the station
         */
        void getActualIndex(String stationName);

        /***
         * Get value of parameter for specified time
         * @param stationName Full name of the station
         * @param day Date in YYYY-mm-dd format
         * @param param Code of the parameter (ex. PO10)
         */
        void getStationParam(String stationName, String day, int hour, int minute, String param);

        /***
         * Get average value of the specified parameter and time range
         * @param stationName Full name of the station
         * @param startDay Date starting time range (YYYY-mm-dd format)
         * @param startHour An hour starting time range
         * @param startMinute A minute starting time range
         * @param endDay Date ending time range (YYYY-mm-dd format)
         * @param endHour An hour ending time range
         * @param endMinute A minute ending time range
         * @param param Code of the parameter (ex. SO2)
         */
        void getStationParamAvg(String stationName, String startDay, int startHour, int startMinute,
                                String endDay, int endHour, int endMinute, String param);

        /***
         * Get the most hasitating parameter for specified stations
         * @param stations Full names of specified stations
         * @param startDay Date starting time range (YYYY-mm-dd format)
         * @param startHour An hour starting time range
         * @param startMinute An minute starting time range
         */
        void getHighestChange(List<String> stations, String startDay, int startHour, int startMinute);

        /**
         * Get lowest param for specified stations and date
         * @param stations List of stations' names
         * @param day Day in (yyyy-MM-dd) format
         * @param hour An hour
         * @param minute A minute
         */
        void getLowestParam(List<String> stations, String day, int hour, int minute);

        /**
         * Get N params exceeded daily norm
         * @param station Station name
         * @param day Date in format YYYY-mm-dd
         * @param hour An hour
         * @param minute A minute
         * @param n Number of params
         */
        void getParamsExceededNorm(String station, String day, int hour, int minute, int n);

        /**
         * Get highest and lowest
         * @param param Param name
         */
        void getStationsWithHighestAndLowest(String param);

        /**
         * Get hourly rates param
         * @param stations List of stations
         * @param paramName Param name
         */
        void getStationHourlyParam(List<String> stations, String paramName);
    }
}
