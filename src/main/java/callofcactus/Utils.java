package callofcactus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Teun
 */
public class Utils {

    public static String getRandomName(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();

        String alphabet = "123xyz";
        for (int i = 0; i < length; i++) {
            stringBuilder.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

    public static <T> T getRandomEntry(T[] array) {
        int random = (int) new Random().nextDouble() * array.length;
        return array[random];
    }

    public static <T> T getLastEntry(ArrayList<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T getRandomEntry(ArrayList<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> ArrayList<T> convertArray(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    public static <T> T[] convertList(List<T> list) {
        return list.toArray((T[]) new Object[list.size()]);
    }

}
