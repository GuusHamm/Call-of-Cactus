package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.GameTexture;
import callofcactus.account.Account;
import callofcactus.account.PlayerAvatar;
import callofcactus.account.PlayerScore;
import callofcactus.multiplayer.Rank;
import callofcactus.multiplayer.serverbrowser.BrowserRoom;
import callofcactus.multiplayer.serverbrowser.ServerBrowser;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Jim on 16-11-2015.
 */
public class ServerBrowserScreen implements Screen {

    public static final long REFRESH = 5000;

    private GameInitializer gameInitializer;
    private ServerBrowser serverBrowser;
    private Timer timer;

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
    private Table gameContainerOverlay;

    // Account data elements
    private Table accountContainer;
    private Table statsContainer;
    private Account account;


    //kills, score, deaths, games played
    private Label scoreLabel;
    private Label kdLabel;
//    private Label gamesPlayedLabel;
    private Label nameLabel;
    private ImageButton avatarImage;
    private Table scoreTable;
    private Table kdTable;
    private Table gamesPlayedTable;
    private Table nameTable;

    // Manual IP Connection
    private Table ipContainer;
//    private TextField ipInput;
//    private Button ipConnectButton;
    private Button createGameButton;
    private Button refreshButton;

    // Leaderboard stuff
    private Table leaderboardContainer;


    private ImageButton.ImageButtonStyle imageButtonStyle;

    public ServerBrowserScreen(GameInitializer gameInitializer) {
        this.account = Administration.getInstance().getLocalAccount();
        this.serverBrowser = new ServerBrowser();
        this.gameInitializer = gameInitializer;
        this.batch = gameInitializer.getBatch();
        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();

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

        // Create the scrollpane for selecting a game
        ScrollPane gamesPane = new ScrollPane(gameInnerContainer);
        stage.addActor(gamesPane);

        // Add the scrollpane to the container
        gameContainer.add(gamesPane).fill().expand();

        // Add a border overlay to the server selection table
        gameContainerOverlay = new Table();
        gameContainerOverlay.setSize(screenWidth / 2, screenHeight / 2);
        gameContainerOverlay.setPosition(screenWidth / 2, screenHeight / 2);
        gameContainerOverlay.background(skin.getDrawable("listForeground"));
        stage.addActor(gameContainerOverlay);

        // Create a container for all account data
        accountContainer = new Table();
        accountContainer.setSize(screenWidth / 4, screenHeight / 1.5f);
        accountContainer.setPosition(0, screenHeight / 5);
        accountContainer.background(skin.getDrawable("accountBackground"));
        stage.addActor(accountContainer);

        // set initial stat values
        Administration a = Administration.getInstance();
        a.setRank(new Rank(Integer.parseInt(a.getTotalScore())));
        CharSequence testtext1 = "Total Score: " + a.getTotalScore() + " (Rank " + a.getRank().getRanking() + ")";
        CharSequence testtext2 = "Kill / Death Ratio: " + calculateKDRatio(a.getTotalKills(),a.getTotalDeaths());
//        CharSequence testtext3 = "Games Played: " + a.getTotalGamesPlayed();
        CharSequence usernameText = "Username: " + a.getLocalAccount().getUsername();

        scoreLabel = new Label(testtext1, skin);
        kdLabel = new Label(testtext2, skin);
//        gamesPlayedLabel = new Label(testtext3, skin);
        nameLabel = new Label(usernameText,skin);
        avatarImage = new ImageButton(account.getAvatar().getSpriteDrawable());
        avatarImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayerAvatar a = account.getAvatar();
                avatarImage.getImage().setDrawable(a.next().getSpriteDrawable());
                super.clicked(event, x, y);
            }
        });

        // Create a container for all account stats
        statsContainer = new Table();
        statsContainer.setPosition(screenWidth / 20, screenHeight / 5);
        accountContainer.add(statsContainer).size(screenWidth / 5, screenHeight / 2);

        // Create tables for independant stats
        nameTable = new Table();
        nameTable.addActor(nameLabel);
        statsContainer.row();
        statsContainer.add(avatarImage);
        statsContainer.row();
        statsContainer.add(nameTable).size(screenWidth / 5, screenHeight / 20);

        scoreTable = new Table();
        scoreTable.addActor(scoreLabel);
        statsContainer.row();
        statsContainer.add(scoreTable).size(screenWidth / 5, screenHeight / 20);

        kdTable = new Table();
        kdTable.addActor(kdLabel);
        statsContainer.row();
        statsContainer.add(kdTable).size(screenWidth / 5, screenHeight / 20);

