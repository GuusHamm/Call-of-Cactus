package callofcactus.io;


import callofcactus.account.Account;
import callofcactus.multiplayer.serverbrowser.BrowserRoom;
import com.mysql.jdbc.Connection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Guus Hamm
 */

/**
 * Constructor of the DatabaseManager
 */
public class DatabaseManager {
    Connection connection;

    public DatabaseManager() {
        openConnection();
    }

    /**
     * @param score      you want to insert into the database
     * @param waveNumber you want to insert into the database
     * @param playerID   you want to insert into the database
     * @return if the statement has been executed without errors
     */
    public boolean addHighScore(int score, int waveNumber, int playerID) {
        String query = String.format("INSERT INTO SINGLEPLAYER(SCORE,WAVENUMBER,USERID) VALUES (%d,%d,%d);", score, waveNumber, playerID);
        return writeToDataBase(query);
    }

    public boolean addAccount(String username, String password) {
        HashMap<String, String> hashedAndSalted = salter(password, null);
        String query = String.format("INSERT INTO ACCOUNT(USERNAME,SALT,PASSWORD) VALUES (\"%s\",\"%s\",\"%s\");", username, hashedAndSalted.get("Salt"), hashedAndSalted.get("Password"));

        return writeToDataBase(query);
    }

    public boolean addAchievement(int accountID, int achievementID)
    {
        String query = String.format("INSERT INTO ACCOUNTACHIEVEMENT(ACCOUNTID,ACHIEVEMENTID) VALUES(%d,%d)", accountID, achievementID);

        return writeToDataBase(query);
    }

