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

    /**
     * Gets all achievements from the database and puts it in the hashmap.
     */
    private Achievement() {
        achievements = new HashMap<>();
        //TODO Get all achievements from the database and add them to the achievements list.
    }

    /**
     * Get the instance of the Achievement class (Singleton)
     * @return
     */
    public static Achievement getInstance() {
        if (instance == null) {
            instance = new Achievement();
        }
        return instance;
    }

    /**
     * Congratulations, you now have an achievement.
     * @param achievementName The name of the achievement
     * @param accoundId         The ID of the account, so we can add it to the right person
     * @return
     */
    public boolean completeAchievement(String achievementName, int accoundId) {

        int key = 0;

        for (Map.Entry<Integer, String> entry : achievements.entrySet()) {
            if (entry.getValue().matches(achievementName)) {
                key = entry.getKey();
            }
        }
        if (key != 0) {
            //TODO Add to database with key and account ID

            return true;
        }

        return false;
    }

    public HashMap<Integer, String> getCompletedAchievements(int accountID) {
        //TODO get all completed achievements from the database with the Account id
        return null;
    }


}
