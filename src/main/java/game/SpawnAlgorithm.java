package game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.io.PropertyReader;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Teun
 */
public class SpawnAlgorithm
{
    private static final int DEFAULTSPAWNRADIUS = 350;
    private static final int MAXTRIES = 1000;
    private int SPAWNRADIUS = DEFAULTSPAWNRADIUS;
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
        try {
            SPAWNRADIUS = new PropertyReader().getJsonObject().getInt(PropertyReader.SPAWN_RADIUS);
        } catch (IOException e) {
            e.printStackTrace();
            SPAWNRADIUS = DEFAULTSPAWNRADIUS;
        }
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
        try {
            SPAWNRADIUS = new PropertyReader().getJsonObject().getInt("baseSpawnRadius");
        } catch (IOException e) {
            e.printStackTrace();
            SPAWNRADIUS = DEFAULTSPAWNRADIUS;
        }
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

            if (impossibleLocations.isEmpty())
                canSpawn = true;

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
     * @param rect Rectangle to enlarge
     * @param radius Size of the rectangle
     * @return Rectangle with the middlepoint pos and radius radius
     */
    private Rectangle generateSpawnRadius(Rectangle rect, int radius) {
        float newWidth = rect.getWidth() + (radius * 2);
        float newHeight = rect.getHeight() + (radius * 2);
        return new Rectangle(rect.x, rect.y, newWidth, newHeight);
    }


    /**
     * Updates the locations entities shouldn't be spawning from the list of entities in the game
     * New List is automatically set
     * @return The updated locations
     */
    public ArrayList<Rectangle> getImpossibleLocations() {
        impossibleLocations.clear();
        for (Entity e : game.getAllEntities()) {
            Rectangle r = generateSpawnRadius(e.getHitBox(), SPAWNRADIUS);
            impossibleLocations.add(r);
        }
        return impossibleLocations;
    }
}