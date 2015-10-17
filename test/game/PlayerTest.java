package game;

import com.badlogic.gdx.graphics.Texture;
import game.role.Boss;
import game.role.Role;
import game.role.Sniper;
import game.role.Soldier;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xubuntu on 12-10-15.
 */
public class PlayerTest
{
private HumanCharacter humanCharacter;
	private Role role;

	@Before
	public void setUp() throws Exception {
		Game game = new Game(1, 1, false, 100);
		Point2D location = new Point2D(1, 1);
		String name = "testplayer";
		role = new Soldier();
		Texture playerTexture = new Texture("player.png");

		humanCharacter = new HumanCharacter(game, location, name,role,playerTexture);
	}

    @Test
    public void testGetRole() throws Exception
    {
       assertEquals(role,humanCharacter.getRole());

    }

    @Test
    public void testTakeDamage() throws Exception
    {
		//Get the starting health
        int startHealth = humanCharacter.getHealth();

		//Make the player take damage
		humanCharacter.takeDamage(5);

		assertEquals("The player is not taking damage properly",startHealth-5,humanCharacter.getHealth());

    }

    @Test
    public void testFireBullet() throws Exception
    {
        //Todo Implent Test

    }

    @Test
    public void testChangeRole() throws Exception
    {
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
		assertNotEquals("the firerate has not changed after switching role",startingFireRate,humanCharacter.getFireRate());
    }

	@Test
    public void testGetDirection() throws Exception
    {
        //Todo Implent Test

    }
}