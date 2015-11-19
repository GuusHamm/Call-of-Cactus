package callofcactus.io;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
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
//			this.connection = (Connection) DriverManager.getConnection("com.mysql.teunwillems.nl","coc","callofcactusgame");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean insertHighScore(int score, int waveNumber, int playerID) {
		try {
			String query = String.format("INSERT INTO SINGLEPLAYER(SCORE,WAVENUMBER,USERID) VALUES (%d,%d,%d);", score, waveNumber, playerID);
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;


	}


	public Connection getConnection() {
		return connection;
	}
}


