package de.bricks.brickmarket.models;

import lombok.Value;

@Value
public class Produkt {
    private String name;
    private int bestand;
    private double volumen;
    private double preis;
    private double gewicht;
}
