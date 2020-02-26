package de.bricks.brickmarket;

import de.bricks.brickmarket.database.KundeRepository;
import de.bricks.brickmarket.database.dto.BestellungRepository;
import de.bricks.brickmarket.database.dto.ProduktDTO;
import de.bricks.brickmarket.database.dto.ProduktRepository;
import de.bricks.brickmarket.models.BestellungsPosition;
import de.bricks.brickmarket.models.Produkt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BestandService {
    private final KundeRepository kunden;
    private final ProduktRepository produkte;
    private final BestellungRepository bestellungen;

    public BestandService(KundeRepository kunden, ProduktRepository produkte, BestellungRepository bestellungen) {
        this.kunden = kunden;
        this.produkte = produkte;
        this.bestellungen = bestellungen;
    }

    public void addBestand(Produkt produkt, int anzahl){
        ProduktDTO produktDTO = produktDTO(produkt);
        produktDTO.setBestand(produktDTO.getBestand() + anzahl);
        produkte.save(produktDTO);
    }

    public void decreaseBestand(List<BestellungsPosition> positionen){
        for (BestellungsPosition position: positionen) {
            if(position.getAnzahl() <= produktDTO(position.getProdukt()).getBestand()){
                ProduktDTO produktDTO = produktDTO(position.getProdukt());
                produktDTO.setBestand(produktDTO.getBestand() - position.getAnzahl());
                produkte.save(produktDTO);
            }
        }
    }

    /**
     * Generates a "ProduktDTO" from a given "Produkt" by searching its name throught the database
     *
     * @param produkt
     * @return produktDTO
     */
    public ProduktDTO produktDTO(Produkt produkt) {
        Iterable<ProduktDTO> alleProdukte = produkte.findAll();
        for (ProduktDTO produktDTO : alleProdukte) {
            if (produktDTO.getProdukt().equals(produkt.getName())) {
                return produktDTO;
            }
        }
        return null;
    }

    public boolean checkBestand(List<BestellungsPosition> positionen) {
        for (BestellungsPosition position: positionen) {
            if(position.getAnzahl() > produktDTO(position.getProdukt()).getBestand()){
                return false;
            }
        }
        return true;
    }

}
