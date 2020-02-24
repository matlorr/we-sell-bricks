package de.bricks.brickmarket.models;

import lombok.Value;

@Value
public class Kunde {
    private long nr;
    private String name;
    private int plz;
    private String stadt;
    private String strasse;
    private double lng;
    private double lat;
}
