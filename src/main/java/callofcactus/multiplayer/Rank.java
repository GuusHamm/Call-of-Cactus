package callofcactus.multiplayer;

/**
 * Created by Jim on 11-1-2016.
 */
public class Rank {
    private int score;
    private int ranking = 0;

    public Rank(int score){
        this.score = score;
        calculateRanking();
    }

    public void calculateRanking(){
        if(score < 100){
            ranking = 1;
        }
        else if(score > 100 && score < 250){
            ranking = 2;
        }
        else if(score > 250 && score < 500){
            ranking = 3;
        }
        else if(score > 500 && score < 1000){
            ranking = 4;
        }
        else if(score > 1000){
            ranking = 5;
        }
    }

    public int getRanking(){
        return ranking;
    }
}
