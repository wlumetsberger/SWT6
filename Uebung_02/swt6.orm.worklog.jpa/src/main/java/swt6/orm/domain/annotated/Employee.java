package swt6.orm.domain.annotated;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name="employee_type",discriminatorType=DiscriminatorType.STRING)
//@DiscriminatorValue("E")
public class Employee implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.TABLE)
  private Long id;

  private String firstName;
  private String lastName;

  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

    @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "zipCode", column = @Column(name = "address_zipCode")),
    @AttributeOverride(name = "city", column = @Column(name = "address_city")),
    @AttributeOverride(name = "street", column = @Column(name = "address_street")) })
  private Address address;

  @OneToMany(mappedBy="employee", cascade=CascadeType.ALL, fetch=FetchType.EAGER,orphanRemoval=true)
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  @ManyToMany(mappedBy="members",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
  @Transient
  private Set<Project> projects = new HashSet<>();

  // classes persisted by Hibernate must have a default constructor
  // (newInstance of reflection API)
  public Employee() {
  }

  public Employee(String firstName, String lastName, Date dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
  }

  public Employee(String firstName, String lastName, Date dateOfBirth, Address address) {
    this(firstName, lastName, dateOfBirth);
    setAddress(address);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Set<LogbookEntry> getLogbookEntries() {
    return logbookEntries;
  }

  @SuppressWarnings("unused")
  private void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
    this.logbookEntries = logbookEntries;
  }

  public void addLogbookEntry(LogbookEntry entry) {
    if (entry.getEmployee() != null) {
      entry.getEmployee().logbookEntries.remove(entry);
    }

    logbookEntries.add(entry);
    entry.setEmployee(this);
  }

  public void removeLogbookEntry(LogbookEntry entry) {
    logbookEntries.remove(entry);
  }

  public Set<Project> getProjects() {
    return projects;
  }

  @SuppressWarnings("unused")
  private void setProjects(Set<Project> projects) {
    this.projects = projects;
  }

  public void addProject(Project proj) {
    if (proj == null) {
      throw new IllegalArgumentException("Null Project");
    }
    proj.getMembers().add(this);
    projects.add(proj);
  }

  @Override
  public String toString() {
    DateFormat fmt = DateFormat.getDateInstance();
    StringBuffer sb = new StringBuffer();
    sb.append(id + ": " + lastName + ", " + firstName );
    if (address != null) {
      sb.append(", " + address);
    }

    return sb.toString();
  }
}