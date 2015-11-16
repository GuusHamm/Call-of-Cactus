package game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import game.GameInitializer;

/**
 * Created by Teun on 16-11-2015.
 */
public class LoginScreen2 implements Screen {

    private Vector2 usernameLabelPosition;
    private Vector2 passwordLabelPosition;
    private Vector2 usernameTextFieldPosition;
    private Vector2 passwordTextFieldPosition;

    private GameInitializer gameInitializer;
    private Stage stage;

    private SpriteBatch spriteBatch;

    private Label usernameLabel, passwordLabel;
    private TextField usernameTextfield, passwordTextfield;

    public LoginScreen2(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        spriteBatch = gameInitializer.getBatch();

        createUI();
        positionUI();
    }

    private void createUI() {
        usernameLabel = new Label("Username", UISkin.getLabelSkin());
        passwordLabel = new Label("Password", UISkin.getLabelSkin());

        usernameTextfield = new TextField("", UISkin.getTextfieldSkin());
        passwordTextfield = new TextField("", UISkin.getTextfieldSkin());

        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);

        stage.addActor(usernameTextfield);
        stage.addActor(passwordTextfield);
    }

    private void positionUI() {
        usernameLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 250);
        passwordLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 150);

        passwordTextFieldPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 100);
        usernameTextFieldPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);

        usernameTextfield.setPosition(usernameTextFieldPosition.x, usernameTextFieldPosition.y);
        passwordTextfield.setPosition(passwordTextFieldPosition.x, passwordTextFieldPosition.y);

        usernameLabel.setPosition(usernameLabelPosition.x, usernameLabelPosition.x);
        passwordLabel.setPosition(passwordLabelPosition.x, passwordLabelPosition.y);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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
}
