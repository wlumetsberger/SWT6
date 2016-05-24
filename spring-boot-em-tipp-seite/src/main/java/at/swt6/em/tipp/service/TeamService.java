package at.swt6.em.tipp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.swt6.em.tipp.domain.Team;
import at.swt6.em.tipp.repository.TeamRepository;

@Service
@Transactional
public class TeamService implements ITeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	@Override
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

}
