package callofcactus.multiplayer.command;


import callofcactus.entities.Entity;
import callofcactus.multiplayer.Serializer;
import org.json.JSONObject;

/**
 * Created by Wouter Vanmulken on 29-11-2015.
 */
public abstract class Command {

    /**
     * Tells which objects this command will need to change.
     */
    private objectEnum objectToChange;
    private methods method;
    private Entity[] objects;
    private String fieldToChange = "";
    private Object newValue = "";
    private int ID;

    /**
     * Constructor for the Command class for a GET Command
     *
     * @param method
     * @param objectsToModify
     */
    public Command(methods method, Entity[] objectsToModify, objectEnum objectToChange) {
        this.method = method;
        this.objects = objectsToModify;
        this.objectToChange = objectToChange;

    }

    /**
     * Constructor for the Command class for a POST or CHANGE Command
     *
     * @param method
     * @param objectsToModify
     */
    public Command(methods method, Entity[] objectsToModify, String fieldToChange, String newValue, objectEnum typeOfObject) {
        this.method = method;
        this.objects = objectsToModify;
        this.fieldToChange = fieldToChange;
        this.newValue = newValue;
        this.objectToChange = typeOfObject;
    }

    /**
     * Constructor for the Command class for a CHANGE Command
     */
    public Command(int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        this.method = methods.CHANGE;
        this.ID = ID;
        this.fieldToChange = fieldToChange;
        this.newValue = newValue;
        this.objectToChange = typeOfObject;
    }

    /**
     * Constructor for the Command class for a CHANGE Command
     */
    public Command(methods method, int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        this.method = method;
        this.ID = ID;
        this.fieldToChange = fieldToChange;
        this.newValue = newValue;
        this.objectToChange = typeOfObject;
    }

    /**
     * Decodes the Command object from a string
     *
     * @param input
     * @return
     */
    public static Command fromString(String input) {

        JSONObject obj = new JSONObject(input);

        Object method = obj.get("method");
        Object value = obj.get("value");
        Object objectsToChange = obj.get("objectsToChange");

        Object field = null;
        Object newValue = null;
        Object ID = null;

        if (obj.has("field")) {
            field = obj.get("field");
            newValue = obj.get("newValue");
        }
        if (obj.has("")) {
            ID = obj.get("ID");
        }

        Command c;
        Entity[] objectValues = new Serializer().deserialeDesiredObjects64(value.toString());

        objectEnum typeOfObject = objectEnum.valueOf(objectsToChange.toString());
        methods methodEnum = methods.valueOf(method.toString());
        if (field != null) {
            c = CommandFactory.createCommand(methodEnum, objectValues, field.toString(), newValue.toString(), typeOfObject);
        } else if (ID == null) {
            c = CommandFactory.createCommand(methodEnum, objectValues, field.toString(), newValue.toString(), typeOfObject);
        }else{
            int parsedID = Integer.parseInt(ID.toString());
            c = CommandFactory.createCommand(methodEnum, parsedID, field.toString(), newValue.toString(), typeOfObject);
        }
        return c;
    }

    public int getID() {
        return ID;
    }

    public objectEnum getObjectToChange() {
        return objectToChange;
    }

    public methods getMethod() {
        return this.method;
    }

    public Object[] getObjects() {
        return objects;
    }

    public String getFieldToChange() {
        return fieldToChange;
    }

    public Object getNewValue() {
        return newValue;
    }

    /**
     * Encodes the Command object to a string that can later be decoded with Command.fromString()
     *
     * @return
     */
    @Override
    public String toString() {

        JSONObject obj = new JSONObject();
        obj.put("method", method.toString());
        obj.put("value", new Serializer().serialeDesiredObjects64(objects));
        obj.put("objectsToChange", objectToChange);
        obj.put("ID", ID);

        if (fieldToChange != "") {
            obj.put("field", fieldToChange);
            obj.put("newValue", newValue);
        }
        return obj.toString();
    }

    public enum methods {
        GET, POST, CHANGE, SUCCES, FAIL
    }

    public enum objectEnum {
        Entity,
        Player,
        HumanCharacter,
        AI,
        Bullet,
        MovingEntity,
        NotMovingEntity,
        Pickup,
        Succes,
        Fail,
        Location,
        Angle
    }

}
