package swt6.orm.domain.annotated;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Module {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },
				fetch = FetchType.EAGER, optional = false)
	private LogbookEntry logBookEntry;
	

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },
			fetch = FetchType.EAGER, optional = false)
private Project project;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LogbookEntry getLogBookEntry() {
		return logBookEntry;
	}

	public void setLogBookEntry(LogbookEntry logBookEntry) {
		this.logBookEntry = logBookEntry;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
	
	

}
