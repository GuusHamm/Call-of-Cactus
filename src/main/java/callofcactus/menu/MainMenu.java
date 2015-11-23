package callofcactus.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.Game;
import callofcactus.GameInitializer;
import callofcactus.io.DatabaseManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.menu.LobbyScreen;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

	private Stage stage;
	private List<Game> games;
	private GameInitializer gameInitializer;
	private SpriteBatch batch;
	//GUI fields
	private Skin skin;
	private Music themeMusic;
	private SpriteBatch backgroundBatch;
	private BackgroundRenderer backgroundRenderer;
	private DatabaseManager databaseManager;

	/**
	 * Makes a new instance of the class MainMenu
	 *
	 * @param gameInitializer Initializer used in-callofcactus
	 */
	public MainMenu(GameInitializer gameInitializer) {
		games = new ArrayList<>();

		this.gameInitializer = gameInitializer;
		this.batch = gameInitializer.getBatch();

		this.databaseManager = new DatabaseManager();

		this.backgroundBatch = new SpriteBatch();
		this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

		//GUI code
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		createBasicSkin();

		TextButton newSinglePlayerButton = new TextButton("Singleplayer", skin); // Use the initialized skin
		newSinglePlayerButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, (Gdx.graphics.getHeight() / 2) + newSinglePlayerButton.getHeight() + 1);
		stage.addActor(newSinglePlayerButton);

		TextButton newMultiPlayerButton = new TextButton("Multiplayer", skin); // Use the initialized skin
		newMultiPlayerButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / (2));
		stage.addActor(newMultiPlayerButton);


		TextButton exitButton = new TextButton("Exit", skin);
		exitButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 3);
		stage.addActor(exitButton);

		//Sets all the actions for the Singleplayer Button
		newSinglePlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
				sound.play(0.3f);
				navigateToSinglePlayerGame();
			}
		});
		//Sets all the actions for the Multiplayer Button
		newMultiPlayerButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
				sound.play(0.3f);
				navigateToMultiPlayerGame();

			}
		});
		//Sets all the actions for the Exit Button
		exitButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		//this will make sure sounds will play on hover
		InputListener il = new InputListener() {
			boolean playing = false;

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				if (!playing) {
					Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gui/coc_buttonHover.mp3"));
					sound.play(.2F);
					playing = true;
				}
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				playing = false;
			}
		};

		newSinglePlayerButton.addListener(il);
		newMultiPlayerButton.addListener(il);
		exitButton.addListener(il);


		// Playing audio
		themeMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/theme.mp3"));
		themeMusic.setVolume(0.35f);
		themeMusic.setLooping(true);
		themeMusic.play();

	}

	/**
	 * Goes to the next screen.
	 */
	private void navigateToSinglePlayerGame() {
		// TODO Go to next screen

		this.dispose();
		gameInitializer.createNewSingeplayerGame();

		gameInitializer.setScreen(new GameScreen(gameInitializer));
	}

	/**
	 * Goes to the single player gamescreen.
	 */
	private void navigateToMultiPlayerGame() {
		// TODO Go to next screen

		this.dispose();
		//gameInitializer.createNewMultiplayerGame();
		//gameInitializer.createNewGame();
		gameInitializer.setScreen(new LobbyScreen(gameInitializer));
	}

	public Boolean createAccount(String username, String password) {

		if (password.matches("\\b[a-z]+") || password.matches("\\b[A-Z]+")) {
			System.out.println("Passwords cant be all one case");
			return false;
		}
		if (password.length() < 8) {
			System.out.println("Password must be at least 8 characters long");
			return false;
		}
		if (!password.matches("[^A-z|\\s]+")) {
			System.out.println("Password must have at least one special character");
			return false;
		}

		if (databaseManager.usernameExists(username)) {
			System.out.println("Username is taken");
		}


		return false;
	}

	/**
	 * Shows the screen.
	 */
	@Override
	public void show() {
		stage.act();
		stage.draw();
	}

	/**
	 * Renders the callofcactus
	 *
	 * @param v : The last time this method was called.
	 */
	@Override
	public void render(float v) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (stage.keyDown(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		//GUI code
		backgroundRenderer.render(backgroundBatch);

		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		themeMusic.stop();
		themeMusic.dispose();
	}

	/**
	 * Returns a list with all the games that are currently active
	 *
	 * @return the list of all the current games
	 */
	public List<Game> getAllGames() {
		// TODO - implement MainMenu.getAllLobbies
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a skin for the GUI to use
	 */
	private void createBasicSkin() {
		//Create a font
		BitmapFont font = new BitmapFont();
		skin = new Skin();
		skin.add("default", font);
		skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
		skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));


		//Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("image");
		textButtonStyle.down = skin.newDrawable("hoverImage");
		textButtonStyle.checked = skin.newDrawable("hoverImage");
		textButtonStyle.over = skin.newDrawable("hoverImage");
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

	}
}