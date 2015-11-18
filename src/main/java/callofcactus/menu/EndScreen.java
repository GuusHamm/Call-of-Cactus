package callofcactus.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.IGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Nino Vrijman on 28-10-2015.
 */
public class EndScreen implements Screen {
	private Stage stage;
	private GameInitializer gameInitializer;
	private IGame game;
	private BitmapFont bitmapFont;
	private SpriteBatch backgroundBatch;
	private BackgroundRenderer backgroundRenderer;

	/**
	 * @param gameInitializer : The initializer of the callofcactus
	 * @param finishedGame    : The callofcactus that is finished
	 */
	public EndScreen(GameInitializer gameInitializer, IGame finishedGame) {
		this.gameInitializer = gameInitializer;
		this.game = finishedGame;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		//Add main menu button
		TextButton mainMenuButton = new TextButton("Go to main menu", createBasicButtonSkin());
		mainMenuButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		stage.addActor(mainMenuButton);

		this.backgroundBatch = new SpriteBatch();
		this.backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");

		mainMenuButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
				sound.play(.3F);
				navigateToMainMenu();
			}
		});

		mainMenuButton.addListener(new InputListener() {
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

		//Add exit button
		TextButton exitButton = new TextButton("Exit", createBasicButtonSkin());
		exitButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() - 10);
		stage.addActor(exitButton);

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

		//Add score
		Label scoreLabel = new Label(getScoreText(), createBasicLabelSkin());
		scoreLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 250);
		stage.addActor(scoreLabel);

		//Add callofcactus over message
		Label gameOverLabel = new Label("GAME OVER", createBasicLabelSkin());
		gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 300);
		stage.addActor(gameOverLabel);

		//Add wave
		Label waveLabel = new Label("You reached wave " + game.getWaveNumber(), createBasicLabelSkin());
		waveLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);
		stage.addActor(waveLabel);
	}

	/**
	 * Go to main menu
	 */
	private void navigateToMainMenu() {
		this.dispose();
		gameInitializer.setScreen(new MainMenu(gameInitializer));
	}

	/**
	 * @return a string that displays "Score: ......." where the dots are the score
	 */
	private String getScoreText() {
		return "Score: " + game.getPlayers().get(0).getScore();
	}

	/**
	 * Shows the stage
	 */
	@Override
	public void show() {
		stage.act();
		stage.draw();
	}

	/**
	 * @param v
	 */
	@Override
	public void render(float v) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//  GUI code
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

	}

	/**
	 * In this method the skin for the buttons is created
	 *
	 * @return Returns the skin for the buttons
	 */
	private Skin createBasicButtonSkin() {
		//Create a font
		BitmapFont font = new BitmapFont();
		Skin skin = new Skin();
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
		return skin;
	}

	/**
	 * In this method the skin for the Labels is created
	 *
	 * @return Returns the skin for the Labels
	 */
	private Skin createBasicLabelSkin() {
		//  Create a font
		BitmapFont font = new BitmapFont();
		Skin skin = new Skin();
		skin.add("default", font);

		//Create a texture
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));

		//  Create a label style

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.BLACK;
		//labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
		skin.add("default", labelStyle);

		return skin;
	}
}