package callofcactus.account;

import java.util.Comparator;

/**
 * Created by Teun on 26-1-2016.
 */
public class PlayerScore implements Comparator<PlayerScore>, Comparable<PlayerScore> {

    private int score;
    private String username;

    public PlayerScore(String username, int score) {
        this.score = score;
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int compare(PlayerScore o1, PlayerScore o2) {
        return o2.getScore() - o1.getScore();
    }

    @Override
    public int compareTo(PlayerScore o) {
        return compare(this, o);
    }
}
