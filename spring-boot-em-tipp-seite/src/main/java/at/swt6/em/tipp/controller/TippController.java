package at.swt6.em.tipp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpMethod;
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
import at.swt6.em.tipp.domain.Tipp;
import at.swt6.em.tipp.domain.User;
import at.swt6.em.tipp.service.ISpielService;
import at.swt6.em.tipp.service.ITippService;
import at.swt6.em.tipp.service.IUserService;

@Controller
public class TippController {
	
	@Autowired
	private ISpielService spielService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITippService tippService;
	
	@InitBinder
	  public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	  }
	
	@RequestMapping("/spiele/entries/{gameId}/addTipp")
	public String addTipp(@PathVariable Long gameId, Model model){
		List<User> users = userService.findAllUsers();
		
		Spiel s = spielService.findSpielById(gameId);
		Tipp t = new Tipp();
		t.setSpiel(s);
		model.addAttribute("spiel", s);
		model.addAttribute("users", users);
		model.addAttribute("entry", t);
		return "add-Tipp";
	}
	
	@RequestMapping(value="/spiele/entries/{gameId}/addTipp", method = RequestMethod.POST)
	public String saveTipp(@PathVariable Long gameId,@ModelAttribute("entry") Tipp entry, BindingResult result, Model model){
		tippService.submitTipp(entry.getTippToreA(), entry.getTippToreB(), entry.getSpiel(), entry.getUser());
		model.addAttribute("spiele",spielService.getSpiele());
		return "spiel-uebersicht";
	}

}
