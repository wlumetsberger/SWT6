package at.swt6.em.tipp.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.swt6.em.tipp.domain.Spiel;
import at.swt6.em.tipp.domain.Tipp;
import at.swt6.em.tipp.domain.User;
import at.swt6.em.tipp.repository.TippRepository;

@Service
public class TippService  implements ITippService{

	@Autowired
	TippRepository tippRepo;
	
	/**
	 * Validates given parameters and stores tipp via repository
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean submitTipp(int toreA, int toreB, Spiel s, User u) {
		if(!validateTipp(toreA, toreB, s, u)){
			return false;
		}
		Tipp t = new Tipp();
		t.setSpiel(s);
		t.setUser(u);
		t.setTippToreA(toreA);
		t.setTippToreB(toreB);
		tippRepo.save(t);
		return true;
	}
	
	/**
	 * Method validates if the given parameters for a Tipp are valid
	 * @param toreA
	 * @param toreB
	 * @param s
	 * @param u
	 * @return
	 */
	private boolean validateTipp(int toreA, int toreB, Spiel s, User u){
		if(s == null || u == null) return false;
		return true;
	}

}
