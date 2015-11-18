package testClasses;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.rmi.RemoteException;

/**
 * Created by Teun on 19-10-2015.
 */
public class TestGame extends Game {
	private callofcactus.Game game;

	private Texture bulletTexture;
	private Texture playerTexture;
	private Texture wallTexture;

	public TestGame() {
		super();
	}

	@Override
	public void create() {
		try {
			game = new callofcactus.SinglePlayerGame();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		bulletTexture = new Texture(Gdx.files.internal("spike.png"));
		playerTexture = new Texture(Gdx.files.internal("player.png"));
		wallTexture = new Texture(Gdx.files.internal("wall.png"));

		game.notify();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public callofcactus.Game getGame() {
		return game;
	}

	public Texture getBulletTexture() {
		return bulletTexture;
	}

	public Texture getPlayerTexture() {
		return playerTexture;
	}

	public Texture getWallTexture() {
		return wallTexture;
	}
}
