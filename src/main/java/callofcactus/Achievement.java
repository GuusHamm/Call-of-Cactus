package callofcactus;

import callofcactus.io.DatabaseManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nekkyou on 1-1-2016.
 */
public class Achievement
{
    private static Achievement instance = null;
    private HashMap<Integer, String> achievements;
    private DatabaseManager databaseManager = new DatabaseManager();

    public Achievement() {
        achievements = new HashMap<>();
        //Get all achievements from the database and add them to the achievements list.
    }

    public static Achievement getInstance() {
        if (instance == null) {
            instance = new Achievement();
        }
        return instance;
    }

    public boolean completeAchievement(String achievementName, int accoundId) {

        int key = 0;

        for (Map.Entry<Integer, String> entry : achievements.entrySet()) {
            if (entry.getValue().matches(achievementName)) {
                key = entry.getKey();
            }
        }
        if (key != 0) {
            //Add to database with key and account ID

            return true;
        }

        return false;
    }


}
