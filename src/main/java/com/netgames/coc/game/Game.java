package com.netgames.coc.game;

import com.netgames.coc.game.menu.*;

import java.awt.*;
import java.util.*;
import com.netgames.coc.account.*;
import com.netgames.coc.game.role.*;
import javafx.geometry.Point2D;

public class Game {

	MainMenu GameBrowser;
	Collection<Account> ActiveGame;
	private int gameLevel;
	private boolean isActive;
	private boolean bossModeActive;
	private int maxScore;
	private int maxNumberOfPlayers;

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

	public void collisionDetect() {
		// TODO - implement Game.collisionDetect
		throw new UnsupportedOperationException();
	}

	public Game() {
		// TODO - implement Game.Game
		throw new UnsupportedOperationException();
	}

	public Point2D generateSpawn() {
		// TODO - implement Game.generateSpawn
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param playerRole
	 */
	public Player newPlayer(Role playerRole) {
		// TODO - implement Game.newPlayer
		throw new UnsupportedOperationException();
	}

	public Entity newGameObject() {
		// TODO - implement Game.newGameObject
		throw new UnsupportedOperationException();
	}

}