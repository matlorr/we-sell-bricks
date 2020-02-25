package de.bricks.brickmarket.database.dto;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("position")
@Data
public class PositionDTO {
    @Id
    private Long id;
    private int anzahl;
    private Long produkt;
}
