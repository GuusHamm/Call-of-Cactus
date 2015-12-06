package callofcactus.menu;

import callofcactus.account.Account;
import callofcactus.GameInitializer;
import callofcactus.BackgroundRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.awt.event.InputEvent;
import java.util.List;

/**
 * Created by Kees on 23/11/2015.
 */
public class WaitingRoom implements Screen {

    private Stage stage;
    private Skin buttonBackSkin;
    private List<Account> accounts;
    private float screenWidth;
    private float screenHeight;
    private GameInitializer gameInitializer;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;

    public WaitingRoom(GameInitializer gameInitializer){

        this.gameInitializer = gameInitializer;
        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");

        //GUI
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonBackSkin = createBasicButtonBackSkin();
        createBackButton();

        //Get screen width and height for future reference
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {

    }

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

    private Skin createBasicButtonBackSkin() {
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
     * Create a button which, when clicked, will return to the lobby.
     */
    private void createBackButton() {
        TextButton backButton = new TextButton("Back", buttonBackSkin); // Use the initialized skin
        backButton.setPosition(screenWidth - screenWidth / 2 - 5, 0);
        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                navigateToLobby();
            }
        });
    }

    /**
     * Navigates back to the lobby.
     */
    private void navigateToLobby() {
        System.out.println("Navigated");
        //TODO MainMenu needs to be Lobby when the lobby is imlemented
        gameInitializer.setScreen(new MainMenu(gameInitializer));
    }

}
