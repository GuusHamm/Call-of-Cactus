package account;

import game.Game;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Account {
	private int ID;
	private Game currentGame;
	private String username;
    private byte[] salt = null;
	private String hash = null;

	/**
	 * creates a new account with the following parameters:
	 *
	 * @param username  : The name that will be displayed, this will be used to log in. must be unique
	 */
	public Account(String username) {
		this.username = username;
	}

	/**
	 * This function is used to add a account to a game
	 *
	 * @param game : The game which the account tries to join
	 * @return true when success, false when failed
	 */
	public boolean joinGame(Game game) {
		// TODO - implement Account.joinGame
		// TODO - check if game is active
        currentGame = game;
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
	 *
	 * @param password : The password that the user chose to login.
	 * @return the account which matches the given username and password or null if none match
	 */
	public Account verifyAccount(String password) {
		// TODO - implement Account.verifyAccount
        if (salt == null && hash == null) {
            //Account account = Database.getAccount(username);
        }

        if (createHash(password) == hash) {
            return this;
        }
        else {
            return null;
        }
	}

	public byte[] createSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
	}

    public String createHash(String password) {
        String toHash = salt+password;
        MessageDigest messageDigest = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        messageDigest.update(toHash.getBytes());
        String encryptedString = new String(messageDigest.digest());
        return encryptedString;
    }
}