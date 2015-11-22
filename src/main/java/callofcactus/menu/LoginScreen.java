package callofcactus.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.account.Account;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author Teun
 */
public class LoginScreen implements Screen {

    private Vector2 usernameLabelPosition;
    private Vector2 passwordLabelPosition;
    private Vector2 usernameTextFieldPosition;
    private Vector2 passwordTextFieldPosition;
    private Vector2 loginButtonPosition;
    private Vector2 invalidLoginLabelPosition;

    private GameInitializer gameInitializer;
    private Stage stage;

    private SpriteBatch spriteBatch;

    private Label usernameLabel, passwordLabel, invalidPasswordLabel;
    private TextField usernameTextfield, passwordTextfield;
    private TextButton loginButton;

    private BackgroundRenderer backgroundRenderer;

    private String username;
    private String password;

    public LoginScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.stage = new Stage();

        spriteBatch = gameInitializer.getBatch();
        backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");

        configureUI();
        positionUI();
    }

    private void configureUI() {
        createUI();
        positionUI();
    }

    private void createUI() {
        usernameLabel = new Label("Username", UISkins.getLabelSkin());

        passwordLabel = new Label("Password", UISkins.getLabelSkin());

        usernameTextfield = new TextField("", UISkins.getTextfieldSkin());
        usernameTextfield.setHeight(50);
        usernameTextfield.setWidth(350);
        usernameTextfield.setColor(Color.WHITE);
        usernameTextfield.setTextFieldListener(usernameTextFieldListener);

        passwordTextfield = new TextField("", UISkins.getTextfieldSkin());
        passwordTextfield.setPasswordCharacter('*');
        passwordTextfield.setPasswordMode(true);
        passwordTextfield.setHeight(50);
        passwordTextfield.setWidth(350);
        passwordTextfield.setColor(Color.WHITE);
        passwordTextfield.setTextFieldListener(passwordTextFieldListener);

        loginButton = new TextButton("Login", UISkins.getButtonSkin());

        loginButton.setHeight(50);
        loginButton.setWidth(350);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                checkValidLogin();
            }
        });

        invalidPasswordLabel = new Label("Invalid Password", UISkins.getLabelSkin());
        invalidPasswordLabel.setColor(Color.RED);
        invalidPasswordLabel.setVisible(false);

        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);
        stage.addActor(invalidPasswordLabel);

        stage.addActor(usernameTextfield);
        stage.addActor(passwordTextfield);

        stage.addActor(loginButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void positionUI() {
        usernameLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 250);
        passwordLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 150);

        passwordTextFieldPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 100);
        usernameTextFieldPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);

        loginButtonPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 50);

        invalidLoginLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 200);

        usernameTextfield.setPosition(usernameTextFieldPosition.x, usernameTextFieldPosition.y);
        passwordTextfield.setPosition(passwordTextFieldPosition.x, passwordTextFieldPosition.y);

        usernameLabel.setPosition(usernameLabelPosition.x, usernameLabelPosition.y);
        passwordLabel.setPosition(passwordLabelPosition.x, passwordLabelPosition.y);

        loginButton.setPosition(loginButtonPosition.x, loginButtonPosition.y);

        invalidPasswordLabel.setPosition(invalidLoginLabelPosition.x, invalidLoginLabelPosition.y);

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


        stage.draw();
        stage.act(v);
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

    private void checkValidLogin() {
        Account account = Account.verifyAccount(usernameTextfield.getText(), passwordTextfield.getText());
        if (account != null) {
            // TODO Handle valid login
            gameInitializer.setScreen(new MainMenu(gameInitializer));
        }else{
            // TODO Handle invalid login
            invalidPasswordLabel.setVisible(true);
            passwordTextfield.setText("");
        }
    }
}