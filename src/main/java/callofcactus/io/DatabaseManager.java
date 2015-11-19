package callofcactus.io;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by guushamm on 19-11-15.
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

	public boolean insertHighScore(int score, int waveNumber, int playerID) {
		String query = String.format("INSERT INTO SINGLEPLAYER(SCORE,WAVENUMBER,USERID) VALUES (%d,%d,%d);", score, waveNumber, playerID);
		return writeToDataBase(query);
	}

	;

	public ResultSet getHighScores() {
		return readFromDataBase("SELECT * FROM SINGLEPLAYER");
	}

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

	public boolean deleteFromTable(tableEnum table) {
		String query = String.format("DELETE FROM %s;", table);
		return writeToDataBase(query);
	}

	public Connection getConnection() {
		return connection;
	}


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


