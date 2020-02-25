package de.bricks.brickmarket.web;

import de.bricks.brickmarket.BestandService;
import de.bricks.brickmarket.ModelService;
import de.bricks.brickmarket.models.BestellungsPosition;
import de.bricks.brickmarket.models.BestellungsSummary;
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
        System.out.println(summaries);
        return "uebersicht";
    }

    @SessionScope
    @GetMapping("/einkauf")
    //@ResponseBody
    public String trig(Model model) {
        List<Produkt> produkte = modelTranslator.alleProdukte();
        model.addAttribute("produkte", produkte);
        return "einkaeufer_maske";
    }

    @PostMapping(value = "/einkauf", params = "add")
    @SessionScope
    @Transactional
    //@ResponseBody
    public String name(String produkt, int anzahl) {
        Produkt addedProdukt = modelTranslator.produkt(produkt);
        int produktAnzahl = anzahl;
        bestandWriter.addBestand(addedProdukt, anzahl);
        return "redirect:/einkauf";
    }
}

