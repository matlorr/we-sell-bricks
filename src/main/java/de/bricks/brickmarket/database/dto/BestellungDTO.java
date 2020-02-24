package de.bricks.brickmarket.database.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import java.time.LocalDate;
import java.util.Set;

@Table("bestellung")
@Data
public class BestellungDTO {
    @Id
    private Long bestellnr;
    private LocalDate datum;
    private Long kunde;
    private Set<PositionDTO> positionen;
}
