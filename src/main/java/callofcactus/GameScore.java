package callofcactus;

/**
 * Created by Teun on 4-1-2016.
 */
public class GameScore {

    private String username;
    private int kills, deaths;
    private int score;

    public GameScore(String username, int kills, int deaths, int score) {
        this.username = username;
        this.kills = kills;
        this.deaths = deaths;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getKills() {
        return String.valueOf(kills);
    }

    public String getDeaths() {
        return String.valueOf(deaths);
    }

    public String getScore() {
        return String.valueOf(score);
    }
}