//        gamesPlayedTable = new Table();
//        gamesPlayedTable.addActor(gamesPlayedLabel);
//        statsContainer.row();
//        statsContainer.add(gamesPlayedTable).size(screenWidth / 5, screenHeight / 20);

        // Create the manual IP input
        ipContainer = new Table();
//        ipInput = new TextField("Enter IP here", skin);
//        ipContainer.add(ipInput).size(screenWidth / 5, screenHeight / 15);
//        ipConnectButton = new TextButton("Join", skin);
//        ipContainer.add(ipConnectButton).size(screenWidth / 8, screenHeight / 15);
        createGameButton = new TextButton("Create game", skin);
        ipContainer.add(createGameButton).size(screenWidth / 8, screenHeight / 15);

        refreshButton = new ImageButton(imageButtonStyle);
        ipContainer.add(refreshButton).size(screenWidth / 8, screenHeight / 15);

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

//        ipConnectButton.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
//                sound.play(0.3f);
//                WaitingRoom waitingRoom = null;
//                try {
//                    waitingRoom = new WaitingRoom(gameInitializer, ipInput.getText());
//                } catch (RemoteException | NotBoundException e) {
//                    e.printStackTrace();
//                }
//                gameInitializer.setScreen(waitingRoom);
//            }
//        });

        createGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                WaitingRoom waitingRoom = null;
                try {
                    waitingRoom = new WaitingRoom(gameInitializer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                gameInitializer.setScreen(waitingRoom);
            }
        });

