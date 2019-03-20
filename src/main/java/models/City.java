package models;

import java.io.Serializable;

/**
 * City model
 */
public class City implements Serializable {
    private int id;
    private String name;
    private Commune commune;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Commune getCommune() {
        return commune;
    }
}
