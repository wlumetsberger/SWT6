package swt6.orm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private Address address;
	private Set<LogbookEntry> logbookEntries = new HashSet<>();
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
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
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
		if (entry == null) {
			throw new IllegalArgumentException("Logbook Entry must not be null!");
		}
		if (entry.getEmployee() != null) {
			entry.getEmployee().getLogbookEntries().remove(entry);
		}
		this.getLogbookEntries().add(entry);
		entry.setEmployee(this);
	}

	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null || entry.getEmployee() != this) {
			throw new IllegalArgumentException("Logbook Entry must not be null, or is from another Employee!");
		}
		if(this.getLogbookEntries().contains(entry)){
			this.getLogbookEntries().remove(entry);
			entry.setEmployee(null);
		}
	}

	public Set<Project> getProjects() {
		return projects;
	}

	@SuppressWarnings("unused")
	private void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project) {
		// TODO add code
	}

	public void removeProject(Project project) {
		// TODO add code
	}

	public void detach() {
		// TODO add code
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%d: %s, %s (%4$td.%4$tm.%4$tY)", id, lastName, firstName, dateOfBirth));
		if (address != null)
			sb.append(", " + address);

		return sb.toString();
	}
}