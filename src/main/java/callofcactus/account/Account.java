package callofcactus.account;

import callofcactus.Game;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Account {
	private int ID;

	private Game currentGame;
	private String username;
	private String salt;
	private String hash;
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
		//throw new UnsupportedOperationException();
		try {
			currentGame = game;
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Exits the player from his current callofcactus
	 */
	public void exitGame() {
		// TODO - implement Account.exitGame
		// TODO - check if callofcactus is running
		if (currentGame != null) {
			currentGame = null;
		}
	}

	/**
	 * This function is called to verify an callofcactus.account.
	 * It is used as a login function.
	 *
	 * @param username : The name that will be displayed, this will be used to log in. a username must be unique
	 * @param password : The password that the user chose to login.
	 * @return the callofcactus.account which matches the given username and password or null if none match
	 */
	public Account verifyAccount(String username, String password) {
		//TODO actually get items from database
		if (salt.matches("") || hash.matches("")) {
			return null;
		}
		//Try catch for NoSuchAlgorithmException and UnsupportedEncodingException.
		try {
			if (this.username.matches(username) && this.hash.matches(makeHash(password))) {
				return this;
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			//To make sure we will always return something
			return null;
		}
	}

	public String makeHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if (salt.matches("")) {
			salt = makeSalt();
		}
		String hashedpassword = "";
		//TODO hash the actual password
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.reset();
		byte[] buffer = password.getBytes("UTF-8");
		md.update(buffer);
		byte[] digest = md.digest();

		String hexStr = "";
		for (int i = 0; i < digest.length; i++) {
			hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring(1);
		}
		//Bron: http://stackoverflow.com/questions/6120657/how-to-generate-a-unique-hash-code-for-string-input-in-android


		return hashedpassword;
	}

	public String makeSalt() {
		String newSalt = "";
		final Random r = new SecureRandom();
		byte[] saltbytes = new byte[32];
		r.nextBytes(saltbytes);
		newSalt = Base64.encode(saltbytes);

		return newSalt;
	}


}