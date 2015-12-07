package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.account.Account;
import callofcactus.multiplayer.lobby.ILobby;
import callofcactus.multiplayer.lobby.Lobby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.event.InputEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by Kees on 23/11/2015.
 */
public class WaitingRoom implements Screen {

    private Stage stage;
    private Skin skin;
    private float screenWidth;
    private float screenHeight;
    private GameInitializer gameInitializer;
    private SpriteBatch backgroundBatch;
    private SpriteBatch lobbyBackgroundBatch;
    private BackgroundRenderer backgroundRenderer;
    private Table gameContainer;
    private Table gameInnerContainer;

    private ArrayList<Account> accounts = new ArrayList<>();
    private int maxPlayers;
    private int IPAdress;

    private ILobby lobby;
    private Registry registry;

    public WaitingRoom(GameInitializer gameInitializer, String host) throws RemoteException, NotBoundException {
        setup(gameInitializer);

        lobby = (ILobby) LocateRegistry.getRegistry(host, Lobby.PORT).lookup(Lobby.LOBBY_KEY);
    }

    public WaitingRoom(GameInitializer gameInitializer) throws RemoteException, AlreadyBoundException {
        setup(gameInitializer);

        lobby = new Lobby(Administration.getInstance().getLocalAccount());
        registry = LocateRegistry.createRegistry(Lobby.PORT);
        registry.bind(Lobby.LOBBY_KEY, lobby);
    }

    private void setup(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.backgroundBatch = new SpriteBatch();
        this.lobbyBackgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        //Get screen width and height for future reference
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        //GUI
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = createBasicSkin();
        //LobbyBackground
        createLobbyBackground();
        //Create waiting area
        createWaitingArea();
        //Buttons
        createBackButton();
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

        try {
            accounts.clear();
            accounts.addAll(lobby.getPlayers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

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
        try {
            registry.unbind(Lobby.LOBBY_KEY);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void createLobbyBackground() {
        gameContainer = new Table();
        gameContainer.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        gameContainer.setPosition(Gdx.graphics.getWidth() / 2 - gameContainer.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameContainer.getHeight() / 2);
        gameContainer.background(skin.getDrawable("hoverImage"));
        stage.addActor(gameContainer);
    }

    private void createWaitingArea(){
        gameInnerContainer = new Table();
        ScrollPane gamesPane = new ScrollPane(gameInnerContainer);
        stage.addActor(gamesPane);

        // Add the scrollpane to the container
        gameContainer.add(gamesPane).fill().expand();
    }

    private Skin createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
        skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));
        skin.add("listBackground", new Texture(Gdx.files.internal("ScrollPaneBackground.png")));
        skin.add("gameBarBackground", new Texture(Gdx.files.internal("lobbyGameBarBackground.png")));
        skin.add("accountBackground", new Texture(Gdx.files.internal("lobbyAccountDataBackground.png")));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("image");
        textButtonStyle.down = skin.newDrawable("hoverImage");
        textButtonStyle.checked = skin.newDrawable("hoverImage");
        textButtonStyle.over = skin.newDrawable("hoverImage");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        skin.add("default", labelStyle);
        return skin;
    }

    /**
     * Create a button which, when clicked, will return to the lobby.
     */
    private void createBackButton() {
        TextButton backButton = new TextButton("Back", skin); // Use the initialized skin
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, 0/*Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2*/);
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
        gameInitializer.setScreen(new ServerBrowserScreen(gameInitializer));
    }

}
