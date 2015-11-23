package callofcactus.io;


import callofcactus.account.Account;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.istack.internal.NotNull;
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

	@NotNull
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

	/**
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	public void changeToTestDataBase() {
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


