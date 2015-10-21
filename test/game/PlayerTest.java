package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Role;
import game.role.Sniper;
import game.role.Soldier;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by xubuntu on 12-10-15.
 */
public class PlayerTest
{
private HumanCharacter humanCharacter;
	private Role role;

	@Before
	public void setUp() throws Exception {


		Game game = new GameMockup();

		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		role = new Soldier();
		Texture playerTexture = null;

		humanCharacter = new HumanCharacter(game, location, name,role,playerTexture);
	}

    @Test
    public void testGetRole() throws Exception {
       assertEquals(role,humanCharacter.getRole());
    }

    @Test
    public void testTakeDamage() throws Exception {
		//Get the starting health
        int startHealth = humanCharacter.getHealth();

		//Make the player take damage
		humanCharacter.takeDamage(5);

		assertEquals("The player is not taking damage properly",startHealth-5,humanCharacter.getHealth());

    }

    @Test
    public void testFireBullet() throws Exception   {
        //Todo Implent Test

    }

    @Test
    public void testChangeRole() throws Exception    {
        Role startingRole = humanCharacter.getRole();

		int startingHealth = humanCharacter.getHealth();
		int startingDamage = humanCharacter.getDamage();
		int startingSpeed = humanCharacter.getSpeed();
		int startingFireRate = humanCharacter.getFireRate();


		Role newRole = new Sniper();

		humanCharacter.changeRole(newRole);

		assertNotEquals("the role has not changed",startingRole,humanCharacter.getRole());
		assertEquals("the new role is not the one that was set", newRole, humanCharacter.getRole());

		assertNotEquals("the health has not changed after switching role",startingHealth,humanCharacter.getHealth());
		assertNotEquals("the damage has not changed after switching role",startingDamage,humanCharacter.getDamage());
		assertNotEquals("the speed has not changed after switching role",startingSpeed,humanCharacter.getSpeed());
		assertNotEquals("the fire-rate has not changed after switching role",startingFireRate,humanCharacter.getFireRate());
    }

	@Test
    public void testGetDirection() throws Exception {
        // The default direction when an object is initialized will be 0
		int direction = humanCharacter.getDirection();
		assertEquals("The initial direction was not set properly",0,direction);




	}
}