package game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import game.BackgroundRenderer;
import game.GameInitializer;

/**
 * @author Teun
 */
public class LoginScreen implements Screen {

    private Vector2 usernameLabelPosition;
    private Vector2 passwordLabelPosition;
    private Vector2 usernameTextFieldPosition;
    private Vector2 passwordTextFieldPosition;

    private GameInitializer gameInitializer;
    private Stage stage;

    private SpriteBatch spriteBatch;

    private Label usernameLabel, passwordLabel;
    private TextField usernameTextfield, passwordTextfield;

    private BackgroundRenderer backgroundRenderer;

    private String username;
    private String password;

    public LoginScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        spriteBatch = gameInitializer.getBatch();
        backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");

        createUI();
        positionUI();
    }

    private void createUI() {
        usernameLabel = new Label("Username", UISkin.getLabelSkin());

        passwordLabel = new Label("Password", UISkin.getLabelSkin());

        usernameTextfield = new TextField("", UISkin.getTextfieldSkin());
        usernameTextfield.setHeight(50);
        usernameTextfield.setWidth(350);
        usernameTextfield.setColor(Color.BLACK);
        usernameTextfield.setTextFieldListener(usernameTextFieldListener);

        passwordTextfield = new TextField("", UISkin.getTextfieldSkin());
        passwordTextfield.setPasswordCharacter('*');
        passwordTextfield.setPasswordMode(true);
        passwordTextfield.setHeight(50);
        passwordTextfield.setWidth(350);
        passwordTextfield.setColor(Color.BLACK);
        passwordTextfield.setTextFieldListener(passwordTextFieldListener);

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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  GUI code
        backgroundRenderer.render(spriteBatch);

        stage.act(v);
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

    TextField.TextFieldListener usernameTextFieldListener = (textField, c) -> {
        if ((c == '\r' || c == '\n')){
            textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
        }else{
            username += c;
        }
    };

    TextField.TextFieldListener passwordTextFieldListener = (textField, c) -> {
        if ((c == '\r' || c == '\n')){
            textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
        }else{
            password += c;
        }
    };
}
