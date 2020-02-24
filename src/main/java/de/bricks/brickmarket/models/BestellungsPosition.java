package de.bricks.brickmarket.models;

import lombok.Value;

@Value
public class BestellungsPosition {
    private long nr;
    private int anzahl;
    private Produkt produkt;
}
