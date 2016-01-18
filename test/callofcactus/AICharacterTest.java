package callofcactus;

import callofcactus.entities.HumanCharacter;
import callofcactus.entities.ai.AICharacter;
import callofcactus.role.AI;
import callofcactus.role.Boss;
import com.badlogic.gdx.math.Vector2;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Nekkyou on 2-11-2015.
 */
public class AICharacterTest extends BaseTest {
	IGame game;
	AICharacter ai;
	AICharacter bossAI;
	HumanCharacter human;

	@BeforeClass
	public void setUp() throws Exception {
		game = new SinglePlayerGame();
		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Boss rol = new Boss();

		human = new HumanCharacter(game, location, name, rol, GameTexture.texturesEnum.playerTexture, 64, 64, false);

		Vector2 spawnLocation = new Vector2(100, 100);

		ai = new AICharacter(game, spawnLocation, "AiTest", new AI(), human, GameTexture.texturesEnum.aiTexture, 10, 10, true);
		bossAI = new AICharacter(game, spawnLocation, "AIBoss", new Boss(), human, GameTexture.texturesEnum.bossTexture, 10, 10, true);
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
