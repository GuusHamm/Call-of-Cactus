package game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Teun
 */
public class SpawnAlgorithm
{
    private static final int SPAWNRADIUS = 5;
    private static final int MAXTRIES = 1000;
    private Game game;

    private ArrayList<Rectangle> impossibleLocations;

    private int screenWidth, screenHeight;

    /**
     * The default screensize for the algorithm is 800x480, can be changed with extra parameter
     * @param game The game which contains the entities the algorithm should be worried about
     */
    public SpawnAlgorithm(Game game) {
        this.game = game;
        screenWidth = 800;
        screenHeight = 480;
        impossibleLocations = new ArrayList<>();
    }

    /**
     * @param game The game which contains the entities the algorithm should be worried about
     * @param screenSize The size of the area the algorithm can return
     */
    public SpawnAlgorithm(Game game, Vector2 screenSize)
    {
        this.game = game;
        this.screenWidth = (int) screenSize.x;
        this.screenHeight = (int) screenSize.y;
        impossibleLocations = new ArrayList<>();
    }

    /**
     * Finds a valid spawn position for the entity, and returns the position as a Vector2
     * It won't spawn near ANY entities supplied by the game object in the constructor
     * @return Position found for entity
     * @throws NoValidSpawnException This exception is thrown when no valid spawn has been found
     */
    public Vector2 findSpawnPosition() throws NoValidSpawnException    {
        // Update the locations in which the algorithm shouldn't spawn entities
        getImpossibleLocations();

        boolean canSpawn = false;
        int tries = 0;
        Vector2 spawnPosition = null;
        while (!canSpawn)        {
            // If a certain amount of tries is reached, it will throw an exception for the game to handle
            if (tries > MAXTRIES) {
                throw new NoValidSpawnException();
            }

            tries++;
            spawnPosition = generateRandomPosition();

            for (Rectangle impossibleLocation : impossibleLocations)            {
                // Check if the generated location is inside any of the entity radii
                if (impossibleLocation.contains(spawnPosition))                {
                    canSpawn = false;
                    break;
                }else{
                    canSpawn = true;
                }
            }

        }

        return spawnPosition;
    }

    /**
     * Generates a random position within the bounds specified in this class
     * @return The position generated
     */
    private Vector2 generateRandomPosition() {
        Random random = new Random();
        float x = random.nextFloat() * screenWidth;
        float y = random.nextFloat() * screenHeight;
        return new Vector2(x,y);
    }

    /**
     * Generates a Rectangle with a specified radius around the position given
     * @param pos Position to generate the rectangle around
     * @param radius Size of the rectangle
     * @return Rectangle with the middlepoint pos and radius radius
     */
    private Rectangle generateSpawnRadius(Vector2 pos, int radius) {
        Vector2 spawnRadiusStartPos = new Vector2(pos.x - radius, pos.y - radius);
        Vector2 spawnRadiusEndPos = new Vector2(pos.x + radius, pos.y + radius);

        return new Rectangle(spawnRadiusStartPos.x, spawnRadiusStartPos.y, spawnRadiusEndPos.x, spawnRadiusEndPos.y);
    }


    /**
     * Updates the locations entities shouldn't be spawning from the list of entities in the game
     * New List is automatically set
     * @return The updated locations
     */
    public ArrayList<Rectangle> getImpossibleLocations() {
        impossibleLocations.clear();
        for (Entity e : game.getAllEntities()) {
            Rectangle r = generateSpawnRadius(e.getLocation(), SPAWNRADIUS);
            impossibleLocations.add(r);
        }
        return impossibleLocations;
    }
}