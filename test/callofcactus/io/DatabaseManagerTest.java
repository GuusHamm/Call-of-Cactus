package callofcactus.io;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by guushamm on 19-11-15.
 */
public class DatabaseManagerTest {
	DatabaseManager databaseManager;

	@Before
	public void setUp() throws Exception {
		databaseManager = new DatabaseManager();
		databaseManager.changeToTestDataBase();
	}

	@Test
	public void testInsertHighScore() throws Exception {
		databaseManager.addHighScore(1, 1, 1);
		databaseManager.readFromTable(DatabaseManager.tableEnum.SINGLEPLAYER);
		databaseManager.deleteFromTable(DatabaseManager.tableEnum.SINGLEPLAYER);
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
	public void testGetConnection() throws Exception {

	}

	@Test
	public void generateTestData(){

	}

}