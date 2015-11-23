package callofcactus.account;

import callofcactus.BaseTest;
import callofcactus.Game;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by xubuntu on 12-10-15.
 */
public class AccountTest extends BaseTest {

	private Account account;
	private Game game;

	@Before
	public void setUp() throws Exception {
		//Todo Implent Test

		account = new Account("Testuser");
		game = new GameMockup();

	}

	@Test(expected = UnsupportedOperationException.class)
	public void testJoinGame() throws Exception {
		//Todo Implent Test
		account.joinGame(game);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testExitGame() throws Exception {
		//Todo Implent Test
		account.exitGame();

	}

	@Test(expected = UnsupportedOperationException.class)
	public void testVerifyAccount() throws Exception {
		//Todo Implent Test
		account.verifyAccount("Testuser", "EncryptedPasswod");
	}
}