package callofcactus.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nino Vrijman
 */
public class ScoreBoardDialog extends Dialog
{
    public ScoreBoardDialog(String title, Skin skin, HashMap<String, Integer> nameScore)
    {
        super(title, skin);

        for (Map.Entry<String, Integer> entry : nameScore.entrySet()) {
            text(entry.getKey() + " Score: " + entry.getValue());
        }
    }

    public ScoreBoardDialog(String title, Skin skin, String windowStyleName)
    {
        super(title, skin, windowStyleName);
    }

    public ScoreBoardDialog(String title, WindowStyle windowStyle)
    {
        super(title, windowStyle);
    }
}
