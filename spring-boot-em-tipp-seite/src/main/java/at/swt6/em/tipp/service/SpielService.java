package at.swt6.em.tipp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.swt6.em.tipp.domain.Score;
import at.swt6.em.tipp.domain.Spiel;
import at.swt6.em.tipp.domain.User;
import at.swt6.em.tipp.repository.SpielRepository;
import at.swt6.em.tipp.repository.UserRepository;

@Service
@Transactional
public class SpielService implements ISpielService {

	@Autowired
	private SpielRepository spielRepostiory;

	@Autowired
	private UserRepository userRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveOrUpdateSpiel(Spiel s) {
		spielRepostiory.save(s);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<Spiel> getSpiele() {
		Sort sort = new Sort(Direction.ASC, "date");
		return spielRepostiory.findAll(sort);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public Spiel findSpielById(Long id) {
		return spielRepostiory.findOne(id);
	}

	/**
	 * Wenn das Spiel bereits Stattgefunden hat 
	 * kann es gewertet werden
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<Score> generateHighScoreList() {
		List<Score> score = new ArrayList<>();
		List<User> users = userRepository.findAll();
		users.forEach(u -> {
			Score s = new Score();
			s.setPlayerName(u.getName());
			u.getTipps().forEach(tipp -> {
				if(tipp.getSpiel().getDate().getTime() <= (new Date()).getTime()){
					if (tipp.getTippToreA() == tipp.getSpiel().getToreA()
							&& tipp.getTippToreB() == tipp.getSpiel().getToreB()) {
						s.setPoints(s.getPoints() + 1);
					}
				}
				
			});
			score.add(s);
		});

		return score.stream().sorted((s1, s2) -> Integer.compare(s2.getPoints(), s1.getPoints()))
				.collect(Collectors.toList());

	}

}
