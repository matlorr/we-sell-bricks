package de.bricks.brickmarket.models;

import lombok.Value;
import static de.bricks.brickmarket.FrachtPreisRechner.berechneFrachtPreis;

@Value
public class BestellungsPosition {
    private long nr;
    private int anzahl;
    private Produkt produkt;

    public double getPreis(){
        return anzahl*produkt.getPreis();
    }
    public double getFrachtPreis(double entfernung){
        return berechneFrachtPreis(produkt.getGewicht(),produkt.getVolumen(),entfernung);
    }
}
