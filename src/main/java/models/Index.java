package models;

import java.io.Serializable;

/**
 * Index model
 */
public class Index implements Serializable {
    private int id;
    private String stCalcDate;
    private IndexLevel stIndexLevel;
    private String stSourceDataDate;
    private String so2CalcDate;
    private IndexLevel so2IndexLevel;
    private String so2SourceDataDate;
    private String no2CalcDate;
    private IndexLevel no2IndexLevel;
    private String no2SourceDataDate;
    private String coCalcDate;
    private IndexLevel coIndexLevel;
    private String coSourceDataDate;
    private String pm10CalcDate;
    private IndexLevel pm10IndexLevel;
    private String pm10SourceDataDate;
    private String pm25CalcDate;
    private IndexLevel pm25IndexLevel;
    private String pm25SourceDataDate;
    private String o3CalcDate;
    private IndexLevel o3IndexLevel;
    private String o3SourceDataDate;
    private String c6h6CalcDate;
    private IndexLevel c6h6IndexLevel;
    private String c6h6SourceDataDate;
    private boolean stIndexStatus;
    private String stIndexCrParam;

    public int getId() {
        return id;
    }

    public IndexLevel getStIndexLevel() {
        return stIndexLevel;
    }

    public IndexLevel getSo2IndexLevel() {
        return so2IndexLevel;
    }

    public IndexLevel getNo2IndexLevel() {
        return no2IndexLevel;
    }

    public IndexLevel getCoIndexLevel() {
        return coIndexLevel;
    }

    public IndexLevel getPm10IndexLevel() {
        return pm10IndexLevel;
    }

    public IndexLevel getPm25IndexLevel() {
        return pm25IndexLevel;
    }

    public IndexLevel getO3IndexLevel() {
        return o3IndexLevel;
    }

    public IndexLevel getC6h6IndexLevel() {
        return c6h6IndexLevel;
    }
}
