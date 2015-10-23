package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Boss;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;


/**
 * Created by xubuntu on 12-10-15.
 */
public class BulletTest
{
    Bullet bullet;
    HumanCharacter human;
    Game game;

    @Before
    public void setUp() throws Exception {

        game = new GameMockup();
        Vector2 location = new Vector2(1, 1);
        String name = "testplayer";
        Boss    rol = new Boss();
        Texture bulletTexture = null;
        Texture playerTexture = null;

        human = new HumanCharacter(game, location, name, rol,playerTexture,64,64);

        bullet = new Bullet(human.getGame(), new Vector2(1,1), human,bulletTexture,100,10,10);

        bullet.setSpeed(1);
    }

    @Test
    public void testGetVelocity() throws Exception
    {
        //The standard speed of a bullet is 20, the speedMultiplier of boss is 0.5, so 20 * 0.5 = 5
        bullet.setSpeed(20);
		org.junit.Assert.assertEquals("This error indicates that the expected Velocity doesn't match the actual one", 40, bullet.getSpeed());
        org.junit.Assert.assertEquals("This error will show when the damage you expected was different than the actual value", bullet.getDamage(), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDamageBadValue() throws IllegalArgumentException {
        bullet.setDamage(-5);
    }

    @Test
    public void testGetShooter() throws Exception    {
		org.junit.Assert.assertEquals("The shooters aren't the same", bullet.getShooter(), human);
    }
}