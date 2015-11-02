package game;

import java.util.Objects;
import java.util.Random;

/**
 * @author Teun
 *
 */
public class Utils {

    public static Object getRandomObjectFromArray(Object[] objects) {
        int random = (int) new Random().nextDouble() * objects.length;
        return objects[random];
    }

}
