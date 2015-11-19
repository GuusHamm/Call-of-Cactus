package callofcactus.maps;

import callofcactus.Game;

/**
 * Created by Teun on 19-11-2015.
 */
public abstract class Map {

    private Game game;

    public Map(Game game) {
        this.game = game;
    }

    protected Game getGame() {
        return game;
    }

    abstract void init();

}
