package account;

import game.Game;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by xubuntu on 12-10-15.
 */
public class AccountTest {

	Account account;
    Game game;

	@Before
	public void setUp() throws Exception {
		//Todo Implent Test

        // Account(String username, String firstName, String lastName)
        account = new Account("Testuser");
        game = new GameMockup();

	}

	@Test (expected = UnsupportedOperationException.class)
	public void testJoinGame() throws Exception {
		//Todo Implent Test
        account.joinGame(game);
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testExitGame() throws Exception {
		//Todo Implent Test
        account.exitGame();

	}

	@Test (expected = UnsupportedOperationException.class)
	public void testVerifyAccount() throws Exception {
		//Todo Implent Test
        account.verifyAccount("EncryptedPasswod");
	}

	@Test
	public void testCreateSalt() {
		byte bytes[] = account.createSalt();
		System.out.println(bytes + "; Length: " + bytes.length);
		org.junit.Assert.assertTrue("The salt isn't the correct length", (bytes.length == 20));
	}
}