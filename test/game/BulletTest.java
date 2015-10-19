package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Boss;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;


/**
 * Created by xubuntu on 12-10-15.
 */
public class BulletTest extends TestCase
{
    Bullet bullet;
    HumanCharacter human;
    Game game;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        game = new GameMockup();
        Vector2 location = new Vector2(1, 1);
        String name = "testplayer";
        Boss    rol = new Boss();
        Texture bulletTexture = null;
        Texture playerTexture = null;

        human = new HumanCharacter(game, location, name, rol,playerTexture);

        bullet = new Bullet(human.getGame(), new Vector2(1,1), human,bulletTexture);

        bullet.setBaseSpeed(1);
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    @Test
    public void testGetVelocity() throws Exception
    {
        //De standaard snelheid van een kogel is 20, de speedMultiplier van boss is 0.5, dus 20 * 0.5 = 5
        bullet.setBaseSpeed(20);
        assertEquals("This error indicates that the expected Velocity doesn't match the actual one", 40, bullet.getVelocity());
    }

    @Test
    public void testGetDamage() throws Exception
    {
        bullet.setDamage(5);
        assertEquals("This error will show when the damage you expected was different than the actual value", bullet.getDamage(), 5);
    }

    @Test
    public void testGetShooter() throws Exception
    {
        assertEquals("The shooters aren't the same", bullet.getShooter(), human);
    }
}