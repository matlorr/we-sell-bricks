package de.bricks.brickmarket.database;

import de.bricks.brickmarket.database.dto.KundeDTO;
import org.springframework.data.repository.CrudRepository;

public interface KundeRepository extends CrudRepository<KundeDTO,Long> {
}
