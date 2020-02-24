package de.bricks.brickmarket.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("kunde")
@Data
public class KundeDTO {
    @Id
    private Long kundennr;
    private String name;
    private int plz;
    private String stadt;
    private String strasse;
    private double lng;
    private double lat;
}
