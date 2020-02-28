package de.bricks.brickmarket;
import de.bricks.brickmarket.database.KundeRepository;
import de.bricks.brickmarket.database.dto.*;
import de.bricks.brickmarket.database.dto.BestellungRepository;
import de.bricks.brickmarket.database.dto.ProduktRepository;
import de.bricks.brickmarket.models.*;
import org.springframework.stereotype.Service;
import static de.bricks.brickmarket.NumberHelper.unitStringToDouble;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final KundeRepository kunden;
    private final ProduktRepository produkte;
    private final BestellungRepository bestellungen;

    public ModelService(KundeRepository kunden, ProduktRepository produkte, BestellungRepository bestellungen) {
        this.kunden = kunden;
        this.produkte = produkte;
        this.bestellungen = bestellungen;
    }

    /** Returns a list of all "BestellungSummeries", which are generated from BestellungDTOs.
     *
     * @return
     */
    public List<BestellungsSummary> alleBestellungen() {
        ArrayList<BestellungsSummary> result = new ArrayList<>();
        Iterable<BestellungDTO> alleBestellungen = bestellungen.findAll();
        alleBestellungen.forEach(dto -> result.add(loadSummary(dto)));
        return result;
    }

    public List<Kunde> alleKunden() {
        ArrayList<Kunde> result = new ArrayList<>();
        Iterable<KundeDTO> alleKunden = kunden.findAll();
        alleKunden.forEach(dto -> result.add(load(dto)));
        return result;
    }

    /**
     * Generates a object of class Produkt with data sourced from the database
     * via the ProduktDTO
    /** Returns a list of all "Produkte", which are generated from ProdukteDTO.
     *
     * @return
     */
    public List<Produkt> alleProdukte() {
        ArrayList<Produkt> result = new ArrayList<>();
        Iterable<ProduktDTO> alleProdukte = produkte.findAll();
        alleProdukte.forEach(dto -> result.add(load(dto)));
        return result;
    }

    /**
     * Generates an "Bestellung" object from database with the id given by the parameter.
     *
     * @param id
     * @return
     */
    public Bestellung bestellung(Long id) {
        return load(bestellungen.findById(id).get());
    }

    /**
     * Generates a "Produkt" object by finding the fitting line in the database via
     * its name.
     *
     * @param name
     * @return
     */
    public Produkt produkt(String name){
        ArrayList<Produkt> result = new ArrayList<>();
        Iterable<ProduktDTO> alleProdukte = produkte.findAll();
        alleProdukte.forEach(dto -> result.add(load(dto)));
        for (Produkt produkt: result) {
            if (sameName(name, produkt.getName())) return produkt;
        }
        return null;
    }

    private boolean sameName(String name, String toCheck ) {
        if (toCheck.equals(name)) {
            return true;
        }
        return false;
    }

    public Kunde kunde(String name){
        ArrayList<Kunde> result = new ArrayList<>();
        Iterable<KundeDTO> alleKunden = kunden.findAll();
        alleKunden.forEach(dto -> result.add(load(dto)));
        for (Kunde kunde: result) {
            if (sameName(name,  kunde.getName())) return kunde;
        }
        return null;
    }

    /**
     * Generates a "BestellungSummary" object by sourcing data via the BestellungDTO from the database
     * concerning the id, date and customer name.
     *
     * @param dto
     * @return Summary of "Bestellung" containing "Bestellnr", date and the name of customer.
     */
    public BestellungsSummary loadSummary(BestellungDTO dto) {
        Kunde kunde = load(kunden.findById(dto.getKunde()).get());
        return new BestellungsSummary(dto.getBestellnr(), dto.getDatum(), kunde.getName());
    }

    /**
     * Generates a "Bestellung" object with data sourced form data base.
     * The data sourced from the BestellungDTO is saved by an object of class "Kunde" and
     * a List of objects of class "BestellPosition" which are loaded into the List through a stream.
     *
     * @param dto
     * @return "Bestellung" object
     */
    public Bestellung load(BestellungDTO dto) {
        Kunde kunde = load(kunden.findById(dto.getKunde()).get());
        Set<BestellungsPosition> positionen = dto.getPositionen().stream().map(this::load).collect(Collectors.toSet());
        return new Bestellung(dto.getBestellnr(),dto.getDatum(), kunde, positionen);
    }

    /**
     * Generates "BestellPosition" object with data sourced from database via the PositionDTO.
     * Data needed for the instance of "Produkt" in class "BestellPosition" is loaded through
     * method load(productDTO).
     *
     * @param dto
     * @return "BestellPosition" object
     */
    public BestellungsPosition load(PositionDTO dto) {
        Long produktId = dto.getProdukt();
        ProduktDTO produktDTO = produkte.findById(produktId).get();
        return new BestellungsPosition(dto.getId(),dto.getAnzahl(), load(produktDTO));
    }

    /**
     * Generates two objects "Adresse" and "Koordinaten" with data sourced from database via their DTOs.
     * Those above mentioned objects are used to generate an object from class "Kunde".
     *
     * @param dto
     * @return "Kunde" object
     */
    public Kunde load(KundeDTO dto) {
        return new Kunde(dto.getKundennr(),dto.getName(),dto.getPlz(),dto.getStadt(),dto.getStrasse(),dto.getLng(),dto.getLat());
    }

    /**
     * Generates a object of class Produkt with data sourced from the database
     * via the ProduktDTO
     *
     * @param dto
     * @return "Produkt" object
     */
    public Produkt load(ProduktDTO dto) {
        double volumen = unitStringToDouble(dto.getBreite())
                * unitStringToDouble(dto.getHoehe())
                * unitStringToDouble(dto.getTiefe());
        double preis = unitStringToDouble(dto.getPreis());
        double gewicht = unitStringToDouble(dto.getGewicht());
        int bestand =dto.getBestand();
        return new Produkt(dto.getProdukt(), bestand, volumen, preis, gewicht);
    }

}