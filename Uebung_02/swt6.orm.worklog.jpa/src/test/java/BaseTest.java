import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import swt6.orm.jpa.JPAUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(swt6.orm.jpa.JPAUtil.class)
public abstract class BaseTest {

	protected final Logger logger = Logger.getLogger(this.getClass());
	protected static String testDataSet = "test-dataset.xml";
	protected static String persistenceUnit = "unit-testing";//"";

	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	private static IDatabaseConnection connection;
	private static IDataSet dataset;

	/**
	 * Methode muss immer im @BeforeClass aufgerufen werden Davor können
	 * testDataSet sowie persistenceUnit angepasst werden
	 * 
	 * @throws DatabaseUnitException
	 */
	public static void initializeHibernateContext() throws DatabaseUnitException, SQLException {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
		connection = new DatabaseConnection(((SessionImpl) (em.getDelegate())).connection());

		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		dataset = flatXmlDataSetBuilder
				.build(Thread.currentThread().getContextClassLoader().getResourceAsStream(testDataSet));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
	}
	
	/**
	 * JPAUtil-> klasse muss mit einem Mock versehen werden, um den richtigen entityManager zurückzuliefern
	 */
	@Before
	public void doStaticMocks(){
		em.clear();
		PowerMockito.stub(PowerMockito.method(JPAUtil.class, "getEntityManager")).toReturn(em);

	}
	

	/**
	 * Wurde alles erfolgreich Initalisiert, so können nach jedem Test die
	 * Test-Daten auf einen Initialstand zurück gesetzt werden
	 * 
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 */
	@After
	public void cleanUp() throws DatabaseUnitException, SQLException {
		if (connection != null && dataset != null) {
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
		}
	}

	/**
	 * Wurde ein EntityManager sowie eine Factory dafür erzeugt, und geöffnet,
	 * so muss diese geschlossen werden
	 */
	@AfterClass
	public static void cleanUpHibernateContext() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}

	}

}
