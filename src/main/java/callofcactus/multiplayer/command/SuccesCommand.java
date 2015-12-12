package callofcactus.multiplayer.command;

import callofcactus.entities.Entity;

/**
 * Created by Teun on 12-12-2015.
 */
public class SuccesCommand extends Command {

    public SuccesCommand(Entity[] objectsToModify, objectEnum objectToChange) {
        super(methods.SUCCES, objectsToModify, objectToChange);
    }

    public SuccesCommand(Entity[] objectsToModify, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.SUCCES, objectsToModify, fieldToChange, newValue, typeOfObject);
    }

    public SuccesCommand(int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.SUCCES, ID, fieldToChange, newValue, typeOfObject);
    }

    @Override
    public methods getMethod() {
        return methods.SUCCES;
    }
}
