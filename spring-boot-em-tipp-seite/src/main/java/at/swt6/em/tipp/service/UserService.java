package at.swt6.em.tipp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.swt6.em.tipp.domain.User;
import at.swt6.em.tipp.repository.UserRepository;

@Service
@Transactional
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	
	
	

}
