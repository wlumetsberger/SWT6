package at.swt6.em.tipp.service;

import at.swt6.em.tipp.domain.Spiel;
import at.swt6.em.tipp.domain.User;

public interface ITippService {
	
	boolean submitTipp(int toreA, int toreB , Spiel s, User u);

}
