package callofcactus.multiplayer;


import callofcactus.entities.Entity;
import org.json.JSONObject;

/**
 * Created by Wouter Vanmulken on 29-11-2015.
 */
public class Command {

    /**
     * Tells which objects this command will need to change.
     */
    private objectEnum objectToChange;
    private methods method;
    private Entity[] objects;
    private String fieldToChange = "";
    private Object newValue = "";

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

    int ID=-1;

    public int getID() {
        return ID;
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
     * Constructor for the Command class for a CHANGE, FAIL and SUCCES Command
     */
    public Command(methods method,int ID, String fieldToChange, String newValue, objectEnum typeOfObject) {
        this.method = method;
        this.ID = ID;
        this.fieldToChange = fieldToChange;
        this.newValue = newValue;
        this.objectToChange = typeOfObject;
    }


    public objectEnum getObjectToChange() {
        return objectToChange;
    }

    public methods getMethod() {
        return method;
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
        Object ID = obj.get("ID");

        Object field = null;
        Object newValue = null;

        if (obj.has("field")) {
            field = obj.get("field");
            newValue = obj.get("newValue");
        }


        Command c;
        Entity[] objectValues = new Serializer().deserialeDesiredObjects64(value.toString());




        if(Integer.parseInt(ID.toString())!=-1 && field!=null){


            //Command.methods.CHANGE, e.getID(),"location",e.getLocation().x+";"+e.getLocation().y, Command.objectEnum.Bullet


            c = new Command(
                    methods.valueOf(method.toString()),
                    Integer.parseInt(ID.toString()),
                    field.toString(),
                    newValue.toString(),
                    objectEnum.valueOf(objectsToChange.toString())
            );

            return c;
        }
        //for a post, complete object is being sent. This is also why field ==nulll
        else if (field != null) {
            c = new Command(methods.valueOf(method.toString()),
                    objectValues,
                    field.toString(),
                    newValue.toString(),
                    objectEnum.valueOf(
                            objectsToChange.toString()
                    ));
            return c;
        }
        else if(field==null){
            c = new Command(methods.valueOf(method.toString()),
                    objectValues,
                    objectEnum.valueOf(objectsToChange.toString()));
            return c;
        }
        return null;
    }

    public enum methods {
        GET, POST, CHANGE, SUCCES, FAIL, DESTROY
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
        Angle,
        MatchID
    }

}
