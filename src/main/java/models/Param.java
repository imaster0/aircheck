package models;

import java.io.Serializable;

/**
 * Param model
 */
public class Param implements Serializable {
    private String paramName;
    private String paramFormula;
    private String paramCode;
    private int idParam;

    public Param(String paramName, String paramFormula, String paramCode, int idParam) {
        this.paramName = paramName;
        this.paramFormula = paramFormula;
        this.paramCode = paramCode;
        this.idParam = idParam;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamFormula() {
        return paramFormula;
    }

    public String getParamCode() {
        return paramCode;
    }

    public int getIdParam() {
        return idParam;
    }

    /**
     * Get daily norm
     * @return Double
     */
    public Double getNorm() { //µg/m3
        switch (paramCode) {
            case "C6H6": return 5d;
            case "NO2": return 40d;
            case "SO2": return 125d;
            case "CO": return 10000d;
            case "PM10": return 40d;
            case "PM2.5": return 20d;
            case "PB": return 0.5d;
        }
        return 10000d;
    }
}
