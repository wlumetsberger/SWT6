package at.swt6.em.tipp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Spiel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Team teamA;
	@ManyToOne(cascade=CascadeType.ALL)
	private Team teamB;
	
	private int toreA;
	private int toreB;
	
	private Date date;
	
	
	
}
