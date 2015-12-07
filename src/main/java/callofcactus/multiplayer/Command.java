package callofcactus.multiplayer;


import org.json.JSONObject;

/**
 * Created by Wouter Vanmulken on 29-11-2015.
 */
public class Command {

    /**
     * Tells which objects this command will need to change.
     */
    private String objectToChange;
    private methods method;
    private Object[] objects;
    private String fieldToChange="";
    private Object newValue="";

    /**
     * Constructor for the Command class for a GET Command
     *
     * @param method
     * @param objectsToModify
     */
    public Command(methods method, Object[] objectsToModify, String objectToChange) {
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
    public Command(methods method, Object[] objectsToModify, String fieldToChange, String newValue, String objectToChange) {
        this.method = method;
        this.objects = objectsToModify;
        this.fieldToChange = fieldToChange;
        this.newValue = newValue;
        this.objectToChange = objectToChange;
    }

    /**
     * Decodes the Command object from a string
     * @param input
     * @return
     */
    public static Command fromString(String input) {

        JSONObject obj = new JSONObject(input);
        JSONObject method = obj.getJSONObject("method");
        JSONObject value = obj.getJSONObject("value");
        JSONObject objectsToChange = obj.getJSONObject("objectsToChange");

        JSONObject field = null;
        JSONObject newValue = null;

        if (obj.has("field")) {
            field = obj.getJSONObject("field");
            newValue = obj.getJSONObject("newValue");
        }

        if (field == null) {
            return new Command(methods.valueOf(method.toString()), (new Serializer().deserialeDesiredObjects64(value.toString())), field.toString(), newValue.toString(), objectsToChange.toString());
        }
        return new Command(methods.valueOf(method.toString()), (new Serializer().deserialeDesiredObjects64(value.toString())), objectsToChange.toString());
    }

    public String getObjectToChange() {
        return objectToChange;
    }

    public methods getMethod() {
        return method;
    };

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
     * @return
     */
    @Override
    public String toString() {

        JSONObject obj = new JSONObject();
        obj.put("method", method.toString());
        obj.put("value", new Serializer().serialeDesiredObjects64(objects));
        obj.put("objectsToChange", objectToChange);

        if(fieldToChange.isEmpty()) {
            obj.put("field", fieldToChange);
            obj.put("newvalue", newValue);
        }
        return obj.toString();
    }

    public enum methods {
        GET, POST, CHANGE,SUCCES, FAIL
    }

    public enum objectEnum {
        Entity,
        Player,
        HumanCharacter,
        AI,
        Bullet,
        MovingEntity,
        NotMovingEntity,
        Pickup
    }

}
