package account;

import game.Game;

public class Account {
    private int ID;
	private Game currentGame;
	private String username;
	private String firstName;
	private String lastName;
	private String password;

	/**
	 * creates a new account with the following parameters:
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param password
	 */
    public Account(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

	/**
	 * This function is used to add a account to a game
	 * @param game : The game which the account tries to join
	 * @return true when success, false when failed
	 */
	public boolean joinGame(Game game) {
		// TODO - implement Account.joinGame
		// TODO - check if game is active
		throw new UnsupportedOperationException();
	}

	/**
	 * Exits the player from his current game
	 */
	public void exitGame() {
		// TODO - implement Account.exitGame
        // TODO - check if game is running
		throw new UnsupportedOperationException();
	}

    /**
	 * This function is called to verify an account.
	 * It is used as a login function.
	 * @param username
	 * @param password
	 * @return the account which matches the given username and password or null if none match
	 */
	public Account verifyAccount(String username, String password) {
		// TODO - implement Account.verifyAccount
		throw new UnsupportedOperationException();
	}

}