package callofcactus.menu;

import callofcactus.account.Account;
import callofcactus.GameInitializer;
import callofcactus.BackgroundRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.awt.event.InputEvent;
import java.util.ArrayList;

/**
 * Created by Kees on 23/11/2015.
 */
public class WaitingRoom implements Screen {

    private Stage stage;
    private Skin buttonBackSkin;
    private float screenWidth;
    private float screenHeight;
    private GameInitializer gameInitializer;
    private SpriteBatch backgroundBatch;
    private SpriteBatch lobbyBackgroundBatch;
    private BackgroundRenderer backgroundRenderer;

    private ArrayList<Account> accounts = new ArrayList<>();
    private int maxPlayers;

    public WaitingRoom(GameInitializer gameInitializer){

        this.gameInitializer = gameInitializer;
        this.backgroundBatch = new SpriteBatch();
        this.lobbyBackgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        //GUI
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //LobbyBackground
        createLobbyBackground();
        //Buttons
        buttonBackSkin = createBasicButtonBackSkin();
        createBackButton();

        //Get screen width and height for future reference
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        //Logic
        //TODO depending on sort game, get maxplayers from the database
        maxPlayers = 5;
    }

    /**
     * If a player in the lobby wants to join a room, this method is called.
     * If there is enough room in the waitingroom the player will be added.
     * @param a : The Account of the player which is trying to join this room
     * @return true if te player successfully joined the room, false when it failed
     */
    public boolean joinRoom(Account a){
        if(accounts.size() >= maxPlayers){
            return false;
        }
        else{
            accounts.add(a);
            return true;
        }
    }

    /**
     * Called when a player leaves the room, either when he leaves himself or got kicked by the host.
     * @param a : The Account which will leave the room
     */
    private void leaveRoom(Account a){
        accounts.remove(a);
    }

    /**
     * Starts the game, only the host of a room can start the game.
     */
    private void startGame(){

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

    private void createLobbyBackground() {
        Texture texture = new Texture(Gdx.files.internal("MenuButtonBase.png"));
        Sprite lobbyBackgroundSprite = new Sprite(texture);
        lobbyBackgroundSprite.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        lobbyBackgroundSprite.setPosition(Gdx.graphics.getWidth() / 2 - lobbyBackgroundSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - lobbyBackgroundSprite.getHeight() / 2);
        lobbyBackgroundSprite.setCenter(Gdx.graphics.getWidth() / 2 - lobbyBackgroundSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - lobbyBackgroundSprite.getHeight() / 2);
        lobbyBackgroundSprite.setOriginCenter();
        lobbyBackgroundBatch.begin();
        lobbyBackgroundSprite.draw(lobbyBackgroundBatch);
        lobbyBackgroundBatch.end();
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
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2);
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
