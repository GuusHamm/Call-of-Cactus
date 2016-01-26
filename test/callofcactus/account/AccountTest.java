package callofcactus.account;

import callofcactus.BaseTest;
import callofcactus.IGame;
import callofcactus.SinglePlayerGame;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xubuntu on 12-10-15.
 */
public class AccountTest extends BaseTest {

	private Account account;
	private IGame game;

	@Before
	public void setUp() throws Exception {
		account = new Account("Testuser");
		game = new SinglePlayerGame();

	}

	@Test
	public void testVerifyAccount() throws Exception {
		account.verifyAccount("Testuser", "EncryptedPasswod");
	}
}