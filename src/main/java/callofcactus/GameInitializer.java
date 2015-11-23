package callofcactus;

import callofcactus.menu.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Teun
 */
public class GameInitializer extends Game {

	private callofcactus.IGame game;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public GameInitializer() {
		super();
	}

	@Override
	public void create() {

		int width = Gdx.graphics.getDesktopDisplayMode().width;
		int height = Gdx.graphics.getDesktopDisplayMode().height;
//		int width = 1000;
//		int height = 1000;


		Gdx.graphics.setDisplayMode(width, height, true);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		batch = new SpriteBatch();

		this.setScreen(new MainMenu(this));
	}

	public void createNewSingeplayerGame() {

		try {
			this.game = new SinglePlayerGame();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void createNewMultiplayerGame() {
		try {
			//TODO use the server for getting the MultiplayerGame
			this.game = new MultiPlayerGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.exit();
	}

// public OrthographicCamera getCamera() {
//  return camera;
// }

	public SpriteBatch getBatch() {
		return batch;
	}

	public callofcactus.IGame getGame() {
		return game;
	}
}