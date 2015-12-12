package callofcactus.multiplayer.command;

import callofcactus.entities.Entity;

/**
 * Created by Teun on 12-12-2015.
 */
public class FailCommand extends Command {

    public FailCommand(Entity[] objectsToModify, objectEnum objectToChange) {
        super(methods.FAIL, objectsToModify, objectToChange);
    }

    public FailCommand(Entity[] objectsToModify, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.FAIL, objectsToModify, fieldToChange, newValue, typeOfObject);
    }

    public FailCommand(int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.FAIL, ID, fieldToChange, newValue, typeOfObject);
    }
}
