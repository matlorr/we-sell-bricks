package de.bricks.brickmarket;
import de.bricks.brickmarket.NumberHelper;
import de.bricks.brickmarket.database.KundeRepository;
import de.bricks.brickmarket.database.dto.*;
import de.bricks.brickmarket.database.dto.BestellungRepository;
import de.bricks.brickmarket.database.dto.ProduktRepository;
import de.bricks.brickmarket.models.BestellungsSummary;
import de.bricks.brickmarket.models.Kunde;
import de.bricks.brickmarket.models.Produkt;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static de.bricks.brickmarket.NumberHelper.unitStringToDouble;

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
     * erstellt.
     */
    public List<BestellungsSummary> alleBestellungen(){
        ArrayList<BestellungsSummary> result = new ArrayList<>();
        Iterable<BestellungDTO> alleBestellungen = bestellungen.findAll();
        alleBestellungen.forEach(dto -> result.add(loadSummary(dto)));
        return result;
    }

    /**
     * Erstellt eine Liste aller Kunden, durch Laden der entsprechenden Information
     * durch das KundenDTO.
     * @return
     */
    public List<Kunde> alleKunden() {
        ArrayList<Kunde> result = new ArrayList<>();
        Iterable<KundeDTO> alleKunden = kunden.findAll();
        alleKunden.forEach(dto -> result.add(load(dto)));
        return result;
    }

    public List<Produkt> alleProdukte() {
        ArrayList<Produkt> result = new ArrayList<>();
        Iterable<ProduktDTO> alleProdukt = produkte.findAll();
        alleProdukt.forEach(dto -> result.add(load(dto)));
        return result;
    }

    /**
     * Erzeugt ein Kundenobjekt und befüllt ein BestellungsSummaryobjekt mit diesem
     * und Daten aus dem BestellungsDTO.
     * @param dto
     * @return
     */
    public BestellungsSummary loadSummary(BestellungDTO dto){
        Kunde kunde = load(kunden.findById(dto.getKunde()).get());
        return new BestellungsSummary(dto.getBestellnr(),dto.getDatum(),kunde.getName());
    }

    /**
     * Erzeugt ein Kundenobjekt aus den transferierten Daten eines KundenDTOs.
     * @param dto
     * @return
     */
    public Kunde load(KundeDTO dto){
        return new Kunde(dto.getKundennr(),dto.getName(),dto.getPlz(),dto.getStadt(),dto.getStrasse(),dto.getLng(),dto.getLat());
    }

    private Produkt load(ProduktDTO dto) {
        double volumen = unitStringToDouble(dto.getHoehe()) * unitStringToDouble(dto.getBreite()) * unitStringToDouble(dto.getTiefe());
        double gewicht = unitStringToDouble(dto.getGewicht());
        double preis = unitStringToDouble(dto.getPreis());
        return new Produkt(dto.getProdukt(), dto.getBestand(), volumen, preis, gewicht);
    }



}