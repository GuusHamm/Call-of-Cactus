package game;

import Multiplayer.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.menu.MainMenu;

/**
 * @author Teun
 */
public class GameInitializer extends Game {

	private game.Game game;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public GameInitializer() {
		super();
	}

	@Override
	public void create() {

		int width = Gdx.graphics.getDesktopDisplayMode().width;
		int height = Gdx.graphics.getDesktopDisplayMode().height;
		;

		Gdx.graphics.setDisplayMode(width, height, true);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		batch = new SpriteBatch();

		this.setScreen(new MainMenu(this));
	}

	public void createNewSingeplayerGame() {
		this.game = new SinglePlayerGame();
		((SinglePlayerGame) game).addSinglePlayerHumanCharacter();
	}

	public void createNewMultiplayerGame() {
		try {
			//TODO use the server for getting the MultiplayerGame
			this.game = new Client().startClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.exit();
	}

//	public OrthographicCamera getCamera() {
//		return camera;
//	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public game.Game getGame() {
		return game;
	}
}
