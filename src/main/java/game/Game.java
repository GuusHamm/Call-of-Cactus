package game;

import account.Account;
import game.menu.MainMenu;
import javafx.geometry.Point2D;

import javax.xml.stream.Location;
import java.util.ArrayList;

public class Game {

	private MainMenu gameBrowser;
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
	 * Called when an entity needs to be added to the game (Only in the memory, but it is not actually drawn)
	 * @param entity : Adds a new entity to this game
	 */
	public void addEntityToGame(Entity entity){
		entities.add(entity);
	}
	public void removeEntityFromGame(Entity entity){
		entities.add(entity);
	}


}