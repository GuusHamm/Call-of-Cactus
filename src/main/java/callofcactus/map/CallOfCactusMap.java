package callofcactus.map;

import callofcactus.Game;

public abstract class CallOfCactusMap {

    private Game game;

    public CallOfCactusMap(Game game) {
        this.game = game;
    }

    public abstract void init();

}
