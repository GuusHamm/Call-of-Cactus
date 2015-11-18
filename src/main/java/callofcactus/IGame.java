package callofcactus;

import callofcactus.entities.Entity;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.MovingEntity;
import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.math.Vector2;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wouter Vanmulken on 16-11-2015.
 */
public interface IGame {

	boolean getMuted();

	void setMuted(boolean muted);

	GameTexture getTextures();

	void setMousePositions(int x, int y);

	JSONObject getJSON();

	ArrayList<NotMovingEntity> getNotMovingEntities();

	ArrayList<HumanCharacter> getPlayers();

	HumanCharacter getPlayer();

	ArrayList<MovingEntity> getMovingEntities();

	Vector2 getMouse();

	List<Entity> getAllEntities();

	boolean getGodMode();

	void setGodMode(boolean godMode);

	int getWaveNumber();

	Vector2 generateSpawn() throws NoValidSpawnException;

	/**
	 * Calculates the angle between two vectors
	 *
	 * @param beginVector : The vector that will be used as center
	 * @param endVector   : Where the object has to point to
	 * @return Returns the angle, this will be between 0 and 360 degrees
	 */
	int angle(Vector2 beginVector, Vector2 endVector);

	/**
	 * Calculates the new position between the currentPosition to the Endposition.
	 *
	 * @param currentPosition : The current position of the object
	 * @param EndPosition     : The position of the end point
	 * @param speed           : The speed that the object can move with
	 * @return the new position that has been calculated
	 */
	Vector2 calculateNewPosition(Vector2 currentPosition, Vector2 EndPosition, double speed);


	/**
	 * Calculates the new position from a beginposition and a angle..
	 *
	 * @param currentPosition : The current position of the object
	 * @param speed           : The speed that the object can move with
	 * @param angle           : The angle of where the object should be heading
	 * @return the new position that has been calculated
	 */

	Vector2 calculateNewPosition(Vector2 currentPosition, double speed, double angle);


	/**
	 * Called when an entity needs to be added to the callofcactus (Only in the memory, but it is not actually drawn)
	 *
	 * @param entity : Entity that should be added to the callofcactus
	 */
	void addEntityToGame(Entity entity);

	void removeEntityFromGame(Entity entity);


	void createPickup();

	long secondsToMillis(int seconds);

	/**
	 * This method checks every entity in callofcactus if two hitboxes overlap, if they do the appropriate action will be taken.
	 * This method has reached far beyond what should be asked of a single method but it works.
	 * Follow the comments on its threaturous path and you will succes in finding what you seek.
	 * This should also be ported to callofcactus in the next itteration.
	 */
	void compareHit();

	void playRandomHitSound();

	void playRandomBulletSound();
}
