package game;

import account.Account;
import game.menu.MainMenu;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Game extends com.badlogic.gdx.Game {

	private MainMenu gameBrowser;
    //sets the pixels per steps that are taken with every calculation in calculateNewPosition
    private int steps = 1;
    public ArrayList<Account> getAccountsInGame() {
        return accountsInGame;
    }

    private ArrayList<Account> accountsInGame;
	private int gameLevel;
	private boolean isActive;
	private boolean bossModeActive;
	private int maxScore;
	private int maxNumberOfPlayers;
	private ArrayList<Entity> entities;

	/**
	 * Makes a new instance of the class Game
	 */
    public Game(int gameLevel, int maxNumberOfPlayers, boolean bossModeActive, int maxScore) {
        this.gameLevel = gameLevel;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.bossModeActive = bossModeActive;
        this.maxScore = maxScore;
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

	public Point2D getMouse() {
		// TODO - implement Game.getMouse
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 */
	public void collisionDetect() {
		// TODO - implement Game.collisionDetect
        // TODO -
		throw new UnsupportedOperationException();
	}

    /**
	 * Generates spawnpoints for every entity in the game that needs to be spawned.
	 * This includes players (both human and AI), bullets, pickups and all not-moving entities.
	 * @return the spawnpoint for the selected entity
	 */
	public Point2D generateSpawn(Entity entity) {
		// TODO - implement Game.generateSpawn
		throw new UnsupportedOperationException();
	}

	public int angle(Point2D point1, Point2D point2){
		int angle = 360 - (int)Math.toDegrees(Math.atan2(point2.getY() - point1.getY(), point2.getX()- point1.getX()));

		if(angle > 360){
			angle -= 360;
		}

		return angle;

	}

    /**
     * Calculates the new position between the currentPosition to the Endposition.
     * @param currentPosition
     * @param EndPosition
     * @param speed
     * @return the new position that has been calculated
     */
	public Point2D calculateNewPosition(Point2D currentPosition,Point2D EndPosition,int speed){

        double x=currentPosition.getX();
        double y=currentPosition.getY();

        //calculating...
        //wow so many ifs -.- need to fiz that

        //gets the difference of the two x coordinates
        double differenceX =currentPosition.getX()- EndPosition.getX();
        //gets the difference of the two y coordinates
        double differenceY =currentPosition.getY()- EndPosition.getY();

        //checks if it should go in the positive or negative direction
        if(differenceX>=0)
        {
            //checks if the steps that should be taken is bigger than the difference.
            //if so it will use the exact steps
            if(differenceX > (steps*speed))
                x +=(steps*speed);
            else
                x+=differenceX;
        }
        else
        {
            //checks if the steps that should be taken is bigger than the difference.
            //if so it will use the exact steps
            if(differenceX > (steps*speed))
                x -=(steps*speed);
            else
                x-=differenceX;
        }

        //checks if it should go in the positive or negative direction
        if(differenceY>=0)
        {
            //checks if the steps that should be taken is bigger than the difference.
            //if so it will use the exact steps
            if(differenceY > (steps*speed))
                y +=(steps*speed);
            else
                y+=differenceY;
        }
        else
        {
            //checks if the steps that should be taken is bigger than the difference.
            //if so it will use the exact steps
            if(differenceY > (steps*speed))
                y -=(steps*speed);
            else
                y-=differenceY;
        }

		return new Point2D(x,y);
	}

    public Point2D calculateNewPosition(Point2D currentPosition, int speed, double angle){

        double x=currentPosition.getX();
        double y=currentPosition.getY();

        //checks if the direction is in the positive or negative direction
        if(angle>180)
        {
            //uses sin and cos to calculate the EndPosition
            x = x + (Math.sin(Math.toRadians(angle)) * (steps * speed));
            y = y + (Math.cos(Math.toRadians(angle)) * (steps * speed));
        }
        else
        {
            x = x - (Math.sin(Math.toRadians(angle)) * (steps * speed));
            y = y - (Math.cos(Math.toRadians(angle)) * (steps * speed));
        }
        //uses the calculated EndPosition to calculate where to go to and returns the newly calculated point2D
        return calculateNewPosition(currentPosition, new Point2D(x,y), speed);
    }



	/**
	 * Called when an entity needs to be added to the game (Only in the memory, but it is not actually drawn)
	 * @param entity : Adds a new entity to this game
	 */
	public void addEntityToGame(Entity entity){
		entities.add(entity);
	}
	public void removeEntityFromGame(Entity entity){
		entities.add(entity);
	}

	public void create() {

	}


}