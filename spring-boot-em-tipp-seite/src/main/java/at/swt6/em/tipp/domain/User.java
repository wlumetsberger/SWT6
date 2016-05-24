package at.swt6.em.tipp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	private String email;
	@NotNull
	private String name;
	
	@OneToMany(mappedBy="user")
	private List<Tipp> tipps;

}
