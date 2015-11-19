package callofcactus;

import callofcactus.entities.Entity;
import callofcactus.entities.HumanCharacter;
import callofcactus.io.PropertyReader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Teun
 */
public class SpawnAlgorithm {
	private final int DEFAULTSPAWNRADIUS = 350;
	private final int MAXTRIES = 1000;
	private int SPAWNRADIUS = DEFAULTSPAWNRADIUS;
	private Game game;

	private ArrayList<Rectangle> impossibleLocations;

	private int screenWidth, screenHeight;

	/**
	 * The default screensize for the algorithm is 800x480, can be changed with extra parameter
	 *
	 * @param game The callofcactus which contains the entities the algorithm should be worried about
	 */
	public SpawnAlgorithm(Game game) {
		this.game = game;
		try {
			screenWidth = Gdx.graphics.getWidth();
			screenHeight = Gdx.graphics.getHeight();
		} catch (Exception e) {
			screenWidth = 800;
			screenHeight = 480;
		}
		impossibleLocations = new ArrayList<>();
		try {
			SPAWNRADIUS = new PropertyReader().getJsonObject().getInt(PropertyReader.SPAWN_RADIUS);
		} catch (IOException e) {
			e.printStackTrace();
			SPAWNRADIUS = DEFAULTSPAWNRADIUS;
		}
	}

	/**
	 * @param game       The callofcactus which contains the entities the algorithm should be worried about
	 * @param screenSize The size of the area the algorithm can return
	 */
	public SpawnAlgorithm(Game game, Vector2 screenSize) {
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
	 * It won't spawn near ANY entities supplied by the callofcactus object in the constructor
	 *
	 * @return Position found for entity
	 * @throws NoValidSpawnException This exception is thrown when no valid spawn has been found
	 */
	public Vector2 findSpawnPosition() throws NoValidSpawnException {
		// Update the locations in which the algorithm shouldn't spawn entities
		getImpossibleLocations();

		boolean canSpawn = false;
		int tries = 0;
		Vector2 spawnPosition = null;
		while (!canSpawn) {
			// If a certain amount of tries is reached, it will throw an exception for the callofcactus to handle
			if (tries > MAXTRIES) {
				throw new NoValidSpawnException();
			}

			tries++;
			spawnPosition = generateRandomPosition();

			if (impossibleLocations.isEmpty())
				canSpawn = true;

			if (isInInvalidLocation(spawnPosition)) {
				canSpawn = false;
				tries++;
			} else {
				canSpawn = true;
			}

		}

		return spawnPosition;
	}

	/**
	 * Generates a random position within the bounds specified in this class
	 *
	 * @return The position generated
	 */
	private Vector2 generateRandomPosition() {
		Random random = new Random();
		float x = random.nextFloat() * screenWidth;
		float y = random.nextFloat() * screenHeight;
		return new Vector2(x, y);
	}

	public boolean isInInvalidLocation(Vector2 location) {
		for (Rectangle impossibleLocation : impossibleLocations) {
			// Check if the generated location is inside any of the entity radii
			if (impossibleLocation.contains(location)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Generates a Rectangle with a specified radius around the position given
	 *
	 * @param rect   Rectangle to enlarge
	 * @param radius Size of the rectangle
	 * @return Rectangle with the middlepoint pos and radius radius
	 */
	private Rectangle generateSpawnRadius(Rectangle rect, int radius) {
		float newWidth = rect.getWidth() + (radius * 2);
		float newHeight = rect.getHeight() + (radius * 2);
		return new Rectangle(rect.x - radius, rect.y - radius, newWidth, newHeight);
	}


	/**
	 * Updates the locations entities shouldn't be spawning from the list of entities in the callofcactus
	 * New List is automatically set
	 *
	 * @return The updated locations
	 */
	public ArrayList<Rectangle> getImpossibleLocations() {
		impossibleLocations.clear();
		Rectangle r;
		for (Entity e : game.getAllEntities()) {
			if (e instanceof HumanCharacter) {
				r = generateSpawnRadius(e.getHitBox(), SPAWNRADIUS + 50);
			} else {
				r = generateSpawnRadius(e.getHitBox(), SPAWNRADIUS);
			}
			impossibleLocations.add(r);
		}
		return impossibleLocations;
	}
}