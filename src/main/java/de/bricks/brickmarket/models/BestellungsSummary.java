package de.bricks.brickmarket.models;

import lombok.Value;

import java.time.LocalDate;

@Value
public class BestellungsSummary {
    private long nr;
    private LocalDate datum;
    private String kunde;
}
