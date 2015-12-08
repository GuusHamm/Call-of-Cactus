package callofcactus.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.account.Account;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Jim on 16-11-2015.
 */
public class ServerBrowserScreen implements Screen {
    private GameInitializer gameInitializer;
    private SpriteBatch batch;
    private Stage stage;
    //GUI fields
    private Skin skin;
    private Music themeMusic;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;

    private float screenHeight;
    private float screenWidth;

    // Game list elements
    private Table gameContainer;
    private Table gameInnerContainer;

    // Account data elements
    private Table accountContainer;
    private Table statsContainer;
    //kills, score, deaths, games played
    private Label scoreLabel;
    private Label kdLabel;
    private Label gamesPlayedLabel;
    private Table scoreTable;
    private Table kdTable;
    private Table gamesPlayedTable;

    // Manual IP Connection
    private Table ipContainer;
    private TextField ipInput;
    private Button ipConnectButton;
    private Button createGameButton;

    // Account
    private Account account;

    public ServerBrowserScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.batch = gameInitializer.getBatch();
        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();

        //TODO Login
        account = new Account("TestDude");

        //GUI code
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();

        // Create the Back button
        TextButton newBackButton = new TextButton("Back", skin); // Use the initialized skin
        newBackButton.setPosition(screenWidth / 2 - screenWidth / 8, newBackButton.getHeight() + 1);
        stage.addActor(newBackButton);

        // Create a table that will contain the list of 'Join game' bars
        gameContainer = new Table();
        gameContainer.setSize(screenWidth / 2, screenHeight / 2);
        gameContainer.setPosition(screenWidth / 2, screenHeight / 2);
        gameContainer.background(skin.getDrawable("listBackground"));
        stage.addActor(gameContainer);

        // Create the inner table that will serve as the container for all "join game' bars
        this.gameInnerContainer = new Table();

        // Create 2 test 'Join game' bars
        createJoinGameButton("Jimbolul's Deathmatch");
        createJoinGameButton("GuusHamm's Team Deathmatch");

        // Create the scrollpane for selecting a game
        ScrollPane gamesPane = new ScrollPane(gameInnerContainer);
        stage.addActor(gamesPane);

        // Add the scrollpane to the container
        gameContainer.add(gamesPane).fill().expand();

        // Create a container for all account data
        accountContainer = new Table();
        accountContainer.setSize(screenWidth / 4, screenHeight / 1.5f);
        accountContainer.setPosition(0, screenHeight / 5);
        accountContainer.background(skin.getDrawable("accountBackground"));
        stage.addActor(accountContainer);

        // Set initial stat values
        CharSequence testtext1 = "Total Score: 10827";
        CharSequence testtext2 = "Kill / Death Ratio: 0.87";
        CharSequence testtext3 = "Games Played: 69";
        scoreLabel = new Label(testtext1, skin);
        kdLabel = new Label(testtext2, skin);
        gamesPlayedLabel = new Label(testtext3, skin);

        // Create a container for all account stats
        statsContainer = new Table();
        statsContainer.setPosition(screenWidth / 20, screenHeight / 5);
        accountContainer.add(statsContainer).size(screenWidth / 5, screenHeight / 2);

        // Create tables for independant stats
        scoreTable = new Table();
        scoreTable.addActor(scoreLabel);
        statsContainer.row();
        statsContainer.add(scoreTable).size(screenWidth / 5, screenHeight / 20);

        kdTable = new Table();
        kdTable.addActor(kdLabel);
        statsContainer.row();
        statsContainer.add(kdTable).size(screenWidth / 5, screenHeight / 20);

        gamesPlayedTable = new Table();
        gamesPlayedTable.addActor(gamesPlayedLabel);
        statsContainer.row();
        statsContainer.add(gamesPlayedTable).size(screenWidth / 5, screenHeight / 20);

        // Create the manual IP input
        ipContainer = new Table();
        ipInput = new TextField("Enter IP here", skin);
        ipContainer.add(ipInput).size(screenWidth / 5 , screenHeight / 15);
        ipConnectButton = new TextButton("Join", skin);
        ipContainer.add(ipConnectButton).size(screenWidth / 8, screenHeight / 15);
        createGameButton = new TextButton("Create game", skin);
        ipContainer.add(createGameButton).size(screenWidth / 8, screenHeight / 15);

        stage.addActor(ipContainer);
        ipContainer.setPosition(screenWidth / 2, screenHeight / 3);

        //Sets all the actions for all buttons
        newBackButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                navigateToMainMenu();
            }
        });

        ipConnectButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                WaitingRoom waitingRoom = null;
                try {
                    waitingRoom = new WaitingRoom(gameInitializer, ipInput.getText());
                } catch (RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }
                gameInitializer.setScreen(waitingRoom);
            }
        });

        createGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                WaitingRoom waitingRoom = null;
                try {
                    waitingRoom = new WaitingRoom(gameInitializer);
                } catch (RemoteException | AlreadyBoundException e) {
                    e.printStackTrace();
                }
                gameInitializer.setScreen(waitingRoom);
            }
        });

        ipInput.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ipInput.setText("");
            }
        });
    }

    private void navigateToMainMenu() {
        this.dispose();
        gameInitializer.setScreen(new MainMenu(gameInitializer));
    }

    private void createPreGameLobby(Account a){
        this.dispose();
        //TODO  implementation
    }

    private void joinGame(String IPAdress, Account a){
        this.dispose();
        //TODO implementation
    }

    public void createJoinGameButton(String gameName) {
        Table testGameBar = new Table();
        testGameBar.add(new Image(new Texture(Gdx.files.internal("player.png"))));
        testGameBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar.add(new Label(gameName, skin));
        testGameBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar.add(new TextButton("Join Game", skin)).size(screenWidth / 12, screenHeight / 20);
        testGameBar.background(skin.getDrawable("gameBarBackground"));

        testGameBar.addListener(new FocusListener() {
            @Override
            public boolean handle(Event event) {
                if (event.toString().equals("mouseMoved")) {
                    return false;
                } else if (event.toString().equals("exit")) {
                    return false;
                }
                return true;
            }
        });

        gameInnerContainer.row();
        gameInnerContainer.add(testGameBar).size(gameContainer.getWidth() - gameContainer.getWidth() / 20, gameContainer.getHeight() / 5);

    }

    @Override
    public void show() {
        stage.act();
        stage.draw();
    }

    @Override
    public void render(float v) {
        //GUI code
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

    private void createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
        skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));
        skin.add("listBackground", new Texture(Gdx.files.internal("ScrollPaneBackground.png")));
        skin.add("gameBarBackground", new Texture(Gdx.files.internal("lobbyGameBarBackground.png")));
        skin.add("accountBackground", new Texture(Gdx.files.internal("lobbyAccountDataBackground.png")));
        skin.add("cursor", new Texture(Gdx.files.internal("cursor.png")));

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
        //labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", labelStyle);

        // Create Textfield style
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background = skin.newDrawable("gameBarBackground");
        textFieldStyle.cursor = skin.getDrawable("cursor");
        skin.add("default", textFieldStyle);

    }
}