package at.swt6.em.tipp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.swt6.em.tipp.domain.Spiel;
import at.swt6.em.tipp.domain.Team;
import at.swt6.em.tipp.service.ISpielService;
import at.swt6.em.tipp.service.ITeamService;

@Controller
public class SpielController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpielController.class.getName());
	
	@Autowired
	private ISpielService spielService;

	@Autowired
	private ITeamService teamService;

	@InitBinder
	  public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	  }
	
	@RequestMapping("/spiele")
	public String listGames(Model model) {
		// model.addAttribute("name", name);
		List<Spiel> spiele = spielService.getSpiele();
		model.addAttribute("spiele", spiele);
		return "spiel-uebersicht";
	}

	@RequestMapping("/spiele/entries/new")
	public String newGame(Model model) {
		List<Team> teams = teamService.getAllTeams();
		Spiel s = new Spiel();
		model.addAttribute("entry", s);
		model.addAttribute("teams", teams);

		return "spiel-detail";
	}

	@RequestMapping(value = "/spiele/entries/new", method = RequestMethod.POST)
	public String processNew(@ModelAttribute("entry") Spiel entry, BindingResult result, Model model) {
		LOGGER.info("Spiel hinzugefuegt: " + entry);
		spielService.saveOrUpdateSpiel(entry);
		model.addAttribute("spiele",spielService.getSpiele());
		return "spiel-uebersicht";
	}
	
	@RequestMapping("/spiele/entries/{gameId}/update")
	public String updateGame(@PathVariable Long gameId, Model model){
		List<Team> teams = teamService.getAllTeams();
		Spiel s = spielService.findSpielById(gameId);
		model.addAttribute("entry", s);
		model.addAttribute("teams", teams);
		return "spiel-detail";
	}
	
	@RequestMapping(value = "/spiele/entries/{gameId}/update", method = RequestMethod.POST)
	public String updateGame(@ModelAttribute("entry") Spiel entry, BindingResult result, Model model) {
		LOGGER.info("Spiel ändern: " + entry);
		spielService.saveOrUpdateSpiel(entry);
		model.addAttribute("spiele",spielService.getSpiele());
		return "spiel-uebersicht";
	}

}
