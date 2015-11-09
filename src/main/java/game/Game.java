package game;

import Multiplayer.Client;
import Multiplayer.Server;
import account.Account;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import game.ai.AICharacter;
import game.io.PropertyReader;
import game.pickups.*;
import game.role.AI;
import game.role.Boss;
import game.role.Role;
import game.role.Soldier;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
	int count=0;
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

    //Sound variable
    private GameSounds gameSounds = new GameSounds(this);
	private Random random;
	//Godmode
	private boolean godMode = false;
    private boolean muted=true;
	/**
	 * Makes a new instance of the class Game
	 *
	 * @param gameLevel          Level of the game to start with
	 * @param maxNumberOfPlayers Max number of players able to join
	 * @param bossModeActive     Should boss mode be activated on start?
	 * @param maxScore           Max score reachable
	 */
	public Game(int gameLevel, int maxNumberOfPlayers, boolean bossModeActive, int maxScore) {
        new Server();
        new Client();
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

		this.random = new Random();
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

    public GameSounds getGameSounds() {
        return gameSounds;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

	public Random getRandom() {
		return random;
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
		ArrayList<Texture> textures = new ArrayList<>();
		textures.add(new Texture(Gdx.files.internal("damagePickup.png")));
		textures.add(new Texture(Gdx.files.internal("healthPickup.png")));
		textures.add(new Texture(Gdx.files.internal("speedPickup.png")));
		textures.add(new Texture(Gdx.files.internal("spike.png")));
		textures.add(new Texture(Gdx.files.internal("robot.png")));

		for (int i = 0; i < AIAmount; i++) {
			nextBossAI--;
			if (nextBossAI == 0) {
				nextBossAI = 10;
				createBossAI(bossAiTexture);
			} else {
				createMinionAI(aiTexture);
			}
		}
		if ((waveNumber % (int)getJSON().get(PropertyReader.PICKUP_PER_WAVE))==0){
			createPickup(textures);
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

	private void createPickup(ArrayList<Texture> pickupTextures){
		int i = (int)(Math.random() *5);

		Pickup pickup = null;
		if (i == 0) {
			pickup = new DamagePickup(this,new Vector2(1,1),pickupTextures.get(0),30,30);
		}
		else if (i == 1){
			pickup = new HealthPickup(this,new Vector2(1,1),pickupTextures.get(1),30,30);
		}
		else if (i == 2){
			pickup = new SpeedPickup(this,new Vector2(1,1),pickupTextures.get(2),30,30);
		}
		else if (i == 3){
			pickup = new SpeedPickup(this,new Vector2(1,1),pickupTextures.get(3),30,30);
		}
		else if (i == 4){
			pickup = new FireRatePickup(this,new Vector2(1,1),pickupTextures.get(4),30,30);
		}

		try {
			pickup.setLocation(generateSpawn(pickup));
		} catch (Exception nvs) {
	//			nvs.printStackTrace();
	//			createPickup(pickupTexture);
	//			pickup.destroy();
		}
	}

	public long secondsToMillis(int seconds) {
		return seconds * 1000;
	}

    /**
     * This method checks every entity in game if two hitboxes overlap, if they do the appropriate action will be taken.
     * This method has reached far beyond what should be asked of a single method but it works.
     * Follow the comments on its threaturous path and you will succes in finding what you seek.
     * This should also be ported to game in the next itteration.
     */
    public void compareHit() {

        //Gets all the entities to check
        List<Entity> entities = this.getAllEntities();
        //A list to put the to remove entities in so they won't be deleted mid-loop.
        List<Entity> toRemoveEntities = new ArrayList<>();

        //A if to make sure the player is correctly checked in the list of entities
        if (!entities.contains(this.getPlayer())) {
            this.addEntityToGame(this.getPlayer());
        }

        //starts a loop of entities that than creates a loop to compare the entity[i] to entity[n]
        //n = i+1 to prevent double checking of entities.
        //Example:
        // entity[1] == entity[2] will be checked
        // entity[2] == entity[1] will not be checked
        //this could be shorter by checking both
        //instead of the ifs but this will be re-evaluated once past the first iteration
        for (int i = 0; i < entities.size(); i++) {
            //gets the first entity to compare to
            Entity a = entities.get(i);

            for (int n = i + 1; n < entities.size(); n++) {
                //gets the second entity to compare to
                Entity b = entities.get(n);

                //Checks if the hitbox of entity a overlaps with the hitbox of entity b, for the hitboxes we chose to use rectangles
                if (a.getHitBox().overlaps(b.getHitBox())) {

					if(!checkBullet(a,b))	continue;

					checkHumanCharacterAndAI(a,b,toRemoveEntities);

					checkPickupAndHumancharacterI(a,b, toRemoveEntities);

                    checkNotMovingEntity(a,b,toRemoveEntities);

                }
            }
        }
        //This will destroy all the entities that will need to be destroyed for the previous checks.
        //this needs to be outside of the loop because you can't delete objects in a list while you're
        //working with the list
        toRemoveEntities.forEach(Entity::destroy);

    }

	/**
	 * Executes actions that need to be executed if a bullet collides with something
	 * @param a
	 * @param b
     * @return
     */
	private boolean checkBullet(Entity a, Entity b)
	{

		//==========================================================================//
		//                                Bullet                                    //
		//==========================================================================//

		if (a instanceof Bullet) {

			//makes it so your own bullets wont destroy eachother
			if (b instanceof Bullet) {
				if (((Bullet) a).getShooter().equals(((Bullet) b).getShooter())) {
					return false;
				}
			}
			//if b is the shooter of bullet a then continue to the next check.
			//because friendly fire is off standard
			if (b instanceof HumanCharacter && ((Bullet) a).getShooter() == b) {
				return false;
			}

			//if the bullet hit something the bullet will disapear by taking damage (this is standard behaviour for bullet.takedamage())
			// and the other entity will take the damage of the bullet.
			a.takeDamage(1);
			if (b instanceof AICharacter) {
				((AICharacter) b).takeDamage(b.getDamage(), player);
			} else {
				b.takeDamage(b.getDamage());
			}

			gameSounds.playRandomHitSound();

		}
		//!!!!! IMPORTANT !!!!!!!!
		// this does exactly the same as the previous if but with a and b turned around
		//!!!!!!!!!!!!!!!!!!!!!!!!
		else if (b instanceof Bullet) {
			count++;

			if (a instanceof Bullet) {
				if (((Bullet) b).getShooter().equals(((Bullet) a).getShooter())) {
					return false;
				}
			}

			//Incase the shooter of the bullet is the one the collision is with break.
			if (a instanceof HumanCharacter && ((Bullet) b).getShooter() == a) {
				return false;
			}

			b.takeDamage(1);

			if (a instanceof AICharacter) {
				((AICharacter) a).takeDamage(b.getDamage(), player);
			} else {
				a.takeDamage(b.getDamage());
			}
			//Play hit sound
			gameSounds.playRandomHitSound();
		}
		//________________________________End_______________________________________//

		return true;


	}
	private void checkHumanCharacterAndAI(Entity a, Entity b, List<Entity> toRemoveEntities)
	{
		//==========================================================================//
		//                    AICharacter & HumanCharacter                          //
		//==========================================================================//

		//Check collision between AI and player
		if (a instanceof HumanCharacter && b instanceof AICharacter) {
			if (!this.getGodmode()) {
				System.out.println("B: " + b.getDamage() + ";  " + b.toString());
				a.takeDamage(b.getDamage());
			}
			toRemoveEntities.add(b);

			if(!isMuted()) {
				gameSounds.playRandomHitSound();
			}
		}
		//Checks the as the previous if but with a and b turned around
		else if (b instanceof HumanCharacter && a instanceof AICharacter) {
			if (!this.getGodmode()) {
				System.out.println("A: " + a.getDamage());
				b.takeDamage(a.getDamage());
			}
			toRemoveEntities.add(a);
		}
		//________________________________End_______________________________________//
	}
	private void checkPickupAndHumancharacterI(Entity a, Entity b, List<Entity> toRemoveEntities) {

		//==========================================================================//
		//                    Pickup & HumanCharacter                               //
		//==========================================================================//
		if (a instanceof HumanCharacter && b instanceof Pickup) {
			((HumanCharacter) a).setCurrentPickup((Pickup)b);
			toRemoveEntities.add(b);
			if(!isMuted()) {
				//Play hit sound
				Sound ouch = Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab1.mp3"));
				ouch.play(.4F);
			}
		}
		//Checks the as the previous if but with a and b turned around
		else if (b instanceof HumanCharacter && a instanceof AICharacter) {
			((HumanCharacter) b).setCurrentPickup((Pickup)a);
			toRemoveEntities.add(a);
		}


		//________________________________End_______________________________________//

	}

	private void checkNotMovingEntity(Entity a, Entity b, List<Entity> toRemoveEntities) {

		//==========================================================================//
		//                      NotMovingEntity Collisions                          //
		//==========================================================================//

		//checks if a MovingEntity has collided with a NotMovingEntity
		//if so, the current location will be set to the previous location
		if (a instanceof NotMovingEntity && ((NotMovingEntity) a).isSolid() && b instanceof MovingEntity) {
			b.setLocation(b.getLastLocation());
		} else if (b instanceof NotMovingEntity && ((NotMovingEntity) b).isSolid() && a instanceof MovingEntity) {
			a.setLocation(a.getLastLocation());
		}

		//________________________________End_______________________________________//
	}

}