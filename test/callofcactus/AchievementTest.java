package callofcactus;

import callofcactus.io.DatabaseManager;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Kees on 18/01/2016.
 */
public class AchievementTest extends BaseTest{

    Achievement achievement;
    DatabaseManager databaseManager;

    @BeforeClass
    public void setUp() throws Exception {
        achievement = Achievement.getInstance();
        databaseManager = new DatabaseManager();
        databaseManager.changeToTestDataBase();

    }

    @Test
    public void testCompleteAchievement() {

        databaseManager.addAccount("KlaasMinder", "43211234");
        int accID = databaseManager.getAccountID("KlaasMinder");

        Assert.assertTrue("Achievement not correctly completed.", achievement.completeAchievement("The Big 5", accID));

        boolean saveComplete = false;
        for (String s : databaseManager.getCompletedAchievements(accID)){
            if (s.equals("The Big 5")){
                saveComplete = true;
            }
        }

        Assert.assertTrue("Completed achievement not saved in database.", saveComplete);
    }

    @Test
    public void testCompleteAchievement_DoubleComplete() {

        databaseManager.addAccount("DitIsEenTestAccount", "WW");
        int accID = databaseManager.getAccountID("DitIsEenTestAccount");

        Assert.assertTrue("Achievement not correctly completed.", achievement.completeAchievement("Hot Shot", accID));
        Assert.assertFalse("Achievement added to the database, this should not happen.", achievement.completeAchievement("Hot Shot", accID));
    }
}
