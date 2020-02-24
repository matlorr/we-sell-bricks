package de.bricks.brickmarket.models;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class Bestellung {
    private Long id;
    private LocalDate date;
    private Kunde kunde;
    private List<BestellungsPosition> positionen;

}
