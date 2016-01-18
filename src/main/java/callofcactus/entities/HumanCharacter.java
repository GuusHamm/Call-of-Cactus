package callofcactus.entities;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.account.Account;
import callofcactus.multiplayer.Command;
import callofcactus.multiplayer.ServerS;
import callofcactus.role.Boss;
import callofcactus.role.Role;
import com.badlogic.gdx.math.Vector2;

public class HumanCharacter extends Player implements Comparable {

    private int score;
    private int killCount;
    private int deathCount;


    /**
     * @param game          : The callofcactus of which the entity belongs to
     * @param location      : Coordinates of the entity
     * @param name          : The name that will be displayed in callofcactus
     * @param role          : The role that the player will play as, different roles have different stats
     * @param spriteHeight  The height of characters sprite
     * @param spriteTexture callofcactus.Texture to use for this AI
     * @param spriteWidth   The width of characters sprite
     */
    public HumanCharacter(IGame game, Vector2 location, String name, Role role, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer) {
        super(game, location, name, role, spriteTexture, spriteWidth, spriteHeight, fromServer, null);
        score = 0;
        killCount = 0;
        deathCount = 0;
    }

    public HumanCharacter(IGame game, Vector2 location, String name, Role role, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer, Account account) {
        super(game, location, name, role, spriteTexture, spriteWidth, spriteHeight, fromServer, account);
        score = 0;
        killCount = account.getKillCount();
        deathCount = account.getDeathCount();
    }

    /**
     * @return Current Score of this HumanCharacter
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
        if (account != null) {
            account.setScore(score);
        }
    }

    /**
     * @return the amount of players you killed
     */
    public int getKillCount()
    {
        return killCount;
    }

    public void setKillCount(int kills)
    {
        this.killCount = kills;
        if (account != null) {
            account.setKillCount(kills);
        }
    }

    /**
     * @return the amount of times you died
     */
    public int getDeathCount()
    {
        return deathCount;
    }

    public void setDeathCount(int deaths)
    {
        this.deathCount = deaths;
        if (account != null) {
            account.setDeathCount(deaths);
        }
    }

    /**
     * When you killed an enemy, raise the killCount variable
     */
    public void addKill(boolean shouldSend)
    {
        killCount++;
        if (account != null) {
            account.raiseKillCount();
            if (shouldSend) {
                sendChangeCommand(this, "killCount", account.getKillCount() + "", Command.objectEnum.HumanCharacter);
                addScore(1, shouldSend);
            }
        }
        else {
            System.out.println("Account is null");
        }
    }

    /**
     * When you die, raise the deathCount variable
     */
    public void addDeath(boolean shouldSend)
    {
        deathCount++;
        if (account != null) {
            account.raiseDeathCount();
            if (shouldSend) {
                sendChangeCommand(this, "deathCount", account.getDeathCount() + "", Command.objectEnum.HumanCharacter);
            }
        }

    }

    /**
     * Called when a player earns points.
     * The given value will be added to the total score of the player.
     *
     * @param score : Value that will be added to the total score of this player
     */
    public void addScore(int score, boolean shouldSend) {
        this.score += score;
        if (account != null) {
            account.setScore(account.getScore() + score);
            if(shouldSend) {
                sendChangeCommand(this, "score", this.score + "", Command.objectEnum.HumanCharacter);
            }
        }

    }

    @Override
    /**
     * Moves the entity towards a specific point
     * @param Point : Coordinates of where the object will move to
     */
    public void move(Vector2 Point, boolean shouldSend) {

        Vector2 calculateNewPosition = Administration.getInstance().calculateNewPosition(this.location, Point, speed);

        if (calculateNewPosition.x < 0) {
            calculateNewPosition.x = 0;
        }
        if (calculateNewPosition.y < 0) {
            calculateNewPosition.y = 0;
        }
        location = calculateNewPosition;

        Float a = location.x;
        Float b = location.y;
        Command.objectEnum c = Command.objectEnum.HumanCharacter;
        if(shouldSend) {
            sendChangeCommand(this, "location", location.x + ";" + location.y, Command.objectEnum.HumanCharacter);
        }
    }

    public void respawn(boolean shouldSend) {
//        isDead = false;
//
//        this.setHealth((int) (100 * getRole().getHealthMultiplier()), shouldSend);
//        try
//        {
//            setLocation(game.generateSpawn(), true);
//        }
//        catch (NoValidSpawnException e)
//        {
//            setLocation(new Vector2(100, 100), true);
//        }
    }

    public void becomeBoss() {
        changeRole(new Boss());
        try {
            Command c = new Command(this.getID(), "role", "Boss", Command.objectEnum.HumanCharacter);
            ServerS.getInstance().sendMessagePush(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int compareTo(Object o)
    {
        HumanCharacter compareHC = (HumanCharacter)o;
        if (this.score == compareHC.score) {
            return 0;
        }
        else if (this.score < compareHC.score) {
            return 1;
        }
        else {
            return -1;
        }
    }
}