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
        account = new Account("Testuser", "Test", "User");
        game = new GameMockup();

	}

	@Test
	public void testJoinGame() throws Exception {
		//Todo Implent Test
        try {
            account.joinGame(game);
        }
        catch (UnsupportedOperationException use) {
            //Not yet implemented
        }
	}

	@Test
	public void testExitGame() throws Exception {
		//Todo Implent Test
        try {
            account.exitGame();
        }
        catch (UnsupportedOperationException use) {
            //Not yet implemented
        }

	}

	@Test
	public void testVerifyAccount() throws Exception {
		//Todo Implent Test
        try {
            account.verifyAccount("Testuser", "EncryptedPasswod");
        }
        catch (UnsupportedOperationException use) {
            //Not yet implemented
        }
	}
}