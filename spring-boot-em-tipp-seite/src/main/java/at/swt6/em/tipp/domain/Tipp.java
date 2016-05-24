package at.swt6.em.tipp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Tipp {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private int tippToreA;
	private int tippToreB;
	
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="email")
	private User user;
	
	@ManyToOne
	private Spiel spiel;

}
