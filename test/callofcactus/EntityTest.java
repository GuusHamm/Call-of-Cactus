package callofcactus;

import callofcactus.entities.HumanCharacter;
import callofcactus.entities.NotMovingEntity;
import callofcactus.role.Role;
import callofcactus.role.Soldier;
import com.badlogic.gdx.math.Vector2;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Wouter Vanmulken on 8-10-2015.
 */
public class EntityTest extends BaseTest {
	private IGame game;
	private HumanCharacter humanCharacter;
	private NotMovingEntity notMovingEntity;
	private Vector2 location;

	@BeforeClass
	public void setUp() throws Exception {
		this.game = new SinglePlayerGame();

		this.location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();

		humanCharacter = new HumanCharacter(game, location, name, role, GameTexture.texturesEnum.playerTexture, 64, 64, true);
		notMovingEntity = new NotMovingEntity(game, new Vector2(2, 2), true, 10, false, GameTexture.texturesEnum.wallTexture, 64, 64, true);
	}

	@Test
	public void testGetLocation() throws Exception {
		assertEquals(this.location, humanCharacter.getLocation());

	}

	@Test
	public void testGetSpriteWidth() throws Exception {
		assertEquals(64, humanCharacter.getSpriteWidth());

	}

	@Test
	public void testGetSpriteHeight() throws Exception {
		assertEquals(64, humanCharacter.getSpriteHeight());

	}

	@Test
	public void testDestroy() throws Exception {
		assertTrue(game.getMovingEntities().contains(humanCharacter));

		assertTrue(humanCharacter.destroy());

		assertFalse(game.getMovingEntities().contains(humanCharacter));

	}

	@Test
	public void testSetHealth()
	{
		notMovingEntity.setHealth(10, false);
		assertEquals(notMovingEntity.getHealth(), 10);
	}

	@Test
	public void testSetLastLocation()
	{
		Vector2 vector2 = new Vector2(1, 1);
		notMovingEntity.setLastLocation(vector2);
		notMovingEntity.setToLastLocation(vector2, false);
		assertEquals(notMovingEntity.getLastLocation(), vector2);
	}

	@Test
	public void testSetFromServer()
	{
		notMovingEntity.setFromServer(false);

		assertFalse(notMovingEntity.getFromServer());
	}

	@Test
	public void testGetDamage()
	{
		notMovingEntity.getDamage();
	}

	@Test
	public void testSetClientS()
	{
		humanCharacter.setClientS();
	}
	@Test
	public void testSetID()
	{
		int id = 13;
		humanCharacter.setID(id, false);

		assertEquals(humanCharacter.getID(), id);
	}

	@Test
	public void testChangeCommand()
	{
		humanCharacter.setID(13, true);
	}


	@Test
	public void testTameDamage() throws Exception
	{
		int startHealth = humanCharacter.getHealth();
		humanCharacter.takeDamage(1, false);
		assertEquals(startHealth - 1, humanCharacter.getHealth());
	}

	@Test
	public void testGetHitBox()
	{
		notMovingEntity.getHitBox();
	}

	@Test
	public void testSetSpriteDimensions()
	{
		int startingHeight = notMovingEntity.getSpriteHeight();
		int startingWidth = notMovingEntity.getSpriteWidth();

		notMovingEntity.setSpriteHeight(21);
		notMovingEntity.setSpriteWidth(21);

		assertNotSame(startingHeight, notMovingEntity.getSpriteHeight());
		assertNotSame(startingWidth, notMovingEntity.getSpriteWidth());
	}

	@Test
	public void testSetGame()
	{
		IGame iGame = notMovingEntity.getGame();

		notMovingEntity.setGame(new SinglePlayerGame());

		assertNotSame(iGame, notMovingEntity.getGame());
	}
	@Test
	public void testsetlocation() throws Exception {
		Vector2 lastLocation = humanCharacter.getLocation();
		Vector2 newLocation = new Vector2(lastLocation.x + 1, lastLocation.y + 1);

		humanCharacter.setLocation(newLocation,false);

		assertEquals(newLocation, humanCharacter.getLocation());
	}


	@Test
	public void testLastlocation() throws Exception {
		Vector2 lastLocation = humanCharacter.getLocation();
		Vector2 newLocation = new Vector2(lastLocation.x + 1, lastLocation.y + 1);

		humanCharacter.setLastLocation(newLocation);

		assertEquals(newLocation, humanCharacter.getLastLocation());
	}

}