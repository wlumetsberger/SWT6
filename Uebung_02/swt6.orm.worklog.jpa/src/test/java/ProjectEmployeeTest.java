import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import swt6.orm.domain.annotated.Project;
import swt6.orm.jpa.JPAUtil;
import swt6.orm.jpa.JPAWorkLogManager;

public class ProjectEmployeeTest extends BaseTest{

	@BeforeClass
	public static void setConfiguration() throws Exception{
		BaseTest.testDataSet = "Employee-Test-Initial.xml";
		BaseTest.initializeHibernateContext();
	}
	
	@Test
	public void getProjects(){
		Assert.assertEquals(JPAWorkLogManager.getProjects().size(),2);
	}
	@Test
	public void checkEmployeesForProject(){
		Assert.assertEquals(JPAWorkLogManager.getProjects().size(),2);
		Project p = JPAWorkLogManager.getProject(2);
		Assert.assertNotNull(p);
		Assert.assertEquals(p.getMembers().size(), 2);
	}
	
	@Test
	public void checkAddProject(){
		Assert.assertEquals(JPAWorkLogManager.getProjects().size(),2);
		Project p = new Project("asdf");
		p.addMember(JPAWorkLogManager.getEmployee(1));
		JPAWorkLogManager.saveProject(p);
		Assert.assertEquals(JPAWorkLogManager.getProjects().size(),3);
	}
}
