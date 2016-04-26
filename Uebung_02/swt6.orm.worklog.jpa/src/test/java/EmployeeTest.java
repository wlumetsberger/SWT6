import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import swt6.orm.domain.annotated.Address;
import swt6.orm.domain.annotated.Employee;
import swt6.orm.domain.annotated.LogbookEntry;
import swt6.orm.jpa.JPAWorkLogManager;

public class EmployeeTest extends BaseTest{

	@BeforeClass
	public static void setConfiguration() throws Exception{
		BaseTest.testDataSet = "Employee-Test-Initial.xml";
		BaseTest.initializeHibernateContext();
	}
	
	@Test
	public void checkEmployees(){
		Assert.assertEquals(JPAWorkLogManager.getEmployess().size(),2);
	}
	
	@Test
	public void checkInsertNewEmployee(){
		Assert.assertEquals(JPAWorkLogManager.getEmployess().size(),2);
		Employee e = new Employee();
		e.setFirstName("asdf");
		e.setLastName("asdf");
		e.setAddress(new Address("4020","Linz","Ramsauerstraße"));
		JPAWorkLogManager.saveEmployee(e);
		Assert.assertEquals(JPAWorkLogManager.getEmployess().size(), 3);
	}
	
	@Test
	public void checkDeleteEmployee(){
		Assert.assertEquals(JPAWorkLogManager.getEmployess().size(),2);
		Employee emp = JPAWorkLogManager.getEmployee(2);
		Assert.assertEquals(emp.getId().longValue(), 2);
		JPAWorkLogManager.deleteEmployee(emp);
		Assert.assertEquals(JPAWorkLogManager.getEmployess().size(),1);
	}
	
	@Test
	public void checkEmployeeHasLogBookEntries(){
		Assert.assertEquals(JPAWorkLogManager.getEmployess().size(),2);
		Assert.assertEquals(JPAWorkLogManager.getEmployee(1).getLogbookEntries().size(),1);
	}
	
	@Test
	public void checkRemoveLogBookEntry(){
		Assert.assertEquals(JPAWorkLogManager.getEmployee(1).getLogbookEntries().size(),1);
		Employee e = JPAWorkLogManager.getEmployee(1);
		Assert.assertNotNull(e);
		LogbookEntry entry = e.getLogbookEntries().stream().findFirst().get();
		e.removeLogbookEntry(entry);
		e= JPAWorkLogManager.updateEmployee(e);
		Assert.assertNotNull(e);
		Assert.assertEquals(e.getLogbookEntries().size(),0);
		Assert.assertEquals(JPAWorkLogManager.getEmployee(1).getLogbookEntries().size(),0);
	}	
	
	@Test
	public void checkAddLogBookEntry(){
		Assert.assertEquals(JPAWorkLogManager.getEmployee(1).getLogbookEntries().size(),1);
		Employee e = JPAWorkLogManager.getEmployee(1);
		Assert.assertNotNull(e);
		LogbookEntry entry = new LogbookEntry("keine",new Date(), new Date());
		e.addLogbookEntry(entry);
		e= JPAWorkLogManager.updateEmployee(e);
		Assert.assertNotNull(e);
		Assert.assertEquals(e.getLogbookEntries().size(),2);
		Assert.assertEquals(JPAWorkLogManager.getEmployee(1).getLogbookEntries().size(),2);
		
	}
	
	@Test
	public void changeEmployeeInformation(){
		Employee e = JPAWorkLogManager.getEmployee(1);
		e.setFirstName("asdf");
		e = JPAWorkLogManager.updateEmployee(e);
		Assert.assertNotNull(e);
		Assert.assertEquals(e.getFirstName(),"asdf");
		Assert.assertEquals(JPAWorkLogManager.getEmployee(1).getFirstName(),"asdf");
		
	}

}
