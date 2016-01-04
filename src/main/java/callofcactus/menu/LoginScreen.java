package callofcactus.menu;

import callofcactus.Administration;
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

    private GameInitializer gameInitializer;
    private Stage stage;

    private Administration administration = Administration.getInstance();

    private SpriteBatch spriteBatch;

    private Label usernameLabel, passwordLabel, invalidPasswordLabel;
    private TextField usernameTextfield, passwordTextfield;
    private TextButton loginButton;
    private TextButton createAccountButton;
    private Label accountCreatedLabel;

    private BackgroundRenderer backgroundRenderer;

    private String username;
    private String password;
    private TextField.TextFieldListener usernameTextFieldListener = (textField, c) -> {
        if ((c == '\r' || c == '\n')) {
            textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
        } else {
            username += c;
        }
    };
    private TextField.TextFieldListener passwordTextFieldListener = (textField, c) -> {
        if ((c == '\r' || c == '\n')) {
            textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
        } else {
            password += c;
        }
    };


    public LoginScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.stage = new Stage();

        spriteBatch = gameInitializer.getBatch();
        backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");

        configureUI();
        positionUI();
    }

    private void configureUI() {
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

        createAccountButton = new TextButton("Create new account", UISkins.getButtonSkin());
        createAccountButton.setHeight(50);
        createAccountButton.setWidth(350);
        createAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                checkAccountExists();
            }
        });

        accountCreatedLabel = new Label("Account successfully created", UISkins.getLabelSkin());
        accountCreatedLabel.setColor(Color.BLACK);
        accountCreatedLabel.setVisible(false);

        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);
        stage.addActor(invalidPasswordLabel);

        stage.addActor(usernameTextfield);
        stage.addActor(passwordTextfield);

        stage.addActor(loginButton);
        stage.addActor(createAccountButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void positionUI() {
        Vector2 usernameLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 250);
        Vector2 passwordLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 150);

        Vector2 passwordTextFieldPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 100);
        Vector2 usernameTextFieldPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);

        Vector2 loginButtonPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 50);

        Vector2 invalidLoginLabelPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 200);

        Vector2 createAccountButtonPosition = new Vector2(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 100);

        usernameTextfield.setPosition(usernameTextFieldPosition.x, usernameTextFieldPosition.y);
        passwordTextfield.setPosition(passwordTextFieldPosition.x, passwordTextFieldPosition.y);

        usernameLabel.setPosition(usernameLabelPosition.x, usernameLabelPosition.y);
        passwordLabel.setPosition(passwordLabelPosition.x, passwordLabelPosition.y);

        loginButton.setPosition(loginButtonPosition.x, loginButtonPosition.y);

        invalidPasswordLabel.setPosition(invalidLoginLabelPosition.x, invalidLoginLabelPosition.y);


        createAccountButton.setPosition(createAccountButtonPosition.x, createAccountButtonPosition.y);

    }

    @Override
    public void show() {
        return;
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

    private void checkValidLogin() {
        boolean account = false;

        try{
            if(administration.logIn(usernameTextfield.getText(),passwordTextfield.getText())){
                gameInitializer.setScreen(new MainMenu(gameInitializer, Account.getAccount(usernameTextfield.getText())));
            }
//            account = Account.verifyAccount(usernameTextfield.getText(), passwordTextfield.getText());
//            if (account)
//                gameInitializer.setScreen(new MainMenu(gameInitializer, Account.getAccount(usernameTextfield.getText())));
        }
        catch(StringIndexOutOfBoundsException e){
            invalidPasswordLabel.setVisible(true);
            passwordTextfield.setText("");
        }

        if (account) {
            // TODO Handle valid login

        } else {
            // TODO Handle invalid login

            invalidPasswordLabel.setVisible(true);
            passwordTextfield.setText("");
        }
    }

    private void checkAccountExists()
    {
        try{
            boolean account = Account.verifyAccount(usernameTextfield.getText(), passwordTextfield.getText());
            usernameTextfield.setText("");
            passwordTextfield.setText("");
        }
        // Account does not exist
        catch(StringIndexOutOfBoundsException e){

            if (Administration.getInstance().getDatabaseManager().addAccount(usernameTextfield.getText(), passwordTextfield.getText())) {
                Administration.getInstance().getDatabaseManager().addMultiplayerResult(1, administration.getDatabaseManager().getAccountID(usernameTextfield.getText()), 0, 0, 0);
                accountCreatedLabel.setText("Account successfully created");
                accountCreatedLabel.setColor(Color.BLACK);
                accountCreatedLabel.setVisible(true);
            }
            else {
                accountCreatedLabel.setText("Account creation failed");
                accountCreatedLabel.setColor(Color.RED);
                accountCreatedLabel.setVisible(true);
            }
        }

    }
}