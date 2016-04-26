package swt6.orm.domain.annotated;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Project implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  // private Employee manager;
  
  @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
  @JoinTable(name ="ProjectEmployee",
  joinColumns={
		  @JoinColumn(name="project_id")
  	}
  	,inverseJoinColumns={
  			@JoinColumn(name="employee_id")
  		}
  	
  )
  private Set<Employee> members = new HashSet<>();
  
  @OneToMany(mappedBy="project", cascade=CascadeType.ALL, fetch=FetchType.EAGER,orphanRemoval=true)
  private List<Module> modules;
  

  public Long getId() {
    return id;
  }

  public Project() {
  }

  public Project(String name) {
    this.name = name;
  }

  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Employee> getMembers() {
    return members;
  }

  public void setMembers(Set<Employee> members) {
    this.members = members;
  }

  public void addMember(Employee empl) {
    if (empl == null) {
      throw new IllegalArgumentException("Null Employee");
    }
    empl.getProjects().add(this);
    members.add(empl);
  }

  @Override
  public String toString() {
    return name;
  }
}
