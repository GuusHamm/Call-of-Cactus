package callofcactus.io;

import callofcactus.BaseTest;
import callofcactus.multiplayer.serverbrowser.BrowserRoom;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by guushamm on 19-11-15.
 */
public class DatabaseManagerTest extends BaseTest {
	DatabaseManager databaseManager;
	String userName = "TestUser";
	String password = "SuperSecurePassword";

	@BeforeClass
	public void setUp() throws Exception {
		databaseManager = new DatabaseManager();
		databaseManager.changeToTestDataBase();
		databaseManager.deleteFromTable(DatabaseManager.tableEnum.ACCOUNT);
		databaseManager.generateTestData();
		databaseManager.addAccount(userName, password);
	}

	@Test
	public void testInsertHighScore() throws Exception {
		databaseManager.addHighScore(1, 1, 1);
		databaseManager.readFromTable(DatabaseManager.tableEnum.SINGLEPLAYER);
		databaseManager.deleteFromTable(DatabaseManager.tableEnum.SINGLEPLAYER);
	}

//	@Test
//	public void testPassword() {
//		String username = "Test mcTest";
//		String password = "Test";
//		databaseManager.addAccount(username, password);
//
//		Assert.assertTrue(databaseManager.verifyAccount(username, password));
//
//		Assert.assertFalse(databaseManager.verifyAccount(username, (password + "ed")));
//
//
//		databaseManager.deleteFromTable(DatabaseManager.tableEnum.ACCOUNT);
//
//	}

	@Test
	public void testGenerateTestData() {
		//Assert.assertTrue(databaseManager.generateTestData());

	}

	@Test
	public void testIsConnectionOpen()
	{
		assertTrue(databaseManager.isConnectionOpen());
	}

	@Test
	public void testAddAccount()
	{
		String testName = "TestMcTest";
		databaseManager.addAccount(testName, "Password");
		assertTrue(databaseManager.usernameExists(testName));
	}

	@Test
	public void testCreateRoom()
	{
		BrowserRoom browserRoom = new BrowserRoom(1, 0, "test", "test");
		databaseManager.createRoom(browserRoom);

		assertNotNull(databaseManager.getRooms());
	}

	@Test
	public void testGetAllAchievements()
	{
		HashMap<Integer, String> achievements = databaseManager.getAllAchievements();
		assertFalse(achievements.isEmpty());
	}

	@Test
	public void testAddAchievement()
	{
		databaseManager.addAchievement(databaseManager.getAccountID(userName), 1);

		ArrayList<String> completedAchievements = databaseManager.getCompletedAchievements(databaseManager.getAccountID(userName));
		assertFalse(completedAchievements.isEmpty());
	}

	@Test
	public void testGetNextGameID()
	{
		databaseManager.getNextGameID();
	}

	@Test
	public void testVerifyAccount()
	{
		assertTrue(databaseManager.verifyAccount(userName, password));
	}





}