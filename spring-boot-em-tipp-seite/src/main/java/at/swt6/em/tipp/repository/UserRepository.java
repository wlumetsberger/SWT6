package at.swt6.em.tipp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import at.swt6.em.tipp.domain.User;


public interface UserRepository  extends JpaRepository<User, String>{

	List<User>findByName(String name);
}
