package game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.role.Sniper;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * @author Teun
 */
public class SpawnAlgorithmTest {
	private SpawnAlgorithm spawnAlgorithm;
	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new GameMockup();
		spawnAlgorithm = new SpawnAlgorithm(game, new Vector2(1920f, 1080f));

		AICharacter aiCharacter = new AICharacter(game, new Vector2(5f, 5f), "AI1", new Sniper(), game.getPlayer(), null, 5, 5);
		AICharacter aiCharacter1 = new AICharacter(game, new Vector2(15f, 5f), "AI2", new Sniper(), game.getPlayer(), null, 5, 5);
		AICharacter aiCharacter2 = new AICharacter(game, new Vector2(25f, 5f), "AI3", new Sniper(), game.getPlayer(), null, 5, 5);
		AICharacter aiCharacter3 = new AICharacter(game, new Vector2(35f, 5f), "AI4", new Sniper(), game.getPlayer(), null, 5, 5);
		AICharacter aiCharacter4 = new AICharacter(game, new Vector2(45f, 5f), "AI5", new Sniper(), game.getPlayer(), null, 5, 5);

		game.addEntityToGame(aiCharacter);
		game.addEntityToGame(aiCharacter1);
		game.addEntityToGame(aiCharacter2);
		game.addEntityToGame(aiCharacter3);
		game.addEntityToGame(aiCharacter4);

	}

	@Test
	public void testFindSpawnPositionException() throws Exception {
		// Make field smaller so there can't be any valid spawns
		spawnAlgorithm = new SpawnAlgorithm(game, new Vector2(45f, 5f));
		// Expected exception occurs here
		spawnAlgorithm.findSpawnPosition();
	}

	@Test
	public void testFindSpawnPosition() throws Exception {
		// Let the algorithm generate some positions
		Vector2[] spawnPositions = new Vector2[10];
		for (int i = 0; i < spawnPositions.length; i++) {
			spawnPositions[i] = spawnAlgorithm.findSpawnPosition();
		}

		// Get the area they shouldn't be in
		List<Rectangle> impossibleLocations = spawnAlgorithm.getImpossibleLocations();

		// Check if generated positions aren't in the area of other entities
		for (Vector2 spawnPosition : spawnPositions) {
			for (Rectangle rectangle : impossibleLocations) {
				assertFalse(rectangle.contains(spawnPosition));
			}
		}
	}
}