    public int getAccountID(String username)
    {
        String query = String.format("SELECT ID FROM ACCOUNT WHERE USERNAME = \"%s\"", username);

        ResultSet resultSet = readFromDataBase(query);
        try {
            while (resultSet.next()) {
                return resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public HashMap<Integer, String> getAllAchievements()
    {
        HashMap<Integer, String> results = new HashMap<>();

        String query = String.format("SELECT ID, NAME FROM ACHIEVEMENT ORDER BY ID");

        ResultSet resultSet = readFromDataBase(query);

        try {
            while (resultSet.next()) {
                results.put(Integer.parseInt(resultSet.getString("ID")), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public ArrayList<String> getCompletedAchievements(int accountID)
    {
        ArrayList<String> results = new ArrayList<>();

        String query = String.format("SELECT A.NAME FROM ACHIEVEMENT A JOIN ACCOUNTACHIEVEMENT AA ON (A.ID = AA.ACHIEVEMENTID) JOIN ACCOUNT AC ON (AA.ACCOUNTID = AC.ID) WHERE AC.ID = %d", accountID);

        ResultSet resultSet = readFromDataBase(query);

        try {
            while (resultSet.next()) {
                results.add(resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public int getNextGameID(){
        String query = String.format("SELECT max(ID) as \"ID\" from PLAYERMATCH");

        ResultSet resultSet = readFromDataBase(query);
        try {
            while (resultSet.next()){
                return resultSet.getInt("ID") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean addMultiplayerResult(int playerID, int matchID, int score, int kills, int deaths) {
        String query = String.format("INSERT INTO PLAYERMATCH (ACCOUNTID,MATCHID,SCORE,KILLS,DEATHS VALUES (%d,%d,%d,%d,%d);", playerID, matchID, score, kills, deaths);

        return writeToDataBase(query);
    }

    private HashMap<String, String> salter(String password, String salt) {
        HashMap<String, String> result = new HashMap<>();
        if (salt == null) {

            salt = BCrypt.gensalt();

        }

        result.put("Salt", salt);

        result.put("Password", BCrypt.hashpw(password, salt));

        return result;
    }

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();

        ResultSet resultSet = readFromTable(tableEnum.ACCOUNT);

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String username = resultSet.getString("USERNAME");
                Account account = new Account(username);
                account.setID(id);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return accounts;
    }

    public ResultSet getSortedScores(int matchID) {
        String query = String.format("SELECT USERNAME, SCORE, KILLS, DEATHS FROM PLAYERMATCH P JOIN ACCOUNT A ON (P.ACCOUNTID = A.ID) WHERE MATCHID = %d ORDER BY SCORE DESC;", matchID);

        return readFromDataBase(query);
    }

    public HashMap<String, String> getResultsOfPlayer(int playerID) {
        HashMap<String, String> results = new HashMap<>();
        String query = String.format("SELECT USERNAME,SUM(SCORE) AS SCORETOTAL,SUM(KILLS) AS KILLS, SUM(DEATHS) AS DEATHS FROM PLAYERMATCH P JOIN ACCOUNT A ON (P.ACCOUNTID = A.ID) WHERE ACCOUNTID = %d;", playerID);

        ResultSet resultSet = readFromDataBase(query);

        try {
            while (resultSet.next()) {
                results.put("Username", resultSet.getString("USERNAME"));
                results.put("TotalScore", resultSet.getString("SCORETOTAL"));
                results.put("TotalKills", resultSet.getString("KILLS"));
                results.put("TotalDeaths", resultSet.getString("DEATHS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }

    public List<BrowserRoom> getRooms() {
        String query = "SELECT * FROM ROOM";

        ArrayList<BrowserRoom> browserRooms = new ArrayList<>();
        ResultSet resultSet = readFromDataBase(query);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int hostid = resultSet.getInt("hostid");
                String name = resultSet.getString("name");
                String hostip = resultSet.getString("hostip");
                browserRooms.add(new BrowserRoom(id, hostid, name, hostip));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return browserRooms;
    }

    public boolean createRoom(BrowserRoom browserRoom) {
        removeRoom(browserRoom.getHostid());
        String query = "INSERT INTO ROOM (hostid, name, hostip) VALUES (?,?,?)";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, browserRoom.getHostid());
            preparedStatement.setString(2, browserRoom.getName());
            preparedStatement.setString(3, browserRoom.getHostip());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeRoom(int hostAccountId) {
        String query = "DELETE FROM ROOM WHERE hostid = " + hostAccountId;
        return writeToDataBase(query);
    }

    public void closeConnection() {
        if (connection == null)
            return;

        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
        try {
            this.connection =
                    (Connection) DriverManager.getConnection("jdbc:mysql://teunwillems.nl/CallOfCactus?" +
                            "user=coc&password=callofcactusgame");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnectionOpen() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyAccount(String username, String password) {
        String query = String.format("SELECT SALT FROM ACCOUNT WHERE USERNAME = \"%s\";", username);

        ResultSet resultSet = readFromDataBase(query);
        String salt = "";
        try {
            while (resultSet.next()){
                    salt = resultSet.getString("SALT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        password = salter(password,salt).get("Password");

        query = String.format("SELECT ID FROM ACCOUNT WHERE PASSWORD = \"%s\" AND USERNAME = \"%s\";", password,username);
        try {
            if (readFromDataBase(query).next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param table you want to read from
     * @return ResultSet from all the data in the desired
     */
    public ResultSet readFromTable(tableEnum table) {
        String query = String.format("SELECT * FROM %s;", table);
        return readFromDataBase(query);
    }

    /**
     * //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
     * //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
     * //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
     * //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
     * //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
     * //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
     * //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
     * //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
     * <p>
     * <p>
     * Don't even think about making this method public, I will find you.......
     * <p>
     * <p>
     * //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
     * //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
     * //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
     * //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
     * //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
     * //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
     * //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
     * //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
     *
     * @param query you want to write to the database
     * @return if the query executed successfully
     */
    private boolean writeToDataBase(String query) {
        try {
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
     * //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
     * //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
     * //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
     * //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
     * //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
     * //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
     * //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
     * <p>
     * <p>
     * Don't even think about making this method public, I will find you.......
     * <p>
     * <p>
     * //	  $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\  $$$$$$\  $$\   $$\ $$$$$$$$\
     * //	  \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|$$  __$$\ $$$\  $$ |\__$$  __|
     * //		$$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |   $$ /  $$ |$$$$\ $$ |   $$ |
     * //		$$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |   $$$$$$$$ |$$ $$\$$ |   $$ |
     * //		$$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |   $$  __$$ |$$ \$$$$ |   $$ |
     * //		$$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |\$$$ |   $$ |
     * //	  $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |   $$ |  $$ |$$ | \$$ |   $$ |
     * //	  \______|\__|     \__|\__|       \______/ \__|  \__|  \__|   \__|  \__|\__|  \__|   \__|
     *
     * @param query you want to read from the database
     * @return the ResultSet of the query
     */
    private ResultSet readFromDataBase(String query) {
        ResultSet results = null;
        try {
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            statement.execute();
            results = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * @param table from tableEnum that you want to delete stuff from
     * @return if the statement has been executed without errors
     */
    public boolean deleteFromTable(tableEnum table) {
        String query = String.format("DELETE FROM %s;", table);
        return writeToDataBase(query);
    }

    public boolean usernameExists(String username) {
        return getAccounts().stream().filter(e -> e.getUsername().equals(username)).findAny() == null;
    }

    public void changeToTestDataBase() {
        try {
            this.connection =
                    (Connection) DriverManager.getConnection("jdbc:mysql://teunwillems.nl/Test?" +
                            "user=coc&password=callofcactusgame");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean generateTestData() {
        boolean hasNotFailed = true;

        //First remove all DB Data
        String query = "SET FOREIGN_KEY_CHECKS = 0;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE ACCOUNTACHIEVEMENT;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE PLAYERMATCH;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE MULTIPLAYERMATCH;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE SINGLEPLAYER;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE ACCOUNT;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE ACHIEVEMENT;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "TRUNCATE TABLE GAMEMODE;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "SET FOREIGN_KEY_CHECKS = 1;";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }

        //Next generate 10 accounts
        addAccount("FlipDeMier", "iziPassw123");
        addAccount("HansDuo", "800815ftw");
        addAccount("Fr0d0", "H0bb1t!");
        addAccount("Jan", "321");
        addAccount("Theodore", "Geronimooo!!!111");
        addAccount("FrogLord", "TadPole");
        addAccount("REMI", "IMER");
        addAccount("KinckyKong", "1*20!@kadKasa!");
        addAccount("tntzlr", "HandtasjeDeKat");
        addAccount("Bam0wnage", "JJJoooJJJooo");

        //Last add all other test data
        query = "INSERT INTO ACHIEVEMENT (ID, NAME, DESCRIPTION) " +
                "VALUES (1,'Hot Shot','Fire 100 bullets without one of them missing their target')," +
                "(2,'Pure Nature','Win a round without using any pickups')," +
                "(3,'Alien Invasion Terror','Win a round only moving left or right')," +
                "(4,'The Big 5','Pickup 5 different power-ups in one round')," +
                "(5,'Try All, Master All','Win a round with all current roles')," +
                "(6,'Ultimate Streak, Master All','Win 10 rounds in a row');";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "INSERT INTO ACCOUNTACHIEVEMENT (ACCOUNTID, ACHIEVEMENTID) " +
                "VALUES (1,1), (1,3), (3,1), (3,4), (3,6)," +
                "(4,1), (5,2), (5,3), (5,5), (5,6), (8,1)," +
                "(8,6), (9,4), (10,1), (10,2), (10,3)," +
                "(10,4), (10,5), (10,6);";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "INSERT INTO GAMEMODE (DESCRIPTION, MAXPLAYERS, NAME) " +
                "VALUES ('Endless waves of enemys will spawn and try to destroy you, survive as long as possible and get a high score!',4,'Endless')," +
                "('Seek and kill all other players! The player with the highest score at the end wins!',8,'FreeForALl')," +
                "('Join one of two teams and kill the opposite one. You can`t damage teammates in this mode. Last team standing wins!',10,'Team VS Team');";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "INSERT INTO MULTIPLAYERMATCH (GAMEMODEID) " +
                "VALUES (1), (3), (3), (1), (2)," +
                "(1), (1), (2), (3), (2);";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "INSERT INTO PLAYERMATCH (ACCOUNTID, MATCHID, SCORE, KILLS, DEATHS) " +
                "VALUES (1, 1, 300, 5, 0)," +
                "(1, 3, 150, 10, 2)," +
                "(1, 6, 222, 12, 6)," +
                "(3, 1, 142, 2, 11)," +
                "(3, 2, 123, 9, 21)," +
                "(4, 2, 331, 5, 3)," +
                "(4, 7, 300, 3, 0)," +
                "(4, 8, 1122, 12, 0)," +
                "(5, 10, 6455, 53, 6)," +
                "(6, 3, 112, 2, 22)," +
                "(8, 4, 850, 0, 8)," +
                "(9, 5, 334, 0, 12)," +
                "(10, 8, 1122, 44, 12)," +
                "(10, 9, 2324, 20, 0)," +
                "(10, 10, 2100, 18, 4);";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        query = "INSERT INTO SINGLEPLAYER (SCORE, WAVENUMBER, ACCOUNTID) " +
                "VALUES (115, 10, 2)," +
                "(2219, 44, 2)," +
                "(11, 2, 2)," +
                "(176, 7, 4)," +
                "(450, 7, 4)," +
                "(2744, 24, 6)," +
                "(7492, 71, 7)," +
                "(177, 13, 9)," +
                "(80, 3, 9)," +
                "(2, 1, 9);";
        if (!writeToDataBase(query)) {
            hasNotFailed = false;
        }
        return hasNotFailed;

    }

    /**
     * An Enum with all the tables in the database in it.
     */
    public enum tableEnum {
        ACCOUNT,
        ACCOUNTACHIEVEMENT,
        ACHIEVEMENT,
        GAMEMODE,
        MULTIPLAYERMATCH,
        PLAYERMATCH,
        SINGLEPLAYER

    }
}


