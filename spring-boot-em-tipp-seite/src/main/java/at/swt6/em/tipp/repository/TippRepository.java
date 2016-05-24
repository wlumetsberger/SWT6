package at.swt6.em.tipp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import at.swt6.em.tipp.domain.Tipp;

public interface TippRepository extends JpaRepository<Tipp, Long> {

}
