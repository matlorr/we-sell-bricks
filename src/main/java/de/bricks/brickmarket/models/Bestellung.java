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

    public double getProduktKosten(){
        double preis = 0;
        for(BestellungsPosition pos:positionen){
            preis += pos.getPreis();
        }
        return preis;
    }

    public double getFrachtKosten(){
        double preis = 0;
        for(BestellungsPosition pos : positionen){
            preis += pos.getFrachtPreis(getEntfernung(kunde));
        }
        return preis;
    }

    public double getGesamtPreis(){
        return getProduktKosten() + getFrachtKosten();
    }
    public double getEntfernung(Kunde kunde){
        return 0;
    }
}
