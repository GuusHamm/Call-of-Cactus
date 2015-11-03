package game;

import account.Account;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import game.io.PropertyReader;
import game.role.AI;
import game.role.Boss;
import game.role.Role;
import game.role.Soldier;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	//sets the pixels per steps that are taken with every calculation in calculateNewPosition
	private int steps = 1;
	private ArrayList<Account> accountsInGame;
	private int gameLevel;
	private boolean bossModeActive;
	private int maxScore;
	private int maxNumberOfPlayers;
	private ArrayList<NotMovingEntity> notMovingEntities;
	private ArrayList<MovingEntity> movingEntities;
	private HumanCharacter player;
	private Vector2 mousePositions = new Vector2(0, 0);
	private PropertyReader propertyReader;
	//Collision fields
	private Intersector intersector;
	//Ai variables
	private long lastSpawnTime = 0;
	private int AInumber = 0;
	private int AIAmount = 3;
	private int maxAI = 20;
	private int nextBossAI = 10;
	private int waveNumber = 0;
	//Godmode
	private boolean godMode = false;

	/**
	 * Makes a new instance of the class Game
	 *
	 * @param gameLevel          Level of the game to start with
	 * @param maxNumberOfPlayers Max number of players able to join
	 * @param bossModeActive     Should boss mode be activated on start?
	 * @param maxScore           Max score reachable
	 */
	public Game(int gameLevel, int maxNumberOfPlayers, boolean bossModeActive, int maxScore) {
		this.gameLevel = gameLevel;
		this.maxNumberOfPlayers = maxNumberOfPlayers;
		this.bossModeActive = bossModeActive;
		this.maxScore = maxScore;
		this.notMovingEntities = new ArrayList<>();
		this.movingEntities = new ArrayList<>();

		Role playerDefaultRole = new Soldier();

		FileHandle fileHandle = Gdx.files.internal("player.png");
		Texture t = new Texture(fileHandle);

		try {
			this.propertyReader = new PropertyReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Player p = new HumanCharacter(this, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), "CaptainCactus", playerDefaultRole, t, 64, 64);

		this.player = (HumanCharacter) p;
		addEntityToGame(p);

		FileHandle fileHandle2 = Gdx.files.internal("wall.png");
		Texture t2 = new Texture(fileHandle2);

		intersector = new Intersector();
	}

	public Game() {
		this.gameLevel = 1;
		this.maxNumberOfPlayers = 1;
		this.bossModeActive = false;
		this.maxScore = 100;
		this.notMovingEntities = new ArrayList<>();
		this.movingEntities = new ArrayList<>();

		// Initialize player
		Vector2 playerLocation = new Vector2(100, 100);
		Role playerDefaultRole = new Soldier();

		try {
			this.propertyReader = new PropertyReader();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Player p = new HumanCharacter(this, new Vector2(1, 1), "CaptainCactus", playerDefaultRole, null, 64, 64);
		this.player = (HumanCharacter) p;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

	public int getGameLevel() {
		return gameLevel;
	}

	private Vector2 findPlayerSpawnLocation() {
		SpawnAlgorithm spawnAlgorithm = new SpawnAlgorithm(this);
		try {
			return spawnAlgorithm.findSpawnPosition();
		} catch (NoValidSpawnException e) {
			e.printStackTrace();
			System.out.println("Could not find spawn position for the player");
			Gdx.app.exit();
		}
		return new Vector2(150, 150);
	}

	public void setMousePositions(int x, int y) {
		this.mousePositions = new Vector2(x, y);
	}

	public JSONObject getJSON() {
		return propertyReader.getJsonObject();
	}

	public ArrayList<NotMovingEntity> getNotMovingEntities() {
		return notMovingEntities;
	}

	public HumanCharacter getPlayer() {
		return player;
	}

	public ArrayList<MovingEntity> getMovingEntities() {
		return movingEntities;
	}

	public Vector2 getMouse() {
		float x = this.mousePositions.x;//Gdx.input.getX();
		float y = this.mousePositions.y;// Gdx.input.getY();

		return new Vector2(x, y);
	}

	public List<Entity> getAllEntities() {
		List<Entity> result = new ArrayList<>();
		result.addAll(notMovingEntities);
		result.addAll(movingEntities);
		return Collections.unmodifiableList(result);
	}

	public boolean getGodmode() {
		return this.godMode;
	}

	public int getWaveNumber() {
		return this.waveNumber;
	}

	/**
	 * Generates spawnvectors for every entity in the game that needs to be spawned.
	 * This includes players (both human and AI), bullets, pickups and all not-moving entities.
	 *
	 * @param entity Entity to generate a spawn point for
	 * @return the spawnvector for the selected entity
	 * @throws NoValidSpawnException Thrown when no valid spawn position has been found
	 */
	public Vector2 generateSpawn(Entity entity) throws NoValidSpawnException {
		// TODO - implement Game.generateSpawn
		SpawnAlgorithm spawnAlgorithm = new SpawnAlgorithm(this);
		return spawnAlgorithm.findSpawnPosition();
	}

	/**
	 * Calculates the angle between two vectors
	 *
	 * @param beginVector : The vector that will be used as center
	 * @param endVector   : Where the object has to point to
	 * @return Returns the angle, this will be between 0 and 360 degrees
	 */
	public int angle(Vector2 beginVector, Vector2 endVector) {

		int angle = (360 - (int) Math.toDegrees(Math.atan2(endVector.y - beginVector.y, endVector.x - beginVector.x)));
		return angle % 360;

	}

	/**
	 * Calculates the new position between the currentPosition to the Endposition.
	 *
	 * @param currentPosition : The current position of the object
	 * @param EndPosition     : The position of the end point
	 * @param speed           : The speed that the object can move with
	 * @return the new position that has been calculated
	 */
	public Vector2 calculateNewPosition(Vector2 currentPosition, Vector2 EndPosition, double speed) {

		float x = currentPosition.x;
		float y = currentPosition.y;

		//gets the difference of the two x coordinates
		double differenceX = EndPosition.x - x;
		//gets the difference of the two y coordinates
		double differenceY = EndPosition.y - y;

		//pythagoras formula
		double c = Math.sqrt(Math.pow(Math.abs(differenceX), 2) + Math.pow(Math.abs(differenceY), 2));

		if (c <= (steps * speed)) {
			return EndPosition;
		}

		double ratio = c / (steps * speed);

		x += (differenceX / ratio);
		y += (differenceY / ratio);

		return new Vector2(x, y);
	}

	/**
	 * Calculates the new position from a beginposition and a angle..
	 *
	 * @param currentPosition : The current position of the object
	 * @param speed           : The speed that the object can move with
	 * @param angle           : The angle of where the object should be heading
	 * @return the new position that has been calculated
	 */
	public Vector2 calculateNewPosition(Vector2 currentPosition, double speed, double angle) {

		angle += 90f;

		double x = currentPosition.x;
		double y = currentPosition.y;

		//uses sin and cos to calculate the EndPosition
		x = x + (Math.sin(Math.toRadians(angle)) * (steps * speed));
		y = y + (Math.cos(Math.toRadians(angle)) * (steps * speed));

		float xF = Float.parseFloat(Double.toString(x));
		float yF = Float.parseFloat(Double.toString(y));
		return new Vector2(xF, yF);
	}


	/**
	 * Called when an entity needs to be added to the game (Only in the memory, but it is not actually drawn)
	 *
	 * @param entity : Entity that should be added to the game
	 */
	public void addEntityToGame(Entity entity) {

		if (entity instanceof MovingEntity) {
			movingEntities.add((MovingEntity) entity);
			if (entity instanceof HumanCharacter) System.out.println("add human");
		} else {
			notMovingEntities.add((NotMovingEntity) entity);
		}
	}

	public void removeEntityFromGame(Entity entity) {

		if (entity instanceof MovingEntity) {
			movingEntities.remove(entity);
			if (entity instanceof HumanCharacter)
				System.out.println("remove human");
			//  TODO change end game condition for iteration 2 of the game

		} else if (entity instanceof NotMovingEntity) {
			notMovingEntities.remove(entity);
		}
	}

	public void spawnAI() {
		//Check if the last time you called this method was long enough to call it again.
		//You can change the rate at which the waves spawn by altering the parameter in secondsToMillis

		if (TimeUtils.millis() - lastSpawnTime < secondsToMillis(5)) {
			return;
		}
		waveNumber++;
		Texture aiTexture = new Texture(Gdx.files.internal("robot.png"));
		Texture bossAiTexture = new Texture(Gdx.files.internal("boss.png"));
		for (int i = 0; i < AIAmount; i++) {
			nextBossAI--;
			if (nextBossAI == 0) {
				nextBossAI = 10;
				createBossAI(bossAiTexture);
			} else {
				createMinionAI(aiTexture);
			}
		}
		//The amount of AI's that will spawn next round will increase with 1 if it's not max already
		if (AIAmount < maxAI) {
			AIAmount++;
		}

		//Set the time to lastSpawnTime so you know when you should spawn next time
		lastSpawnTime = TimeUtils.millis();
	}


	private void createMinionAI(Texture aiTexture) {
		//If it's not a boss

		AICharacter a = new AICharacter(this, new Vector2(1, 1), ("AI" + AInumber++), new AI(), getPlayer(), aiTexture, 30, 30);

		try {
			a.setLocation(generateSpawn(a));
		} catch (NoValidSpawnException nvs) {
			a.destroy();
		}
		//Set the speed for the AI's
		a.setSpeed(2);
	}

	private void createBossAI(Texture aiTexture) {

		AICharacter a = new AICharacter(this, new Vector2(1, 1), ("AI" + AInumber++), new Boss(), getPlayer(), aiTexture, 35, 70);
		try {
			a.setLocation(generateSpawn(a));
		} catch (NoValidSpawnException nvs) {
			a.destroy();
		}
		//Set the speed for the AI's
		a.setSpeed(4);
	}


	public long secondsToMillis(int seconds) {
		return seconds * 1000;
	}

}