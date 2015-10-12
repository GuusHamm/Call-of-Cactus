package game;

import game.role.Boss;
import javafx.geometry.Point2D;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubuntu on 12-10-15.
 */
public class BulletTest extends TestCase
{
    Bullet bullet;
    HumanCharacter human;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Game game = new Game(1, 1, false, 100);
        Point2D location = new Point2D(1, 1);
        String name = "testplayer";
        Boss    rol = new Boss();

        human = new HumanCharacter(game, location, name, rol);

        bullet = new Bullet(human.getGame(), new Point2D(1,1), human);

        bullet.setBaseSpeed(1);
    }

    @Test
    public void testGetVelocity() throws Exception
    {
        //De standaard snelheid van een kogel is 20, de speedMultiplier van boss is 0.5, dus 20 * 0.5 = 5
        bullet.setBaseSpeed(20);
        assertEquals("This error indicates that the expected Velocity doesn't match the actual one", bullet.getVelocity(), 10);
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