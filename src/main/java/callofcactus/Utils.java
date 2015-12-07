package callofcactus;

import java.util.Random;

/**
 * @author Teun
 */
public class Utils {

    public static <T> T getRandomObjectFromArray(T[] objects) {
        int random = (int) new Random().nextDouble() * objects.length;
        return objects[random];
    }

}
