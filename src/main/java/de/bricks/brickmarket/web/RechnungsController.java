package de.bricks.brickmarket.web;
import de.bricks.brickmarket.BestandService;
import de.bricks.brickmarket.ModelService;
import de.bricks.brickmarket.models.Bestellung;
import de.bricks.brickmarket.models.BestellungsPosition;
import de.bricks.brickmarket.models.BestellungsSummary;
import de.bricks.brickmarket.models.Kunde;
import de.bricks.brickmarket.models.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;
import java.util.ArrayList;
import java.util.List;

@SessionScope
@Controller
public class RechnungsController{

    ArrayList<BestellungsPosition> positionen = new ArrayList<BestellungsPosition>();

    @Autowired
    ModelService modelTranslator;

    @Autowired
    BestandService bestandWriter;


    @GetMapping("/")
    public String index(Model model){
        List<BestellungsSummary> summaries = modelTranslator.alleBestellungen();
        model.addAttribute("bestellungen",summaries);
        return "uebersicht";
    }

    @SessionScope
    @GetMapping("/verkauf")
    public String verkauf(Model model) {
        List<Kunde> kunden = modelTranslator.alleKunden();
        List<Produkt> produkte = modelTranslator.alleProdukte();
        model.addAttribute("kunden", kunden);
        model.addAttribute("einkaufsliste", positionen);
        model.addAttribute("produkte", produkte);
        return "verkauf";
    }

    @SessionScope
    @PostMapping(value = "/verkauf", params = "add")
    public String positionHinzufügen(String produkt, int anzahl){
        positionen.add(new BestellungsPosition(0, anzahl, modelTranslator.produkt(produkt)));
        return "redirect:/verkauf";
    }

    @SessionScope
    @PostMapping(value = "/verkauf", params = "submit")
    @Transactional
    public String verkaufen(){
        if(bestandWriter.checkBestand(positionen)){
            bestandWriter.decreaseBestand(positionen);
            positionen = new ArrayList<>();
            return  "verkaufs_abschluss";
        }
        return "redirect:/verkauf";
    }

    @GetMapping("/details")
    public String index(Model model, Long bestellung) {
        Bestellung zuBestellen = modelTranslator.bestellung(bestellung);
        model.addAttribute("bestellung", zuBestellen);
        return "details";
    }

    @SessionScope
    @GetMapping("/einkauf")
    //@ResponseBody
    public String trig(Model model) {
        List<Produkt> produkte = modelTranslator.alleProdukte();
        model.addAttribute("produkte", produkte);
        return "einkauf";
    }

    @PostMapping(value = "/einkauf", params = "add")
    @SessionScope
    @Transactional
    //@ResponseBody
    public String name(String produkt, int anzahl) {
        Produkt addedProdukt = modelTranslator.produkt(produkt);
        bestandWriter.addBestand(addedProdukt, anzahl);
        return "redirect:/einkauf";
    }
}

