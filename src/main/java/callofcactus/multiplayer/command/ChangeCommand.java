package callofcactus.multiplayer.command;

import callofcactus.entities.Entity;

/**
 * Created by Teun on 12-12-2015.
 */
public class ChangeCommand extends Command {

    public ChangeCommand(Entity[] objectsToModify, objectEnum objectToChange) {
        super(methods.CHANGE, objectsToModify, objectToChange);
    }

    public ChangeCommand(Entity[] objectsToModify, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.CHANGE, objectsToModify, fieldToChange, newValue, typeOfObject);
    }

    public ChangeCommand(int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.CHANGE, ID, fieldToChange, newValue, typeOfObject);
    }
}
