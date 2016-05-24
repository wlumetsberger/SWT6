package at.swt6.em.tipp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import at.swt6.em.tipp.domain.Team;
import at.swt6.em.tipp.domain.User;
import at.swt6.em.tipp.repository.SpielRepository;
import at.swt6.em.tipp.repository.TeamRepository;
import at.swt6.em.tipp.repository.UserRepository;
import at.swt6.em.tipp.service.ITippService;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	@Transactional(propagation = Propagation.REQUIRED)
	public CommandLineRunner demo(UserRepository userRepo, TeamRepository teamRepo, SpielRepository spielRepo,
			ITippService tippService) {
		return (args) -> {
			log.info("create test users");
			Application.createUsers(userRepo);
			log.info("create test teams");
			Application.createTeams(teamRepo);
		};

	}

	/**
	 * Creates 10 Test-Users
	 * 
	 * @param userRepo
	 */
	private static void createUsers(UserRepository userRepo) {
		for (int i = 1; i <= 10; i++) {
			User u = new User();
			u.setEmail("user_" + i + "@test.at");
			u.setName("User_" + i);
			u = userRepo.save(u);
		}
	}

	/**
	 * Creates 10 Test-Teams
	 * 
	 * @param teamRepo
	 */
	private static void createTeams(TeamRepository teamRepo) {
		for (int i = 1; i <= 10; i++) {
			Team t = new Team();
			t.setName("Team_" + i);
			teamRepo.save(t);
		}
	}
}
