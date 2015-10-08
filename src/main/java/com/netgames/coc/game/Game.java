package com.netgames.coc.game;

import com.netgames.coc.game.menu.*;

import java.awt.*;
import java.util.*;
import com.netgames.coc.account.*;
import com.netgames.coc.game.role.*;
import javafx.geometry.Point2D;

public class Game {

	private MainMenu gameBrowser;
	private ArrayList<Account> accountsInGame;
	private int gameLevel;
	private boolean isActive;
	private boolean bossModeActive;
	private int maxScore;
	private int maxNumberOfPlayers;
	private ArrayList<Entity> entities;

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
	 * Makes a new instance of the class Game
	 */
	public Game() {
		// TODO - implement Game.Game
		throw new UnsupportedOperationException();
	}

	/**
	 * Generates spawnpoints for every entity in the game that needs to be spawned.
	 * This cocludes players (both human and AI), bullets, pickups and all notmoving entities.
	 * @return the spawnpoint for the selected entity
	 */
	public Point2D generateSpawn(Entity entity) {
		// TODO - implement Game.generateSpawn
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param entity
	 */
	public void addEntityToGame(Entity entity){
		entities.add(entity);
	}


}