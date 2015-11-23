package callofcactus.io;


import callofcactus.account.Account;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Guus Hamm
 */

/**
 * Constructor of the DatabaseManager
 */
public class DatabaseManager {
	Connection connection;

	public DatabaseManager() {
		try {
			this.connection =
					(Connection) DriverManager.getConnection("jdbc:mysql://teunwillems.nl/CallOfCactus?" +
							"user=coc&password=callofcactusgame");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param score      you want to insert into the database
	 * @param waveNumber you want to insert into the database
	 * @param playerID   you want to insert into the database
	 * @return if the statement has been executed without errors
	 */
	public boolean addHighScore(int score, int waveNumber, int playerID) {
		String query = String.format("INSERT INTO SINGLEPLAYER(SCORE,WAVENUMBER,USERID) VALUES (%d,%d,%d);", score, waveNumber, playerID);
		return writeToDataBase(query);
	}

	public boolean addAccount(String username, String password) {
		HashMap<String, String> hashedAndSalted = salter(password, null);
		String query = String.format("INSERT INTO ACCOUNT(USERNAME,SALT,PASSWORD) VALUES (\"%s\",\"%s\",\"%s\");", username, hashedAndSalted.get("Salt"), hashedAndSalted.get("Password"));

		return writeToDataBase(query);
	}

	public boolean addMultiplayerResult(int playerID, int matchID, int score, int kills, int deaths) {
		String query = String.format("INSERT INTO PLAYERMATCH (ACCOUNTID,MATCHID,SCORE,KILLS,DEATHS VALUES (%d,%d,%d,%d,%d);", playerID, matchID, score, kills, deaths);

		return writeToDataBase(query);
	}

	private HashMap<String, String> salter(String password, String salt) {
		HashMap<String, String> result = new HashMap<>();
		if (salt == null) {

			salt = BCrypt.gensalt();

		}

		result.put("Salt", salt);

		result.put("Password", BCrypt.hashpw(password, salt));

		return result;
	}

	public ArrayList<Account> getAccounts() {
		ArrayList<Account> accounts = new ArrayList<>();

		ResultSet resultSet = readFromTable(tableEnum.ACCOUNT);

		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String username = resultSet.getString("USERNAME");
				Account account = new Account(username);
				account.setID(id);
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return accounts;
	}

	public ResultSet getSortedScores(int matchID) {
		String query = String.format("SELECT USERNAME, SCORE, KILLS, DEATHS FROM PLAYERMATCH P JOIN ACCOUNT A ON (P.ACCOUNTID = A.ID) WHERE MATCHID = %d ORDER BY SCORE DESC;", matchID);

		return readFromDataBase(query);
	}

	public HashMap<String, String> getResultsOfPlayer(int playerID) {
		HashMap<String, String> results = new HashMap<>();
		String query = String.format("SELECT USERNAME,SUM(SCORE) AS SCORETOTAL,SUM(KILLS) AS KILLS, SUM(DEATHS) AS DEATHS, SUM(P.ID) AS GAMESPLAYED FROM PLAYERMATCH P JOIN ACCOUNT A ON (P.ACCOUNTID = A.ID) WHERE ACCOUNTID = 1;", playerID);

		ResultSet resultSet = readFromDataBase(query);

		try {
			while (resultSet.next()) {
				results.put("Username", resultSet.getString("USERNAME"));
				results.put("TotalScore", resultSet.getString("SCORETOTAL"));
				results.put("TotalKills", resultSet.getString("KILLS"));
				results.put("TotalDeaths", resultSet.getString("DEATHS"));
				results.put("TotalGames", resultSet.getString("GAMESPLAYED"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return results;
	}

	public boolean verifyAccount(String username, String password) {
		String query = String.format("SELECT ID FROM ACCOUNT WHERE PASSWORD = \"%s\" AND USERNAME = \"%s\";", username, password);
		try {
			if (readFromDataBase(query).next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * @param table you want to read from
	 * @return ResultSet from all the data in the desired
	 */
	public ResultSet readFromTable(tableEnum table) {
		String query = String.format("SELECT * FROM %s;", table);
		return readFromDataBase(query);
	}

	/**
	 *
	 //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
	 //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
	 //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
	 //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
	 //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
	 //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
	 //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
	 //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
	 *
	 *
	 *		Don't even think about making this method public, I will find you.......
	 *
	 *
	 //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
	 //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
	 //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
	 //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
	 //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
	 //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
	 //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
	 //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
	 *
	 * @param query you want to write to the database
	 * @return if the query executed successfully
	 */
	private boolean writeToDataBase(String query) {
		try {
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 *
	 //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
	 //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
	 //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
	 //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
	 //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
	 //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
	 //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
	 //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
	 *
	 *
	 *		Don't even think about making this method public, I will find you.......
	 *
	 *
	 //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
	 //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
	 //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
	 //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
	 //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
	 //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
	 //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
	 //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
	 *
	 * @param query you want to read from the database
	 * @return the ResultSet of the query
	 */
	private ResultSet readFromDataBase(String query) {
		ResultSet results = null;
		try {
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
			statement.execute();
			results = statement.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * @param table from tableEnum that you want to delete stuff from
	 * @return if the statement has been executed without errors
	 */
	public boolean deleteFromTable(tableEnum table) {
		String query = String.format("DELETE FROM %s;", table);
		return writeToDataBase(query);
	}

	public boolean usernameExists(String username) {
		return getAccounts().stream().filter(e -> e.getUsername().equals(username)).findAny() == null;
	}

	public void changeToTestDataBase() {
	}

	public boolean generateTestData() {
		//First remove all DB Data
		String query = "SET FOREIGN_KEY_CHECKS = 0;\n" +
				"\n" +
				"TRUNCATE TABLE ACCOUNTACHIEVEMENT;\n" +
				"TRUNCATE TABLE PLAYERMATCH;\n" +
				"TRUNCATE TABLE MULTIPLAYERMATCH;\n" +
				"TRUNCATE TABLE SINGLEPLAYER;\n" +
				"TRUNCATE TABLE ACCOUNT;\n" +
				"TRUNCATE TABLE ACHIEVEMENT;\n" +
				"TRUNCATE TABLE GAMEMODE;\n" +
				"\n" +
				"SET FOREIGN_KEY_CHECKS = 1;";
		writeToDataBase(query);

		//Next generate 10 accounts
		addAccount("FlipDeMier", "iziPassw123");
		addAccount("HansDuo", "800815ftw");
		addAccount("Fr0d0", "H0bb1t!");
		addAccount("Jan", "321");
		addAccount("Theodore", "Geronimooo!!!111");
		addAccount("FrogLord", "TadPole");
		addAccount("REMI", "IMER");
		addAccount("KinckyKong", "1*20!@kadKasa!");
		addAccount("tntzlr", "HandtasjeDeKat");
		addAccount("Bam0wnage", "JJJoooJJJooo");

		//Last add all other test data
		query = "INSERT INTO ACHIEVEMENT (ID, NAME, DESCRIPTION) " +
				"VALUES (1,'Hot Shot','Fire 100 bullets without one of them missing their target')," +
				"(2,'Pure Nature','Win a round without using any pickups')," +
				"(3,'Alien Invasion Terror','Win a round only moving left or right')," +
				"(4,'The Big 5','Pickup 5 different power-ups in one round')," +
				"(5,'Try All, Master All','Win a round with all current roles')," +
				"(6,'Ultimate Streak, Master All','Win 10 rounds in a row');";
		writeToDataBase(query);
		query = "INSERT INTO ACCOUNTACHIEVEMENT (ACCOUNTID, ACHIEVEMENTID) " +
			    "VALUES (1,1), (1,3), (3,1), (3,4), (3,6)," +
			    "(4,1), (5,2), (5,3), (5,5), (5,6), (8,1)," +
			    "(8,6), (9,4), (10,1), (10,2), (10,3)," +
			    "(10,4), (10,5), (10,6);";
		writeToDataBase(query);
		query = "INSERT INTO GAMEMODE (DESCRIPTION, MAXPLAYERS, NAME) " +
				"VALUES ('Endless waves of enemys will spawn and try to destroy you, survive as long as possible and get a high score!',4,'Endless')," +
				"('Seek and kill all other players! The player with the highest score at the end wins!',8,'FreeForALl')," +
				"('Join one of two teams and kill the opposite one. You can`t damage teammates in this mode. Last team standing wins!',10,'Team VS Team');";
		writeToDataBase(query);
		query = "INSERT INTO MULTIPLAYERMATCH (GAMEMODEID) " +
				"VALUES (1), (3), (3), (1), (2)," +
				"(1), (1), (2), (3), (2);";
		writeToDataBase(query);
		query = "INSERT INTO PLAYERMATCH (ACCOUNTID, MATCHID, SCORE, KILLS, DEATHS) " +
				"VALUES (1, 1, 300, 5, 0)," +
				"(1, 3, 150, 10, 2)," +
				"(1, 6, 222, 12, 6)," +
				"(3, 1, 142, 2, 11)," +
				"(3, 2, 123, 9, 21)," +
				"(4, 2, 331, 5, 3)," +
				"(4, 7, 300, 3, 0)," +
				"(4, 8, 1122, 12, 0)," +
				"(5, 10, 6455, 53, 6)," +
				"(6, 3, 112, 2, 22)," +
				"(8, 4, 850, 0, 8)," +
				"(9, 5, 334, 0, 12)," +
				"(10, 8, 1122, 44, 12)," +
				"(10, 9, 2324, 20, 0)," +
				"(10, 10, 2100, 18, 4);";
		writeToDataBase(query);
		query = "INSERT INTO SINGLEPLAYER (SCORE, WAVENUMBER, ACCOUNTID) " +
				"VALUES (115, 10, 2)," +
				"(2219, 44, 2)," +
				"(11, 2, 2)," +
				"(176, 7, 4)," +
				"(450, 7, 4)," +
				"(2744, 24, 6)," +
				"(7492, 71, 7)," +
				"(177, 13, 9)," +
				"(80, 3, 9)," +
				"(2, 1, 9);";
		writeToDataBase(query);
		return writeToDataBase(query);

	}

	/**
	 * An Enum with all the tables in the database in it.
	 */
	public enum tableEnum {
		ACCOUNT,
		ACCOUNTACHIEVEMENT,
		ACHIEVEMENT,
		GAMEMODE,
		MULTIPLAYERMATCH,
		PLAYERMATCH,
		SINGLEPLAYER

	}
}


