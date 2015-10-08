package game;

import javafx.scene.image.Image;

import java.awt.*;

public class Sprite {

	private int x;
	private int y;
	private int width;
	private int heigth;
	private Image image;

	/**
	 *
	 * @param image
	 */
	public Sprite(Image image) {
		// TODO - implement Sprite.Sprite
		throw new UnsupportedOperationException();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeigth() {
		return this.heigth;
	}

	/**
	 *
	 * @param s
	 * @param pixelLevel
	 */
	public boolean collidesWith(Sprite s, boolean pixelLevel) {
		// TODO - implement Sprite.collidesWith
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param g
	 */
	public void paint(Graphics g) {
		// TODO - implement Sprite.paint
		throw new UnsupportedOperationException();
	}

}