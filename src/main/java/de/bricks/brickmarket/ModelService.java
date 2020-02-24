package de.bricks.brickmarket;
import de.bricks.brickmarket.database.KundeRepository;
import de.bricks.brickmarket.database.dto.BestellungDTO;
import de.bricks.brickmarket.database.dto.BestellungRepository;
import de.bricks.brickmarket.database.dto.KundeDTO;
import de.bricks.brickmarket.database.dto.ProduktRepository;
import de.bricks.brickmarket.models.BestellungsSummary;
import de.bricks.brickmarket.models.Kunde;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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
    public BestellungsSummary loadSummary(BestellungDTO dto){
        Kunde kunde = load(kunden.findById(dto.getKunde()).get());
        return new BestellungsSummary(dto.getBestellnr(),dto.getDatum(),kunde.getName());
    }
    public Kunde load(KundeDTO dto){
        return new Kunde(dto.getKundennr(),dto.getName(),dto.getPlz(),dto.getStadt(),dto.getStrasse(),dto.getLng(),dto.getLat());
    }
}