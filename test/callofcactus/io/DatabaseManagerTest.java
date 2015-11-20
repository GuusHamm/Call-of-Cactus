package callofcactus.io;

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
	}

	@Test
	public void testInsertHighScore() throws Exception {
		databaseManager.addHighScore(1, 1, 1);
		databaseManager.readFromTable(DatabaseManager.tableEnum.SINGLEPLAYER);
		databaseManager.deleteFromTable(DatabaseManager.tableEnum.SINGLEPLAYER);
	}

	@Test
	public void testGetConnection() throws Exception {

	}
}