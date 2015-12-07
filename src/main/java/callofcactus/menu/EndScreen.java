package callofcactus.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.IGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author Nino Vrijman
 */
public class EndScreen implements Screen {
	private Stage stage;
	private GameInitializer gameInitializer;
	private IGame game;
	private SpriteBatch backgroundBatch;
	private BackgroundRenderer backgroundRenderer;
	private InputListener hoverClick = new InputListener() {
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
		TextButton mainMenuButton = new TextButton("Go to main menu", UISkins.getButtonSkin());
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

		mainMenuButton.addListener(hoverClick);

		//Add exit button
		TextButton exitButton = new TextButton("Exit", UISkins.getButtonSkin());
		exitButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() - 10);
		stage.addActor(exitButton);

		exitButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		exitButton.addListener(hoverClick);

		//Add score
		Label scoreLabel = new Label(getScoreText(), UISkins.getLabelSkin());
		scoreLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 250);
		stage.addActor(scoreLabel);

		//Add callofcactus over message
		Label gameOverLabel = new Label("GAME OVER", UISkins.getLabelSkin());
		gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 300);
		stage.addActor(gameOverLabel);

		//Add wave
		Label waveLabel = new Label("You reached wave " + game.getWaveNumber(), UISkins.getLabelSkin());
		waveLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);
		stage.addActor(waveLabel);

		//Add kills
		Label killLabel = new Label("You've killed " + game.getPlayer().getKillCount(), UISkins.getLabelSkin());
		killLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 150);
		stage.addActor(killLabel);
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
	 * @param v Delta time in seconds
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
		return;
	}

	@Override
	public void pause() {
		return;
	}

	@Override
	public void resume() {
		return;
	}

	@Override
	public void hide() {
		return;
	}

	@Override
	public void dispose() {
		return;
	}
}
