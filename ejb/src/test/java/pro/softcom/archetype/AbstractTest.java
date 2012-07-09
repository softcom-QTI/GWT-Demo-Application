package pro.softcom.archetype;

import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import pro.softcom.archetype.constants.AppServerConstants;


/**
 * Basisklasse f�r die EJB Unit-Test
 */
public abstract class AbstractTest {
	@Resource
	private UserTransaction userTransaction;

	@PersistenceContext(unitName = AppServerConstants.PU_NAME)
	private EntityManager entityManager;

	private InitialContext context;

	/**
	 * Falls im Test eine {@link UserTransaction} gestartet wurde, wird sie
	 * zur�ck gesetzt (commit / rollback)
	 * 
	 * @throws Exception
	 */
	@AfterMethod(alwaysRun = true)
	public void resetUserTransaction() throws Exception {
		if (getUserTransaction().getStatus() == Status.STATUS_ACTIVE) {
			getUserTransaction().commit();
		} else if (getUserTransaction().getStatus() == Status.STATUS_MARKED_ROLLBACK) {
			getUserTransaction().rollback();
		}
	}

	/**
	 * OpenEJB initialisieren und DB basierend auf den Entities erstellen.
	 * 
	 * @throws Exception
	 *             falls ein Fehler aufrtritt
	 */
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		// JNDI InitialContext Factory als System Property setzen, damit auch
		// "new InitialContext()" funktioniert
		System.setProperty("java.naming.factory.initial",
				"org.apache.openejb.client.LocalInitialContextFactory");

		// OpenEJB configuration to load UnitTest
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream(
				"/unittest-jndi.properties"));

		context = new InitialContext(properties);
		context.bind("inject", this);

	}

	/**
	 * Test abschliessen.
	 * 
	 * @throws Exception
	 *             falls ein Fehler aufrtritt
	 */
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		if (context != null) {
			context.close();
		}
	}

	/**
	 * Gibt die User Transaction zur�ck
	 * 
	 * @return die {@link UserTransaction}
	 */
	protected UserTransaction getUserTransaction() {
		return userTransaction;
	}

	/**
	 * Gibt den EntityManager zur�ck
	 * 
	 * @return den {@link EntityManager}
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
