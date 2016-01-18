package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.HumanCharacter;
import callofcactus.role.Role;
import callofcactus.role.Soldier;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xubuntu on 12-10-15.
 */
public class HumanCharacterTest extends BaseTest {
	private HumanCharacter humanCharacter;
	private Account account;


	@Before
	public void setUp() throws Exception {
		IGame game = new SinglePlayerGame();

		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();

		account = new Account("Test");
		humanCharacter = new HumanCharacter(game, location, name, role, GameTexture.texturesEnum.damagePickupTexture, 64, 64, false, account);


	}

	@Test
	public void testGetScore() throws Exception {
		assertEquals(0, humanCharacter.getScore());

	}

	@Test
	public void testTakeDamage() throws Exception {
		int startHealth = humanCharacter.getHealth();

		humanCharacter.takeDamage(startHealth - 1);

		assertEquals(startHealth - (startHealth - 1), humanCharacter.getHealth());
		humanCharacter.takeDamage(startHealth - 1);
	}

	@Test
	public void testAddKill()
	{
		int startKillCount = humanCharacter.getKillCount();
		int startScore = humanCharacter.getScore();

		humanCharacter.addKill(false);

		assertNotSame(startKillCount, humanCharacter.getKillCount());
		assertNotSame(startScore, humanCharacter.getScore());
	}

	@Test
	public void testAddDeath()
	{
		int startDeathCount = humanCharacter.getDeathCount();

		humanCharacter.addDeath(false);

		assertNotSame(startDeathCount, humanCharacter.getDeathCount());
	}

	@Test
	public void testSetKill()
	{
		humanCharacter.setKillCount(10);

		assertNotSame(10, humanCharacter.getKillCount());
	}

	@Test
	public void testSetDeath()
	{
		humanCharacter.setDeathCount(10);

		assertNotSame(10, humanCharacter.getDeathCount());
	}

	@Test
	public void testSetScore()
	{
		humanCharacter.setScore(10);

		assertNotSame(10, humanCharacter.getScore());
	}


	@Test
	public void testMove() throws Exception {
		Vector2 location = new Vector2(1, 1);

		humanCharacter.move(location, false);

		assertEquals(location, humanCharacter.getLocation());

		location = new Vector2(-1, 1);
		Vector2 properLocation = new Vector2(0, 1);

		humanCharacter.move(location, false);

		assertEquals(properLocation, humanCharacter.getLocation());

		location = new Vector2(1, -1);
		properLocation = new Vector2(1, 0);

		humanCharacter.move(location, false);

		assertEquals(properLocation, humanCharacter.getLocation());

		location = new Vector2(1, -1);
		properLocation = new Vector2(1, 0);

		humanCharacter.move(location, false);

		assertEquals(properLocation, humanCharacter.getLocation());


		location = new Vector2(801, 1);
		properLocation = new Vector2(800, 1);

		humanCharacter.setLocation(properLocation,false);

		humanCharacter.move(location, false);

		assertEquals(location, humanCharacter.getLocation());

		location = new Vector2(1, 481);
		properLocation = new Vector2(1, 480);

		humanCharacter.setLocation(properLocation,false);

		humanCharacter.move(location, false);

		assertEquals(location, humanCharacter.getLocation());


	}


}