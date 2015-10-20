package game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Teun on 20-10-2015.
 */
public class SpawnAlgorithm
{
    private static final int SPAWNRADIUS = 5;
    private Game game;

    public SpawnAlgorithm(Game game) {
        this.game = game;
    }

    public Vector2 findIdealSpawnPosition(Entity entity)
    {
        List<Vector2> spawnPositions = new ArrayList<>();
        // TODO Need to able to get size of hitboxes of entities

        int randomPosition = new Random().nextInt(spawnPositions.size());
        return spawnPositions.get(randomPosition);
    }
}
