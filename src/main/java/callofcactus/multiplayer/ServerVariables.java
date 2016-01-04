package callofcactus.multiplayer;

/**
 * Created by Wouter Vanmulken on 4-1-2016.
 */
public class ServerVariables {

    static boolean shouldServerStop=false;

    public static void setShouldServerStop(boolean shouldServerStop) {
        ServerVariables.shouldServerStop = shouldServerStop;
    }

    public static boolean getShouldServerStop() {

        return shouldServerStop;
    }
}
