package callofcactus.multiplayer.command;

import callofcactus.entities.Entity;

/**
 * Created by Teun on 12-12-2015.
 */
public class CommandFactory {

    public static Command createCommand(Command.methods method, Entity[] objectsToModify, String fieldToChange, String newValue, Command.objectEnum typeOfObject) {
        Command c = null;
        if (method == Command.methods.CHANGE) {
            c = new ChangeCommand(objectsToModify, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.FAIL) {
            c = new FailCommand(objectsToModify, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.GET) {
            c = new GetCommand(objectsToModify, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.POST) {
            c = new PostCommand(objectsToModify, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.SUCCES) {
            c = new SuccesCommand(objectsToModify, fieldToChange, newValue, typeOfObject);
        }
        return c;
    }

    public static Command createCommand(Command.methods method, int id, String fieldToChange, String newValue, Command.objectEnum typeOfObject) {
        Command c = null;
        if (method == Command.methods.CHANGE) {
            c = new ChangeCommand(id, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.FAIL) {
            c = new FailCommand(id, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.GET) {
            c = new GetCommand(id, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.POST) {
            c = new PostCommand(id, fieldToChange, newValue, typeOfObject);
        }else if (method == Command.methods.SUCCES) {
            c = new SuccesCommand(id, fieldToChange, newValue, typeOfObject);
        }
        return c;
    }

}
