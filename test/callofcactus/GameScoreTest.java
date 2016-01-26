package callofcactus;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Kees on 20/01/2016.
 */
public class GameScoreTest extends BaseTest{

    GameScore gameScore;

    @BeforeClass
    public void setUp() throws Exception {
        gameScore = new GameScore("HansKlok", 4, 7, 11);
    }

    @Test
    public void testGetters_Setters() {

        Assert.assertEquals("Username incorrect", gameScore.getUsername(), "HansKlok");
        Assert.assertEquals("Kills incorrect", gameScore.getKills(), "4");
        Assert.assertEquals("Deaths incorrect", gameScore.getDeaths(), "7");
        Assert.assertEquals("Score incorrect", gameScore.getScore(), "11");
    }

}
