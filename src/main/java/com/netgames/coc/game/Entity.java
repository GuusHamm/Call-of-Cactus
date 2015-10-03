package com.netgames.coc.game;

import javafx.geometry.Point2D;

public abstract class Entity {

	Game Entities;
	private Point2D location;

	public Point2D getLocation() {
		return this.location;
	}

	public boolean kill() {
		// TODO - implement SpriteClass.kill
		throw new UnsupportedOperationException();
	}

	public void paint() {
		// TODO - implement SpriteClass.paint
		throw new UnsupportedOperationException();
	}

	public void addToGame() {
		// TODO - implement SpriteClass.addToGame
		throw new UnsupportedOperationException();
	}

}