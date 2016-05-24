package at.swt6.em.tipp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import at.swt6.em.tipp.domain.Team;

public interface TeamRepository extends JpaRepository<Team, String> {

}
