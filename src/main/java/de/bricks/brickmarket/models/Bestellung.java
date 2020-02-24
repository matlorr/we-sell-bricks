package de.bricks.brickmarket.models;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
public class Bestellung {
    private Long id;
    private LocalDate date;
    private Kunde kunde;
    private Set<BestellungsPosition> positionen;
}
