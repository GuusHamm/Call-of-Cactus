package callofcactus.account;

import callofcactus.IGame;
import callofcactus.io.DatabaseManager;

import java.io.Serializable;

public class Account implements Serializable {
    private int ID;
    private String username;
    private int killCount = 0;
    private int deathCount = 0;
    private int score = 0;
    private int killToBecomeBoss;
    private boolean canBecomeBoss = true;
    private boolean isDead = false;

    /**
     * creates a new callofcactus.account with the following parameters:
     *
     * @param username : The name that will be displayed, this will be used to log in. must be unique
     */
    public Account(String username) {
        this.username = username;
        killToBecomeBoss = 1;
    }

    public static Account getAccount(String username) {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.getAccounts().stream().filter(e -> e.getUsername().equals(username)).findFirst().get();
    }

    public static Account getAccount(int id) {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.getAccounts().stream().filter(e -> e.getID() == id).findFirst().get();
    }

    /**
     * This function is called to verify an callofcactus.account.
     * It is used as a login function.
     *
     * @param username : The name that will be displayed, this will be used to log in. a username must be unique
     * @param password : The password that the user chose to login.
     * @return the callofcactus.account which matches the given username and password or null if none match
     */
    public static boolean verifyAccount(String username, String password) {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.verifyAccount(username, password);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int kills) {
        if (kills == 0) {
            return;
        }
        killCount = kills;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setDeathCount(int deaths) {
        if (deaths == 0) {
            return;
        }
        deathCount = deaths;
    }

    public void raiseKillCount() {
        killCount++;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void raiseDeathCount() {
        deathCount++;
    }

    public int getKillToBecomeBoss() {
        return killToBecomeBoss;
    }

    public void setKillToBecomeBoss() {
        killToBecomeBoss = killCount + 10;
    }

    public boolean getCanBecomeBoss() {
        return canBecomeBoss;
    }

    public void setCanBecomeBoss(boolean value) {
        canBecomeBoss = value;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean value) {
        isDead = value;
    }

    /**
     * When the game is over, reset the kills and deaths for the next mach.
     */
    public void resetKillDeath() {
        killCount = 0;
        deathCount = 0;
        score = 0;
        killToBecomeBoss = 1;
    }

    /**
     * This function is used to add a callofcactus.account to a callofcactus
     *
     * @param game : The callofcactus which the callofcactus.account tries to join
     * @return true when success, false when failed
     */
    public boolean joinGame(IGame game) {
        // TODO - implement Account.joinGame
        // TODO - check if callofcactus is active
        throw new UnsupportedOperationException();
    }

    /**
     * Exits the player from his current callofcactus
     */
    public void exitGame() {
        // TODO - implement Account.exitGame
        // TODO - check if callofcactus is running
        throw new UnsupportedOperationException();
    }

    public String getUsername() {
        return username;
    }
}