//        ipInput.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                ipInput.setText("");
//            }
//        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshRooms();

                super.clicked(event, x, y);
            }
        });

        System.out.println("Account entering server browser: " + Administration.getInstance().getLocalAccount().getUsername());

        createLeaderboard();

        refreshRooms();
    }

    private double calculateKDRatio(String kills, String deaths){
        if (kills == null || deaths == null)
            return 0.0;

        int killsInt = Integer.parseInt(kills);
        int deathsInt =  Integer.parseInt(deaths);
        if(deathsInt <= 0){
            return killsInt;
        }
        else{
            return killsInt / deathsInt;
        }
    }
    private void navigateToMainMenu() {
        this.dispose();
        gameInitializer.setScreen(new MainMenu(gameInitializer, account));
    }


    public void refreshRooms() {
        serverBrowser.retrieveRooms(browserRooms -> {
            gameInnerContainer.clear();
            browserRooms.forEach(this::createJoinGameButton);
        });
    }

    public void createLeaderboard(){
        leaderboardContainer = new Table();
        leaderboardContainer.setBackground(skin.getDrawable("topRankingBackground"));
        leaderboardContainer.setSize(screenWidth / 7, screenHeight / 2);
        leaderboardContainer.setPosition((screenWidth / 2) - (screenWidth / 7), screenHeight / 2);
        stage.addActor(leaderboardContainer);


        Table leaderboard = new Table();
        leaderboard.setSize(screenWidth / 8, screenHeight / 3f);

        //De kolommen die in deze database functie worden aangeroepen bestaan niet.
        ArrayList<PlayerScore> rankings = Administration.getInstance().getDatabaseManager().getSortedScoresOfPlayer();

        int rank = 1;
        for(PlayerScore entry : rankings){
            if(rank<11){
                int score = entry.getScore();
                String name = entry.getUsername();
                createLeader(leaderboard, rank, name, score);
                rank++;
            }
            //System.out.println(entry.getKey() + " " + entry.getValue());
        }

//        createLeader(leaderboard,1,"Smitty W.", 1000);
//        createLeader(leaderboard,2,"Master Chef", 800);
//        createLeader(leaderboard,3,"Hans Worst", 300);
//        createLeader(leaderboard,4,"Jo Moamar", 100);
//        createLeader(leaderboard,5,"Test1", 99);
//        createLeader(leaderboard,6,"Test2", 98);
//        createLeader(leaderboard,7,"Test2", 97);
//        createLeader(leaderboard,8,"Test3", 96);
//        createLeader(leaderboard,9,"Test4", 60);
//        createLeader(leaderboard,10,"Test5", 47);
        stage.addActor(leaderboard);
        leaderboardContainer.add(leaderboard);

    }

    /**
     * Create a table that will be placed in the container table @leaderboard.
     * The created table contains 3 sub-tables: 1 for the rank, 1 for the name and 1 for the score.
     * If the rank is either 1, 2 or 3, a special background will be set for the table instead of placing a label inside.
     * The background image will display the rank instead.
     * @param leaderboard
     * @param globalRank
     * @param name
     * @param score
     */
    public void createLeader(Table leaderboard, int globalRank, String name, int score){
        Table rankTable = new Table();
        Table usernameTable = new Table();
        Table scoreTable = new Table();
        Table spacer1 = new Table();
        Table spacer2 = new Table();

        Table rowInLeaderboard = new Table();


        if(globalRank <= 3){
            switch(globalRank){
                case 1:
                    rankTable.setBackground(skin.getDrawable("rank1Image"));
                    break;
                case 2:
                    rankTable.setBackground(skin.getDrawable("rank2Image"));
                    break;
                case 3:
                    rankTable.setBackground(skin.getDrawable("rank3Image"));
                    break;
            }
        }
        else{
            rankTable.add(new Label(globalRank+"",skin));
        }

        rowInLeaderboard.add(rankTable).size(screenWidth/60,screenHeight/40);
        rowInLeaderboard.add(spacer1).fill().expand();
        rowInLeaderboard.add(usernameTable).size(screenWidth/15,screenHeight/34);
        rowInLeaderboard.add(spacer2).fill().expand();
        rowInLeaderboard.add(scoreTable).size(screenWidth/50,screenHeight/34);


        usernameTable.add(new Label(name, skin));
        scoreTable.add(new Label(score + "", skin));

        leaderboard.row();
        leaderboard.add(rowInLeaderboard);
    }

    public void createJoinGameButton(BrowserRoom room) {
        Table testGameBar = new Table();
        testGameBar.add(new Image(new Texture(Gdx.files.internal("player.png"))));
        testGameBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar.add(new Label("(Host rank: " + room.getRanking() + ") " + room.getName(), skin));
        testGameBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        TextButton textButton = new TextButton("Join Game", skin);
        textButton.setVisible(false);
        if(Administration.getInstance().getRank().getRanking() == room.getRanking() || Administration.getInstance().getRank().getRanking() + 1 == room.getRanking()){
            textButton.setVisible(true);
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    WaitingRoom waitingRoom;
                    try {
                        waitingRoom = new WaitingRoom(gameInitializer, room.getHostip());
                    } catch (RemoteException | NotBoundException e) {
                        e.printStackTrace();
                        return;
                    }
                    gameInitializer.setScreen(waitingRoom);

                }
            });
        }

        testGameBar.background(skin.getDrawable("gameBarBackground"));
        testGameBar.add(textButton).size(screenWidth / 12, screenHeight / 20);

        testGameBar.addListener(new FocusListener() {
            @Override
            public boolean handle(Event event) {
                if (event.toString().equals("mouseMoved") || event.toString().equals("exit")) {
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
        skin.add("listForeground", new Texture(Gdx.files.internal("ScrollPaneBackgroundOverlay.png")));
        skin.add("topRankingBackground", new Texture(Gdx.files.internal("TopRankingBackground.png")));
        skin.add("rank1Image", new Texture(Gdx.files.internal("Rank1.png")));
        skin.add("rank2Image", new Texture(Gdx.files.internal("Rank2.png")));
        skin.add("rank3Image", new Texture(Gdx.files.internal("Rank3.png")));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("image");
        textButtonStyle.down = skin.newDrawable("hoverImage");
        textButtonStyle.checked = skin.newDrawable("hoverImage");
        textButtonStyle.over = skin.newDrawable("hoverImage");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        SpriteDrawable icon = new SpriteDrawable(new Sprite(GameTexture.getInstance().getTexture(GameTexture.texturesEnum.refresh_icon)));
        SpriteDrawable background = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("MenuButtonBase.png"))));
        SpriteDrawable backgroundHover = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("MenuButtonBaseHover.png"))));
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = background;
        imageButtonStyle.down = background;
        imageButtonStyle.checked = background;
        imageButtonStyle.over = backgroundHover;
        imageButtonStyle.checkedOver = backgroundHover;
        imageButtonStyle.imageUp = icon;
        imageButtonStyle.imageDown = icon;
        imageButtonStyle.imageChecked = icon;

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