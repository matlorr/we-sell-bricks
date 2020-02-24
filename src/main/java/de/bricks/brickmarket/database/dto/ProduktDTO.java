package de.bricks.brickmarket.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("produkt")
@Data
public class ProduktDTO {
    @Id
    private Long id;
    private String gewicht;
    private String hoehe;
    private String breite;
    private String tiefe;
    private String preis;
    private String produkt;
    private String beschreibung;
    private int bestand;
}
