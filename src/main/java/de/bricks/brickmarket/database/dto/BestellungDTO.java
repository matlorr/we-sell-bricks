package de.bricks.brickmarket.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import java.util.List;

@Table("bestellung")
@Data
public class BestellungDTO {
    @Id
    private Long bestellnr;
    private String datum;
    private int kunde;
    private List<PositionDTO> positionen;
}
