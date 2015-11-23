package callofcactus;

import callofcactus.entities.HumanCharacter;
import callofcactus.entities.Player;
import callofcactus.entities.ai.AICharacter;
import callofcactus.io.PropertyReader;
import callofcactus.role.AI;
import callofcactus.role.Boss;
import callofcactus.role.Sniper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by guushamm on 16-11-15.
 */
public class SinglePlayerGame extends Game {

	protected GameSounds gameSounds = new GameSounds(this);
	private long lastSpawnTime;
	private int AInumber;
	private int AIAmount;
	private int maxAI;
	private int nextBossAI;


	public SinglePlayerGame() {

		this.lastSpawnTime = 0;
		this.AInumber = 0;
		this.AIAmount = 3;
		this.maxAI = 20;
		this.nextBossAI = 10;
		this.addSinglePlayerHumanCharacter();
	}

	public GameSounds getGameSounds() {
		return gameSounds;
	}

	public HumanCharacter getPlayer() {
		return this.players.get(0);
	}

	public void spawnAI() {
		//Check if the last time you called this method was long enough to call it again.
		//You can change the rate at which the waves spawn by altering the parameter in secondsToMillis

		if (TimeUtils.millis() - lastSpawnTime < secondsToMillis(5)) {
			return;
		}
		waveNumber++;

		for (int i = 0; i < AIAmount; i++) {
			nextBossAI--;
			if (nextBossAI == 0) {
				nextBossAI = 10;
				createBossAI();
			} else {
				createMinionAI();
			}
		}
		if ((waveNumber % (int) getJSON().get(PropertyReader.PICKUP_PER_WAVE)) == 0) {
			this.createPickup();
		}

		//The amount of AI's that will spawn next round will increase with 1 if it's not max already
		if (AIAmount < maxAI) {
			AIAmount++;
		}

		//Set the time to lastSpawnTime so you know when you should spawn next time
		lastSpawnTime = TimeUtils.millis();
	}

	private void createMinionAI() {
		//If it's not a boss

		AICharacter a = new AICharacter(this, new Vector2(1, 1), ("AI" + this.AInumber++), new AI(), getPlayer(), textures.getTexture(GameTexture.texturesEnum.aiTexture), 30, 30);

		try {
			a.setLocation(generateSpawn());
		} catch (NoValidSpawnException nvs) {
			a.destroy();
		}
		//Set the speed for the AI's
		a.setSpeed(2);
	}

	private void createBossAI() {

		AICharacter a = new AICharacter(this, new Vector2(1, 1), ("AI" + AInumber++), new Boss(), getPlayer(), textures.getTexture(GameTexture.texturesEnum.bossTexture), 35, 70);
		try {
			a.setLocation(generateSpawn());
		} catch (NoValidSpawnException nvs) {
			a.destroy();
		}
		//Set the speed for the AI's
		a.setSpeed(4);
	}

	public void addSinglePlayerHumanCharacter() {
		Player p = new HumanCharacter(this, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), "CaptainCactus", new Sniper(), textures.getTexture(GameTexture.texturesEnum.playerTexture), 64, 26);
		this.players.add((HumanCharacter) p);

	}

	@Override
	public void playRandomHitSound() {
        System.out.println("piew");
		this.gameSounds.playRandomHitSound();
	}

	@Override
	public void playRandomBulletSound() {
		System.out.println("piew piew");
		this.gameSounds.playBulletFireSound();

	}

}
