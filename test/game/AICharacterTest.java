package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.ai.AICharacter;
import game.role.AI;
import game.role.Boss;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by Nekkyou on 2-11-2015.
 */
public class AICharacterTest {
	Game game;
	AICharacter ai;
	AICharacter bossAI;
	HumanCharacter human;

	@Before
	public void setUp() throws Exception {

		game = new GameMockup();
		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Boss rol = new Boss();
		Texture AITexture = null;
		Texture bossTexture = null;
		Texture playerTexture = null;

		human = new HumanCharacter(game, location, name, rol, playerTexture, 64, 64);

		Vector2 spawnLocation = new Vector2(100, 100);

		ai = new AICharacter(game, spawnLocation, "AiTest", new AI(), human, AITexture, 10, 10);
		bossAI = new AICharacter(game, spawnLocation, "AIBoss", new Boss(), human, bossTexture, 10, 10);
	}

	@Test
	public void testTakeDamage() {
		int startHealth = ai.getHealth();
		ai.takeDamage(10, human);
		int endHealth = ai.getHealth();
		org.junit.Assert.assertEquals("The health of the AI is wrong", startHealth - endHealth, 10);

		startHealth = bossAI.getHealth();
		bossAI.takeDamage(100, human);
		endHealth = bossAI.getHealth();
		org.junit.Assert.assertEquals("The health of the Boss AI is wrong", startHealth - endHealth, 100);
	}
}
