package callofcactus.multiplayer.command;

import callofcactus.entities.Entity;

/**
 * Created by Teun on 12-12-2015.
 */
public class PostCommand extends Command {

    public PostCommand(Entity[] objectsToModify, objectEnum objectToChange) {
        super(methods.POST, objectsToModify, objectToChange);
    }

    public PostCommand(Entity[] objectsToModify, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.POST, objectsToModify, fieldToChange, newValue, typeOfObject);
    }

    public PostCommand(int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        super(methods.POST, ID, fieldToChange, newValue, typeOfObject);
    }

    @Override
    public methods getMethod() {
        return methods.POST;
    }
}
