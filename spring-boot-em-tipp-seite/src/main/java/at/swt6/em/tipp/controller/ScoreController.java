package at.swt6.em.tipp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import at.swt6.em.tipp.service.ISpielService;

@Controller
public class ScoreController {

	@Autowired
	private ISpielService spielService;
	
	@RequestMapping("/score")
	public String listHighScore(Model model) {
		model.addAttribute("scores", spielService.generateHighScoreList());
		return "score-list";
	}

}
