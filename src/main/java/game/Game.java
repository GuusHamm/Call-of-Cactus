package game;

import account.Account;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.menu.MainMenu;
import game.role.Role;
import game.role.Soldier;

import java.awt.*;
import java.util.ArrayList;

public class Game {

	private MainMenu gameBrowser;
    //sets the pixels per steps that are taken with every calculation in calculateNewPosition
    private int steps = 1;


    private ArrayList<Account> accountsInGame;
	private int gameLevel;
	private boolean isActive;
	private boolean bossModeActive;
	private int maxScore;
	private int maxNumberOfPlayers;
	private ArrayList<NotMovingEntity> notMovingEntities;
    private ArrayList<MovingEntity> movingEntities;
    private HumanCharacter player;

    //Collision fields
    private Intersector intersector;

	/**
	 * Makes a new instance of the class Game
	 */
    public Game(int gameLevel, int maxNumberOfPlayers, boolean bossModeActive, int maxScore) {
        this.gameLevel = gameLevel;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bossModeActive = bossModeActive;
        this.maxScore = maxScore;
        this.notMovingEntities = new ArrayList<>();
        this. movingEntities = new ArrayList<>();

        // Initialize player
        Vector2 playerLocation = new Vector2(100,100);
        Role playerDefaultRole = new Soldier();


        this.player = new HumanCharacter(this,playerLocation,"Player1",playerDefaultRole,new Texture("player.png"));
        addEntityToGame(player);
		
//        this.player = new HumanCharacter(this,playerLocation,"Player1",playerDefaultRole,new Texture("player.png"));
        FileHandle fileHandle = Gdx.files.internal("cactus.png");
        Texture t = new Texture(fileHandle);

        this.player = new HumanCharacter(this, playerLocation, "Player1", playerDefaultRole, t);

        this.accountsInGame = new ArrayList<>();
        intersector = new Intersector();
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

    public int getGameLevel() {
		return this.gameLevel;
	}

	public int getMaxScore() {
		return this.maxScore;
	}

	public int getMaxNumberOfPlayers() {
		return this.maxNumberOfPlayers;
	}

	public Vector2 getMouse() {
        float x = (float) MouseInfo.getPointerInfo().getLocation().getX();
        float y = (float) MouseInfo.getPointerInfo().getLocation().getY();
        return new Vector2(x,y);
	}
    public ArrayList<Account> getAccountsInGame() {
        return accountsInGame;
    }
	/**
	 * Checks for colissions between to colliders of type Rectangle.
	 */
	public boolean collisionDetect(Rectangle colliderA, Rectangle colliderB) {
		// TODO - implement Game.collisionDetect
        boolean colission;
        if(intersector.overlaps(colliderA,colliderB)){
            colission = true;
        }
		throw new UnsupportedOperationException();
	}

    /**
     * Checks for colissions between to colliders of type Circle.
     */
    public void collisionDetect(Circle colliderA, Circle colliderB) {
        // TODO - implement Game.collisionDetect
        // TODO -
        boolean colission;
        if(intersector.overlaps(colliderA,colliderB)){
            colission = true;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Checks for colissions between a collider of type Circle and a collider of type Rectangle.
     */
    public void collisionDetect(Circle colliderA, Rectangle colliderB) {
        // TODO - implement Game.collisionDetect
        // TODO -
        boolean colission;
        if(intersector.overlaps(colliderA,colliderB)){
            colission = true;
        }
        throw new UnsupportedOperationException();
    }

    /**
	 * Generates spawnvectors for every entity in the game that needs to be spawned.
	 * This includes players (both human and AI), bullets, pickups and all not-moving entities.
	 * @return the spawnvector for the selected entity
	 */
	public Vector2 generateSpawn(Entity entity) {
		// TODO - implement Game.generateSpawn
		throw new UnsupportedOperationException();
	}

    /**
     * Calculates the angle between two vectors
     * @param vector1 Vector
     * @param vector2
     * @return
     */
	public int angle(Vector2 vector1, Vector2 vector2){
		int angle = (360 - (int)Math.toDegrees(Math.atan2(vector2.y - vector1.y, vector2.x- vector1.x)))%360;

		return angle%360;

	}

    /**
     * Calculates the new position between the currentPosition to the Endposition.
     * @param currentPosition
     * @param EndPosition
     * @param speed
     * @return the new position that has been calculated
     */
	public Vector2 calculateNewPosition(Vector2 currentPosition, Vector2 EndPosition,double speed){

        float x = currentPosition.x;
        float y = currentPosition.x;

        //gets the difference of the two x coordinates
        double differenceX =EndPosition.x- x;
        //gets the difference of the two y coordinates
        double differenceY =EndPosition.y- y;

        //pythagoras formula
        double c = Math.sqrt(Math.pow(Math.abs(differenceX),2) +Math.pow(Math.abs(differenceY),2));

        if( c <= (steps * speed))
        {
            return EndPosition;
        }

        double ratio = c/(steps*speed);

        x += (differenceX / ratio);
        y += (differenceY / ratio);

        return new Vector2(x,y);
	}
    /**
     * Calculates the new position from a beginposition and a angle..
     * @param currentPosition
     * @param speed
     * @param angle
     * @return the new position that has been calculated
     */
    public Vector2 calculateNewPosition(Vector2 currentPosition, double speed, double angle){

        angle+=90f;

        double x=currentPosition.x;
        double y=currentPosition.x;

        //uses sin and cos to calculate the EndPosition
        x = x + (Math.sin(Math.toRadians(angle))* (steps * speed));
        y = y + (Math.cos(Math.toRadians(angle))* (steps * speed));

        float xF = Float.parseFloat(Double.toString(x));
        float yF = Float.parseFloat(Double.toString(y));
        return new Vector2(xF, yF);
    }



	/**
	 * Called when an entity needs to be added to the game (Only in the memory, but it is not actually drawn)
	 * @param entity : Adds a new entity to this game
	 */
	public void addEntityToGame(Entity entity){

        if (entity instanceof MovingEntity)
        {
            movingEntities.add((MovingEntity)entity);
        }
        else
        {
            notMovingEntities.add((NotMovingEntity)entity);
        }
	}

	public void removeEntityFromGame(Entity entity){

        if (entity instanceof MovingEntity)
        {
            movingEntities.remove(entity);
        }
        else
        {
            notMovingEntities.remove(entity);
        }
	}

	public void create() {

	}

    public void draw(SpriteBatch spriteBatch) {

    }

    /**
     * @see --https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/Screen.html#render-float-
     * @param delta
     */
    public void update(float delta) {

    }


}