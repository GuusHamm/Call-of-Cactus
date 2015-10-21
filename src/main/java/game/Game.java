package game;

import account.Account;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import game.menu.MainMenu;
import game.role.Role;
import game.role.Soldier;

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
        this.movingEntities = new ArrayList<>();

        // Initialize player
        Vector2 playerLocation = new Vector2(100,100);
        Role playerDefaultRole = new Soldier();


        this.player = new HumanCharacter(this,playerLocation,"Player1",playerDefaultRole,new Texture(Gdx.files.internal("player.png")));
        addEntityToGame(player);

//        this.player = new HumanCharacter(this,playerLocation,"Player1",playerDefaultRole,new Texture("player.png"));
        FileHandle fileHandle = Gdx.files.internal("player.png");
        Texture t = new Texture(fileHandle);

        this.player = new HumanCharacter(this, playerLocation, "Player1", playerDefaultRole, t);

        this.accountsInGame = new ArrayList<>();
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
        Vector2 playerLocation = new Vector2(100,100);
        Role playerDefaultRole = new Soldier();

        this.player = new HumanCharacter(this, playerLocation, "Player1", playerDefaultRole, null);

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
//        float x = (float) MouseInfo.getPointerInfo().getLocation().getX();
//        float y = (float) MouseInfo.getPointerInfo().getLocation().getY();
        float x = Gdx.input.getX();
        float y =  Gdx.input.getY();

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
     * @param beginVector   : The vector that will be used as center
     * @param endVector     : Where the object has to point to
     * @return Returns the angle, this will be between 0 and 360 degrees
     */
	public int angle(Vector2 beginVector, Vector2 endVector){

        int angle = (360-(int)Math.toDegrees(Math.atan2(endVector.y - beginVector.y, endVector.x- beginVector.x)));
        return angle%360;

	}

    /**
     * Calculates the new position between the currentPosition to the Endposition.
     * @param currentPosition   : The current position of the object
     * @param EndPosition       : The position of the end point
     * @param speed             : The speed that the object can move with
     * @return the new position that has been calculated
     */
	public Vector2 calculateNewPosition(Vector2 currentPosition, Vector2 EndPosition,double speed){

        float x = currentPosition.x;
        float y = currentPosition.y;

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
     * @param currentPosition   : The current position of the object
     * @param speed             : The speed that the object can move with
     * @param angle             : The angle of where the object should be heading
     * @return the new position that has been calculated
     */
    public Vector2 calculateNewPosition(Vector2 currentPosition, double speed, double angle){

        angle+=90f;

        double x=currentPosition.x;
        double y=currentPosition.y;

        //uses sin and cos to calculate the EndPosition
        x = x + (Math.sin(Math.toRadians(angle))* (steps * speed));
        y = y + (Math.cos(Math.toRadians(angle))* (steps * speed));

        float xF = Float.parseFloat(Double.toString(x));
        float yF = Float.parseFloat(Double.toString(y));
        return new Vector2(xF, yF);
    }



	/**
	 * Called when an entity needs to be added to the game (Only in the memory, but it is not actually drawn)
	 * @param entity : Entity that should be added to the game
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
        else if(entity instanceof NotMovingEntity)
        {
            notMovingEntities.remove(entity);
        }
	}

}