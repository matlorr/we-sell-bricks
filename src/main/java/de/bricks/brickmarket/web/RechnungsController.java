package de.bricks.brickmarket.web;

import de.bricks.brickmarket.ModelService;
import de.bricks.brickmarket.models.BestellungsPosition;
import de.bricks.brickmarket.models.BestellungsSummary;
import de.bricks.brickmarket.models.Kunde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RechnungsController{
    ArrayList<BestellungsPosition> positionen = new ArrayList<BestellungsPosition>();

    @Autowired
    ModelService modelTranslator;

    @GetMapping("/")
    public String index(Model model){
        List<BestellungsSummary> summaries = modelTranslator.alleBestellungen();
        model.addAttribute("bestellungen",summaries);
        System.out.println(summaries);
        return "uebersicht";
    }

    @GetMapping("/verkauf")
    public String verkauf(Model model){
        return "verkauf";
    }
}

