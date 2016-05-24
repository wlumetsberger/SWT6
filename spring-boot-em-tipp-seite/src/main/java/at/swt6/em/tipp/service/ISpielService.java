package at.swt6.em.tipp.service;

import java.util.List;

import at.swt6.em.tipp.domain.Score;
import at.swt6.em.tipp.domain.Spiel;

public interface ISpielService {

	void saveOrUpdateSpiel(Spiel s);
	List<Spiel> getSpiele();
	Spiel findSpielById(Long id);
	
	List<Score> generateHighScoreList();
}
