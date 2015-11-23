package callofcactus.io;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Guus
 */
public class DatabaseManagerTest {
	private DatabaseManager databaseManager;

	@Before
	public void setUp() throws Exception {
		databaseManager = new DatabaseManager();
		databaseManager.changeToTestDataBase();
	}

	@Test
	public void testInsertHighScore() throws Exception {
		assertTrue(databaseManager.addHighScore(1, 1, 1));
		assertNotNull(null, databaseManager.readFromTable(DatabaseManager.tableEnum.SINGLEPLAYER));
		assertTrue(databaseManager.deleteFromTable(DatabaseManager.tableEnum.SINGLEPLAYER));
	}

	@Test
	public void testPassword() {
		String username = "Test mcTest";
		String password = "Test";
		databaseManager.addAccount(username, password);

		Assert.assertTrue(databaseManager.verifyAccount(username, password));

		Assert.assertFalse(databaseManager.verifyAccount(username, (password + "ed")));


		databaseManager.deleteFromTable(DatabaseManager.tableEnum.ACCOUNT);

	}

	@Test
	public void testGenerateTestData() {
		Assert.assertTrue(databaseManager.generateTestData());

	}

}