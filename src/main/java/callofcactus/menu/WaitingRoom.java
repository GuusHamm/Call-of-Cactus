package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.Utils;
import callofcactus.account.Account;
import callofcactus.io.DatabaseManager;
import callofcactus.io.IPReader;
import callofcactus.multiplayer.ClientS;
import callofcactus.multiplayer.lobby.ILobby;
import callofcactus.multiplayer.lobby.ILobbyListener;
import callofcactus.multiplayer.lobby.Lobby;
import callofcactus.multiplayer.serverbrowser.BrowserRoom;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Kees on 23/11/2015.
 */
public class WaitingRoom implements Screen {

    public static final boolean useLocalNetwork = true;

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
    private Table gameContainerOverlay;

    private ArrayList<Account> accounts = new ArrayList<>();

    private ILobby lobby;
    private Registry registry;
    private boolean host;
    private LobbyListener lobbyListener;

    public static final class LobbyListener extends UnicastRemoteObject implements ILobbyListener {
        private WaitingRoom room;
        private GameInitializer gameInitializer;

        public LobbyListener(WaitingRoom room, GameInitializer gameInitializer) throws RemoteException {
            this.room = room;
            this.gameInitializer = gameInitializer;
        }

        @Override
        public void onStart(String hostip) throws RemoteException {
            // TODO Join server
            System.out.println("start");

            ClientS clientS = ClientS.getInstance();
            clientS.setLobbyIp(hostip);
            System.out.println("Joining game on host: " + hostip);
            Gdx.app.postRunnable(() -> {
                gameInitializer.createNewMultiplayerGame();
                // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                gameInitializer.setScreen(new MultiPlayerGameScreen(gameInitializer, Administration.getInstance().getLocalAccount()));
            });

        }

    }

    public WaitingRoom(GameInitializer gameInitializer, String host) throws RemoteException, NotBoundException {
        try {
            lobbyListener = new LobbyListener(this, gameInitializer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (Utils.isLocalhost(host)) {
            try {
                setupLobby();
            } catch (java.net.UnknownHostException e) {
                e.printStackTrace();
            }
        }else{
            registry = LocateRegistry.getRegistry(host, Lobby.PORT);
            try {
                lobby = (ILobby) registry.lookup(Lobby.LOBBY_KEY);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                gameInitializer.setScreen(new ServerBrowserScreen(gameInitializer));
                return;
            }
            lobby.join(Administration.getInstance().getLocalAccount(), lobbyListener, new IPReader().readIP().getIp());
        }
        setup(gameInitializer);
    }

    public WaitingRoom(GameInitializer gameInitializer) throws RemoteException, AlreadyBoundException, java.net.UnknownHostException {
        try {
            lobbyListener = new LobbyListener(this, gameInitializer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setupLobby();
        setup(gameInitializer);
    }

    private void setupLobby() throws RemoteException, java.net.UnknownHostException {
        this.host = true;
        Account localAccount = Administration.getInstance().getLocalAccount();
        lobby = new Lobby(localAccount);
        String ip;

        if (useLocalNetwork) {
            ip = InetAddress.getLocalHost().getHostAddress();
        }else{
            ip = new IPReader().readIP().getIp();
        }

        lobby.join(localAccount, lobbyListener, ip);
        registry = LocateRegistry.createRegistry(Lobby.PORT);
        registry.rebind(Lobby.LOBBY_KEY, lobby);
        new Thread(() -> {
            new DatabaseManager().createRoom(new BrowserRoom(localAccount.getID(), localAccount.getUsername() + "'s lobby", ip, Administration.getInstance().getRank().getRanking()));
        }).start();
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
        if (isHost())
            createStartButton();
    }

    private void createStartButton() {
        TextButton startButton = new TextButton("Start", skin); // Use the initialized skin
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2 - startButton.getWidth(), 0/*Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2*/);
        stage.addActor(startButton);
        WaitingRoom waitingRoom = this;
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (waitingRoom.isHost())
                    new DatabaseManager().removeRoom(Administration.getInstance().getLocalAccount().getID());

                try {
                    startGame();
                } catch (IllegalAccessException | RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startGame() throws IllegalAccessException, RemoteException {
        if (!isHost())
            throw new IllegalAccessException("Cant start a game while you're not the host");

        lobby.start(gameInitializer);
    }

    public boolean isHost() {
        return host;
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
            createAllAccounts(accounts);
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
        new DatabaseManager().removeRoom(Administration.getInstance().getLocalAccount().getID());
        try {
            registry.unbind(Lobby.LOBBY_KEY);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void createAllAccounts(ArrayList<Account> allAccounts){
        gameInnerContainer.clear();
        for (Account a : allAccounts){
            Table testAccountBar = new Table();
            testAccountBar.add(new Image(new Texture(Gdx.files.internal("player.png"))));
            testAccountBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
            testAccountBar.add(new Label(a.getUsername(), skin));
            testAccountBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
            testAccountBar.background(skin.getDrawable("gameBarBackground"));

            testAccountBar.addListener(new FocusListener(){
                @Override
                public boolean handle(Event event){
                    if (event.toString().equals("mouseMoved")){
                        return false;
                    }
                    else if(event.toString().equals("exit")){
                        return false;
                    }
                    return true;
                }
            });

            gameInnerContainer.row();
            gameInnerContainer.add(testAccountBar).size(gameContainer.getWidth(), gameContainer.getHeight() / 5);
        }

    }

    private void createLobbyBackground() {
        gameContainer = new Table();
        gameContainer.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        gameContainer.setPosition(Gdx.graphics.getWidth() / 2 - gameContainer.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameContainer.getHeight() / 2);
        gameContainer.background(skin.getDrawable("listBackground"));
        stage.addActor(gameContainer);
    }

    private void createWaitingArea(){
        gameInnerContainer = new Table();
        ScrollPane gamesPane = new ScrollPane(gameInnerContainer);
        stage.addActor(gamesPane);

        // Add the scrollpane to the container
        gameContainer.add(gamesPane).fill().expand();

        // Add a border overlay to the server selection table
        gameContainerOverlay = new Table();
        gameContainerOverlay.setSize(screenWidth / 2, screenHeight / 2);
        gameContainerOverlay.setPosition(Gdx.graphics.getWidth() / 2 - gameContainer.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameContainer.getHeight() / 2);
        gameContainerOverlay.background(skin.getDrawable("listForeground"));
        stage.addActor(gameContainerOverlay);
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
        skin.add("listForeground", new Texture(Gdx.files.internal("ScrollPaneBackgroundOverlay.png")));

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
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                try {
                    lobby.leave(Administration.getInstance().getLocalAccount(), lobbyListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                dispose();
                navigateToLobby();
            }
        });
    }

    /**
     * Navigates back to the lobby.
     */
    private void navigateToLobby() {
        this.dispose();
        System.out.println("account exiting waitingroom: " + Administration.getInstance().getLocalAccount().getUsername());
        gameInitializer.setScreen(new ServerBrowserScreen(gameInitializer));
    }

}
