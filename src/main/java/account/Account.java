package account;

import game.Game;

public class Account {
    private int ID;
	private Game currentGame;
	private String username;
	private String firstname;
	private String lastname;
	private String password;

	/**
	 * creates a new account with the following parameters:
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param password
	 */
    public Account(String username, String firstname, String lastname, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

	/**
	 * This funcion is used to add a account to a game
	 * @param game : The game which the account tries to join
	 * @return true when succes, false when failed
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
	 * This funcion is called to verify an account.
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