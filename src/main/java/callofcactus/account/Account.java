package callofcactus.account;

import callofcactus.Game;
import callofcactus.io.DatabaseManager;

public class Account {
	private int ID;

	private Game currentGame;
	private String username;
	private String password;
	/**
	 * creates a new callofcactus.account with the following parameters:
	 *
	 * @param username  : The name that will be displayed, this will be used to log in. must be unique
	 */
	public Account(String username) {
		this.username = username;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * This function is used to add a callofcactus.account to a callofcactus
	 *
	 * @param game : The callofcactus which the callofcactus.account tries to join
	 * @return true when success, false when failed
	 */
	public boolean joinGame(Game game) {
		// TODO - implement Account.joinGame
		// TODO - check if callofcactus is active
		throw new UnsupportedOperationException();
	}

	/**
	 * Exits the player from his current callofcactus
	 */
	public void exitGame() {
		// TODO - implement Account.exitGame
		// TODO - check if callofcactus is running
		throw new UnsupportedOperationException();
	}

	/**
	 * This function is called to verify an callofcactus.account.
	 * It is used as a login function.
	 *
	 * @param username : The name that will be displayed, this will be used to log in. a username must be unique
	 * @param password : The password that the user chose to login.
	 * @return the callofcactus.account which matches the given username and password or null if none match
	 */
	public static Account verifyAccount(String username, String password) {
		DatabaseManager databaseManager = new DatabaseManager();
		return (databaseManager.verifyAccount(username, password))
				? databaseManager.getAccounts().stream().filter(o -> o.username.equals(username)).findFirst().get()
				: null;
	}

	public String getUsername() {
		return username;
	}
}