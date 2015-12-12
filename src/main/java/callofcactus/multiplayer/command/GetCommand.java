package callofcactus.multiplayer.command;

import callofcactus.entities.Entity;

/**
 * Created by Teun on 12-12-2015.
 */
public class GetCommand extends Command {

    public GetCommand(int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.GET, ID, fieldToChange, newValue, typeOfObject);
    }

    public GetCommand(Entity[] objectsToModify, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.GET, objectsToModify, fieldToChange, newValue, typeOfObject);
    }

    public GetCommand(Entity[] objectsToModify, objectEnum objectToChange) {
        super(methods.GET, objectsToModify, objectToChange);
    }
}
