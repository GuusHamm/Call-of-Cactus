package game.menu;

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
import game.BackgroundRenderer;
import game.Game;
import game.GameInitializer;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

	Stage stage;
	private List<Game> games;
	private GameInitializer gameInitializer;
	private SpriteBatch batch;
	//GUI fields
	private Skin skin;
	private Music themeMusic;
	private SpriteBatch backgroundBatch;
	private BackgroundRenderer backgroundRenderer;

	/**
	 * Makes a new instance of the class MainMenu
	 *
	 * @param gameInitializer Initializer used in-game
	 */
	public MainMenu(GameInitializer gameInitializer) {
		// TODO - implement MainMenu.MainMenu
		games = new ArrayList<>();

		this.gameInitializer = gameInitializer;
		this.batch = gameInitializer.getBatch();

		this.backgroundBatch = new SpriteBatch();
		this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

		//GUI code
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		createBasicSkin();
		TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
		newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
		stage.addActor(newGameButton);


		TextButton exitButton = new TextButton("Exit", skin);
		exitButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 3);
		stage.addActor(exitButton);

		//
		newGameButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
				sound.play(.3F);
				navigateToNextScreen();

			}
		});

		newGameButton.addListener(new InputListener() {
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
		});

		exitButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		exitButton.addListener(new InputListener() {
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
		});

		// Playing audio
		themeMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/theme.mp3"));
		themeMusic.setVolume(0.35f);
		themeMusic.setLooping(true);
		themeMusic.play();

	}

	private void navigateToNextScreen() {
		// TODO Go to next screen

		this.dispose();
		//gameInitializer.createNewGame();

		gameInitializer.setScreen(new GameScreen(gameInitializer));
	}

	@Override
	public void show() {
		stage.act();
		stage.draw();
	}

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