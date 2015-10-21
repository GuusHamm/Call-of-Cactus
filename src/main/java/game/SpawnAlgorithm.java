package game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Teun on 20-10-2015.
 */
public class SpawnAlgorithm
{
    private static final int SPAWNRADIUS = 5;
    private static final int MAXTRIES = 1000;
    private Game game;

    private int screenWidth, screenHeight;

    public SpawnAlgorithm(Game game) {
        this.game = game;
        screenWidth = 800;
        screenHeight = 480;
    }

    public SpawnAlgorithm(Game game, Vector2 screenSize) {
        this.game = game;
        this.screenWidth = (int) screenSize.x;
        this.screenHeight = (int) screenSize.y;
    }

    public Vector2 findSpawnPosition() throws NoValidSpawnException
    {
        ArrayList<Rectangle> forbiddenRegions = new ArrayList<>();
        // TODO Need to able to get size of hitboxes of entities
        for (Entity e : game.getAllEntities()) {
            forbiddenRegions.add(generateSpawnRadius(e.getLocation(), SPAWNRADIUS));
        }

        Vector2 validSpawnPositon = null;
        Random random = new Random();
        int tries = 0;
        while (validSpawnPositon == null && tries < MAXTRIES) {
            tries++;
            float randomX = (float) (random.nextDouble() * screenWidth) + 1;
            float randomY = (float) (random.nextDouble() * screenHeight) + 1;

            Vector2 newPosition = new Vector2(randomX, randomY);
            for (Rectangle rectangle : forbiddenRegions) {
                if (rectangle.contains(newPosition)) {
                    continue;
                }
            }
            validSpawnPositon = newPosition;
        }

        if (validSpawnPositon == null) {
            throw new NoValidSpawnException();
        }
        return validSpawnPositon;
    }

    private Rectangle generateSpawnRadius(Vector2 pos, int radius) {
        Vector2 spawnRadiusStartPos = new Vector2(pos.x - SPAWNRADIUS, pos.y - SPAWNRADIUS);
        Vector2 spawnRadiusEndPos = new Vector2(pos.x + SPAWNRADIUS, pos.y + SPAWNRADIUS);

        return new Rectangle(spawnRadiusStartPos.x, spawnRadiusStartPos.y, spawnRadiusEndPos.x, spawnRadiusEndPos.y);
    }
}
