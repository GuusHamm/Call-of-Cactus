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

    public static String getRandomName(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();

        String alphabet = "123xyz";
        for (int i = 0; i < length; i++) {
            stringBuilder.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

}
