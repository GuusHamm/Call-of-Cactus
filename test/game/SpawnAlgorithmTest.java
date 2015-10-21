package game;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by Teun on 21-10-2015.
 */
public class SpawnAlgorithmTest
{
    private SpawnAlgorithm spawnAlgorithm;
    private Game game;

    @Before
    public void setUp() throws Exception
    {
        game = new GameMockup();
    }

    @Test
    public void testFindSpawnPosition() throws Exception
    {
        spawnAlgorithm = new SpawnAlgorithm(game, new Vector2(100f, 100f));
        Vector2[] spawnPositions = new Vector2[10];
        for (int i = 0; i < 10; i++) {
            try
            {
                spawnPositions[i] = spawnAlgorithm.findSpawnPosition();
            }
            catch (NoValidSpawnException e)
            {
                e.printStackTrace();
            }
        }
        
    }
}