package game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.BackgroundRenderer;
import game.Game;
import game.GameInitializer;

/**
 * Created by Nekkyou on 16-11-2015.
 */
public class LoginScreen implements Screen
{
    private Stage stage;
    private GameInitializer gameInitializer;
    private BitmapFont bitmapFont;
    private SpriteBatch batch;
    private BackgroundRenderer backgroundRenderer;

    public LoginScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        batch = gameInitializer.getBatch();
        this.backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");


        //Add a label for username
        Label usernameLabel = new Label("username", createBasicLabelSkin());
        usernameLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 250);
        stage.addActor(usernameLabel);

        //Add a textField for username
        TextField usernameTextField = new TextField("", createBasicTextFieldSkin());
        usernameTextField.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);
        usernameTextField.setHeight(20);
        usernameTextField.setWidth(200);
        stage.addActor(usernameTextField);

        //Add click handler
        usernameTextField.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                stage.setKeyboardFocus(usernameTextField);
            }
        });

        //Add listener
        usernameTextField.setTextFieldListener(new TextField.TextFieldListener()
        {
            @Override
            public void keyTyped(TextField textField, char c)
            {
                usernameTextField.setText(usernameTextField.getText() + c);
            }
        });

        //Add a label for password
        Label passwordLabel = new Label("password", createBasicLabelSkin());
        passwordLabel.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 150);
        stage.addActor(passwordLabel);

    //Add a textField for username
    TextField passwordTextField = new TextField("", createBasicTextFieldSkin());
    passwordTextField.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 100);
    passwordTextField.setPasswordCharacter('*');
    passwordTextField.setPasswordMode(true);
    passwordTextField.setHeight(20);
    passwordTextField.setWidth(200);
    stage.addActor(passwordTextField);
}

    private Skin createBasicTextFieldSkin() {
        return null;
    }

    private Skin createBasicLabelSkin() {
        return null;
    }

    @Override
    public void show()
    {
        stage.act();
        stage.draw();
    }

    @Override
    public void render(float v)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  GUI code
        backgroundRenderer.render(batch);

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int i, int i1)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

}
