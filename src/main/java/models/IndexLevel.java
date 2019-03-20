package models;

import java.io.Serializable;

/**
 * Index level model
 */
public class IndexLevel implements Serializable {
    private int id;
    private String indexLevelName;

    public int getId() {
        return id;
    }

    public String getIndexLevelName() {
        return indexLevelName;
    }
}
