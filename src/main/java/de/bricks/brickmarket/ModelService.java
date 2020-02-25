package de.bricks.brickmarket;
import de.bricks.brickmarket.database.KundeRepository;
import de.bricks.brickmarket.database.dto.*;
import de.bricks.brickmarket.database.dto.BestellungRepository;
import de.bricks.brickmarket.database.dto.ProduktRepository;
import de.bricks.brickmarket.models.*;
import org.springframework.stereotype.Service;
import java.util.*;

import static de.bricks.brickmarket.NumberHelper.unitStringToDouble;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class ModelService {
    private final KundeRepository kunden;
    private final BestellungRepository bestellungen;
    private final ProduktRepository produkte;
    public ModelService(KundeRepository kunden, ProduktRepository produkte, BestellungRepository bestellungen){
        this.bestellungen = bestellungen;
        this.kunden = kunden;
        this.produkte = produkte;
    }
    /** erstellen der Liste aller Bestellungen
     * für die Übersicht, wird durch BestellungDTO's
     * erstellt
     */
    public List<BestellungsSummary> alleBestellungen(){
        ArrayList<BestellungsSummary> result = new ArrayList<>();
        Iterable<BestellungDTO> alleBestellungen = bestellungen.findAll();
        alleBestellungen.forEach(dto -> result.add(loadSummary(dto)));
        return result;
    }



    /*
        Laden der DTO's
        zu Model Objekten
     */

    public Bestellung load(BestellungDTO dto){
        Kunde kunde = load(kunden.findById(dto.getKunde()).get());
        Set<BestellungsPosition> bestellungsPosition = dto.getPositionen().stream().map(this::load).collect(toSet());
        return new Bestellung(dto.getBestellnr(),dto.getDatum(),kunde,bestellungsPosition);
    }

    public Bestellung bestellung(Long id){
        return load(bestellungen.findById(id).get());
    }

    public BestellungsPosition load(PositionDTO dto){
        Long produktId = dto.getProdukt();
        ProduktDTO produktDTO = produkte.findById(dto.getProdukt()).get();
        return new BestellungsPosition(produktId,dto.getAnzahl(),load(produktDTO));
    }

    public BestellungsSummary loadSummary(BestellungDTO dto){
        Kunde kunde = load(kunden.findById(dto.getKunde()).get());
        return new BestellungsSummary(dto.getBestellnr(),dto.getDatum(),kunde.getName());
    }

    public Kunde load(KundeDTO dto){
        return new Kunde(dto.getKundennr(),dto.getName(),dto.getPlz(),dto.getStadt(),dto.getStrasse(),dto.getLng(),dto.getLat());
    }

    public Produkt load(ProduktDTO dto){
        double volumen =    unitStringToDouble(dto.getBreite())*
                            unitStringToDouble(dto.getHoehe())*
                            unitStringToDouble(dto.getTiefe());
        double preis = unitStringToDouble(dto.getPreis());
        double gewicht = unitStringToDouble(dto.getGewicht());
        int bestand = dto.getBestand();
        return new Produkt(dto.getProdukt(),bestand,volumen,preis,gewicht);
    }
}