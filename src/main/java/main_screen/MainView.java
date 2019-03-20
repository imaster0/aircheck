package main_screen;

import models.*;

import java.util.List;
import java.util.Map;

/***
 * Class receiving user action and displaying data
 */
public class MainView implements MainContract.View {

    private MainContract.Presenter presenter;

    public MainView(MainContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.attach(this);
    }

    private void drawStatusBar(Double value) {
        value = Math.sqrt(value);
        System.out.print(" ");
        for (int i = 0; i < Math.round(value); i++)
            System.out.print("■");
    }

    @Override
    public void getAllStationNames() {
        presenter.getAllStationNames();
    }

    @Override
    public void getActualIndex(String stationName) {
        presenter.getActualIndex(stationName);
    }

    @Override
    public void getStationParam(String stationName, String day, int hour, int minute, String param) {
        presenter.getStationParam(stationName,day, hour, minute, param);
    }

    @Override
    public void getStationParamAvg(String stationName, String startDay, int startHour, int startMinute,
                                   String endDay, int endHour, int endMinute, String param) {
        presenter.getStationParamAvg(stationName, startDay, startHour, startMinute, endDay, endHour, endMinute, param);
    }

    @Override
    public void getHighestChange(List<String> stations, String startDay, int startHour, int startMinute) {
        presenter.getHighestChange(stations, startDay, startHour, startMinute);
    }

    @Override
    public void getLowestParam(List<String> stations, String day, int hour, int minute) {
        presenter.getLowestParam(stations, day, hour, minute);
    }

    @Override
    public void getParamsExceededNorm(String station, String day, int hour, int minute, int n) {
        presenter.getParamsExceededNorm(station, day, hour, minute, n);
    }

    @Override
    public void getStationsWithHighestAndLowest(String param) {
        presenter.getStationsWithHighestAndLowest(param);
    }

    @Override
    public void getStationHourlyParam(List<String> stations, String paramName) {
        presenter.getStationHourlyParam(stations, paramName);
    }

    @Override
    public void showAllStationNames(List<Station> stations) {
        if (stations.size() == 0) {
            System.out.println("No stations found");
        } else {
            for (Station station: stations) {
                System.out.println(station.getStationName());
            }
        }
    }

    @Override
    public void showActualIndex(Station station, Index index) {
        if (station == null) {
            System.out.println("Empty result");
        } else {
            System.out.println("\n----------------------------------------------------------------------------");
            System.out.println(station.getStationName() + ": " + index.getStIndexLevel().getIndexLevelName());
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Szczegóły:");
            if (index.getSo2IndexLevel() != null) {
                System.out.println("SO2 " + index.getSo2IndexLevel().getIndexLevelName());
            }
            if (index.getC6h6IndexLevel() != null) {
                System.out.println("C6H6 " + index.getC6h6IndexLevel().getIndexLevelName());
            }
            if (index.getCoIndexLevel() != null) {
                System.out.println("CO " + index.getCoIndexLevel().getIndexLevelName());
            }
            if (index.getNo2IndexLevel() != null) {
                System.out.println("NO2 " + index.getNo2IndexLevel().getIndexLevelName());
            }
            if (index.getPm10IndexLevel() != null) {
                System.out.println("PM10 " + index.getPm10IndexLevel().getIndexLevelName());
            }
            if (index.getPm25IndexLevel() != null) {
                System.out.println("PM25 " + index.getPm25IndexLevel().getIndexLevelName());
            }
            if (index.getO3IndexLevel() != null) {
                System.out.println("O3 " + index.getO3IndexLevel().getIndexLevelName());
            }
            System.out.println("----------------------------------------------------------------------------");
        }
    }

    @Override
    public void showStationParam(Measurement measurement) {
        if (measurement == null) {
            System.out.println("There is no measurement for specified station/date");
        } else {
            System.out.println(measurement.getDatetime() + " " + measurement.getValue());
        }
    }

    @Override
    public void showStationParamAvg(Double average) {
        if (average == -1) {
            System.out.println("Not enough data for specified station/date range");
        } else {
            System.out.println(average);
        }
    }

    @Override
    public void showHighestChange(String paramName, Double difference) {
        if (difference < 0) {
            System.out.println("Not enough data for specified stations/date range");
        } else {
            System.out.println(paramName + " " + difference);
        }
    }

    @Override
    public void showLowestParam(String key, Double value) {
        if (key == null || value == 1e8) {
            System.out.println("Not enough data for specified station/date");
        } else {
            System.out.println(key + ": " + value);
        }
    }

    @Override
    public void showParamsExceededNorm(List<Map.Entry<Param, Double>> params) {
        if (params.size() == 0) {
            System.out.println("Not enough data for specified station/date");
        }
        for (Map.Entry<Param, Double> param : params) {
            System.out.println(param.getKey().getParamName() + ": " + param.getValue() + " µg/m3");
        }
    }

    @Override
    public void showStationsWithHighestAndLowest(List<Station> stations, List<Double> values, List<String> dates) {
        if (stations.get(0) == null) {
            System.out.println("Empty result");
        } else {
            System.out.println("Max value: ");
            System.out.println(stations.get(0).getStationName() + ": " + values.get(0) + " " + dates.get(0));
            System.out.println("Min value: ");
            System.out.println(stations.get(1).getStationName() + ": " + values.get(1) + " " + dates.get(1));
        }
    }

    @Override
    public void showStationHourlyParam(List<Station> stations, List<Data> data) {
        for (int i = 0; i < stations.size(); i++) {
            if (data.get(i) == null || data.get(i).getValues() == null) continue;
            for (int j = 0; j < data.get(i).getValues().length; j++) {
                if ( data.get(i).getValues()[j].getValue() == null) continue;
                System.out.print(data.get(i).getValues()[j].getDatetime() + " " +
                        stations.get(i).getStationName());
                drawStatusBar(Double.parseDouble(data.get(i).getValues()[j].getValue()));
                System.out.println(" " + data.get(i).getValues()[j].getValue());
            }
        }
    }
}
