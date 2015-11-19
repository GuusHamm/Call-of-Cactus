package callofcactus.menu;


import callofcactus.GameInitializer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 23-10-2015.
 */
public class SettingsMenu implements Screen {

	private Stage stage;
	private Skin skin;
	private Batch batch;
	private GameInitializer gameInitializer;

	private float screenWidth;
	private float screenHeight;

	private Label lblBaseHealth;
	private List<Label> labels;

	/**
	 * This is the menu where you can alter settings
	 *
	 * @param gameInitializer : The initizialer of the callofcactus
	 */
	public SettingsMenu(GameInitializer gameInitializer) {

		batch = new SpriteBatch();
		labels = new ArrayList<>();
		this.gameInitializer = gameInitializer;

		//GUI code
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		createBasicSkin();

		//Get screen width and height for future reference
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		//Create buttons
		createApplyButton();
		createBackButton();

		//Create labels
		createLabel("Base Health: ", 20, screenHeight - 20);
		createLabel("Base something: ", 20, screenHeight - 40);
		createLabel("Base something else: ", 20, screenHeight - 60);
		createLabel("Base wat: ", 20, screenHeight - 80);
		createLabel("Setting 1: ", 20, screenHeight - 100);
		createLabel("Setting 2: ", 20, screenHeight - 120);
		createLabel("Setting 3: ", 20, screenHeight - 140);
		createLabel("You know self: ", 20, screenHeight - 160);


	}

	@Override
	public void show() {

	}

	/**
	 * Renders the screen
	 *
	 * @param v : Last time the screen was rendered
	 */
	@Override
	public void render(float v) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//GUI code
		stage.act();
		stage.draw();

		batch.begin();
		for (Label l : labels) {
			l.draw(batch, 100);
		}
		batch.end();
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
	 * Creates the basic skins (how the labels and buttons look)
	 */
	private void createBasicSkin() {
		//Create a font
		BitmapFont font = new BitmapFont();
		skin = new Skin();
		skin.add("default", font);

		//Create a texture
		Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));

		//Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		//Create a label style
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.fontColor = Color.BLACK;
		labelStyle.font = skin.getFont("default");
		skin.add("default", labelStyle);

	}

	/**
	 * Create a button which, when clicked, will save the new settings.
	 */
	private void createApplyButton() {
		TextButton applyButton = new TextButton("Apply", skin); // Use the initialized skin
		applyButton.setPosition(screenWidth - screenWidth / 4, 0);
		stage.addActor(applyButton);
		applyButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				//TODO Implement code that will actually store the settings.
			}
		});
	}

	/**
	 * Create a button which, when clicked, will return to the previous menu.
	 */
	private void createBackButton() {
		TextButton backButton = new TextButton("Back", skin); // Use the initialized skin
		backButton.setPosition(screenWidth - screenWidth / 2 - 5, 0);
		stage.addActor(backButton);
		backButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				navigateToMainMenu();
			}
		});
	}

	/**
	 * Create a label
	 *
	 * @param displayText The text the label will display
	 * @param x           The x value of the label's position
	 * @param y           The y value of the label's position
	 */
	private void createLabel(CharSequence displayText, float x, float y) {
		//Create 'Base Health' label
		Label newLabel = new Label(displayText, skin);
		newLabel.setPosition(x, y);
		labels.add(newLabel);
	}

	/**
	 * Go to the main menu
	 */
	private void navigateToMainMenu() {
		System.out.println("Navigated");
		gameInitializer.setScreen(new MainMenu(gameInitializer));
	}
}
