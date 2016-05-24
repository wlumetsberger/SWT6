package at.swt6.em.tipp.service;

import java.util.List;

import at.swt6.em.tipp.domain.User;

public interface IUserService {
	List<User> findAllUsers();
}
